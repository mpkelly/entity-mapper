package org.entitymapper;

import org.entitymapper.mappers.*;
import org.entitymapper.statements.CreateTableStatement;
import org.entitymapper.statements.InsertStatement;
import org.entitymapper.statements.Statement;
import org.entitymapper.statements.UpdateStatement;
import org.entitymapper.util.Fields.FieldRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.entitymapper.util.Fields.fields;
import static org.entitymapper.util.Fields.fieldsWithValue;

public class EntityMapper {
  private final List<TypeMapper> mappers = new ArrayList<>();

  public static EntityMapper withIntegerAutoIncrementPrimaryKey(String ... idNames) {
    return create(new IntegerIdentityTypeMapper(idNames), new LongIdentityTypeMapper());
  }

  public static EntityMapper withLongAutoIncrementPrimaryKey(String ... idNames) {
    return create(new LongIdentityTypeMapper(idNames), new IntegerIdentityTypeMapper());
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

  public String create(List<FieldRecord> records, Statement statement) {
    for (FieldRecord record : records) {
      for (TypeMapper mapper : mappers) {
        if (mapper.canMap(record)) {
          statement.receive(record, mapper);
        }
      }
    }
    return statement.render();
  }

  public <T> List<T> map(Class<T> type, List<Map<String, Object>> rows) throws IllegalAccessException, InstantiationException {
    List<T> results = new ArrayList<>();
    for (Map<String, Object> row : rows) {
      results.add(map(type, row));
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
}