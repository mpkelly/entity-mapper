package org.entitymapper;

import org.entitymapper.mappers.*;
import org.entitymapper.statements.*;
import org.entitymapper.util.Bug;
import org.entitymapper.util.Fields.FieldRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Arrays.setAll;
import static org.entitymapper.util.Fields.fields;
import static org.entitymapper.util.Fields.fieldsWithValue;
import static org.entitymapper.util.Types.ofType;

public class EntityMapper {

  public static EntityMapper withIntegerPrimaryKey(String ... idNames) {
    return create(new IntegerIdentityTypeMapper(idNames), new LongIdentityTypeMapper());
  }

  public static EntityMapper withLongPrimaryKey(String ... idNames) {
    return create(new LongIdentityTypeMapper(idNames), new IntegerIdentityTypeMapper());
  }

  public static EntityMapper withIntegerAutoIncrementPrimaryKey(String ... idNames) {
    return create(new IntegerAutoIncrementIdentityTypeMapper(idNames), new LongAutoIncrementIdentityTypeMapper());
  }

  public static EntityMapper withLongAutoIncrementPrimaryKey(String ... idNames) {
    return create(new LongAutoIncrementIdentityTypeMapper(idNames), new IntegerAutoIncrementIdentityTypeMapper());
  }

  private static EntityMapper create(TypeMapper ... additional) {
    List<TypeMapper> mappers = new ArrayList<>();

    mappers.addAll(asList(additional));

    mappers.add(new StringTypeMapper());
    mappers.add(new BooleanTypeMapper());
    mappers.add(new DateTypeMapper());
    mappers.add(new UuidTypeMapper());
    mappers.add(new DoubleTypeMapper());
    mappers.add(new FloatTypeMapper());

    return create(mappers);
  }

  public static EntityMapper create(List<TypeMapper> mappers) {
    return new EntityMapper(mappers);
  }

  private final List<TypeMapper> mappers = new ArrayList<>();

  private EntityMapper(List<? extends TypeMapper> mappers) {
    this.mappers.addAll(mappers);
  }

  public String createTable(Class<?> type) {
    return create(fields(type), new CreateTableStatement(type));
  }

  public String createUpdate(Object instance) {
    return create(fieldsWithValue(instance), new UpdateStatement(instance.getClass()));
  }

  public String createInsert(Object instance) {
    return create(fieldsWithValue(instance), new InsertStatement(instance.getClass()));
  }

  public String createDelete(Object instance) {
    return create(fieldsWithValue(instance), new DeleteStatement(instance.getClass()));
  }

  public String createDelete(Class<?> type, Object id) {
    SqlIdentity identity = identityFrom(type, id);
    DeleteByIdStatement statement = new DeleteByIdStatement(type, identity);
    return statement.render();
  }

  public String createSelect(Class<?> type, Object id) {
    SqlIdentity identity = identityFrom(type, id);
    SelectByIdStatement statement = new SelectByIdStatement(type, identity);
    return statement.render();
  }

  public String create(List<FieldRecord> records, Statement statement) {
    for (FieldRecord record : records) {
      for (TypeMapper mapper : mappers) {
        if (mapper.canMap(record)) {
          statement.receive(record, mapper);
          break;
        }
      }
    }
    return statement.render();
  }

  public <T> List<T> map(Class<T> type, List<Map<String, Object>> rows)  {
    List<T> results = new ArrayList<>();
    for (Map<String, Object> row : rows) {
      try {
        results.add(map(type, row));
      } catch (IllegalAccessException e) {
        throw new Bug(e, "Can not access field or constructor on {0}", type.getSimpleName());
      } catch (InstantiationException e) {
        throw new Bug(e, "Can only map to publicly visible concrete classes, but was called with {0}", type.getSimpleName());
      }
    }
    return results;
  }

  public <T> T map(Class<T> type, Map<String, Object> row) throws IllegalAccessException, InstantiationException {
    T instance = type.newInstance();
    for (FieldRecord record : fields(type)) {
      for (TypeMapper mapper : mappers) {
        if (mapper.canMap(record)) {
          boolean accessible = record.field.isAccessible();
          try {
            record.field.setAccessible(true);
            mapper.mapToJavaType(record, instance, row.get(record.name));
          } finally {
            record.field.setAccessible(accessible);
          }
        }
      }
    }
    return instance;
  }

  public SqlIdentity identityFrom(Class<?> type, Object javaValue) {
    for(FieldRecord record : fields(type)) {
      for (IdentityTypeMapper mapper : ofType(IdentityTypeMapper.class, mappers)) {
        if (mapper.isId(record)) {
          Object sqlValue = mapper.sqlValue(javaValue);
          return new SqlIdentity(record.name, sqlValue.toString());
        }
      }
    }
    throw new Bug("Class {0} does not contain an identity field", type.getSimpleName());
  }
}