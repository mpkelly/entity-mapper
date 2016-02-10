package test.org.entitymapper.mappers;

import org.entitymapper.mappers.TypeMapper;
import org.entitymapper.statements.CreateTableStatement;
import org.entitymapper.statements.InsertStatement;
import org.entitymapper.statements.UpdateStatement;
import org.entitymapper.util.Fields.FieldRecord;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractTypeMapperTest {

  public static class TestClass {
    boolean booleanPrimitive;
    Boolean booleanObject;

    double doublePrimitive;
    Double doubleObject;

    float floatPrimitive;
    Float floatObject;

    int integerPrimitive;
    Integer integerObject;

    String stringObject;

    long longPrimitive;
    Long longObject;

    Date dateObject;

    UUID uuidObject;
  }

  public static Field field(String name) throws NoSuchFieldException {
    return TestClass.class.getDeclaredField(name);
  }

  public static void canMap(TypeMapper mapper, FieldRecord record) {
    assertTrue("can map", mapper.canMap(record));
  }

  public static void checkCreateTable(final String _name, final Object _type, TypeMapper mapper) throws NoSuchFieldException {
    CreateTableStatement statement = new CreateTableStatement(TestClass.class) {
      @Override public void addColumn(String name, String type) {
        assertEquals("name", _name, name);
        assertEquals("type", _type, type);
      }
    };

    mapper.map(new FieldRecord(field(_name)), statement);
  }

  public static void checkInsert(final String _name, final Object _value, TypeMapper mapper) throws NoSuchFieldException {
    InsertStatement statement = new InsertStatement(TestClass.class) {
      @Override public void addColumnAndValue(String name, String value) {
        assertEquals("name", _name, name);
        assertEquals("type", convert(_value), value);
      }
    };
    Field field = field(_name);
    assertTrue("type check", field.getType().isAssignableFrom(_value.getClass()));

    mapper.map(new FieldRecord(field, _value), statement);
  }

  public static void checkNoInsert(final String _name, final Object _value, TypeMapper mapper) throws NoSuchFieldException {
    InsertStatement statement = new InsertStatement(TestClass.class) {
      @Override public void addColumnAndValue(String name, String value) {
        Assert.fail("method should not be called");
      }
    };
    Field field = field(_name);
    assertTrue("type check", field.getType().isAssignableFrom(_value.getClass()));

    mapper.map(new FieldRecord(field, _value), statement);
  }

  public static void checkUpdate(final String _name, final Object _value, TypeMapper mapper) throws NoSuchFieldException {
    UpdateStatement statement = new UpdateStatement(TestClass.class) {
      @Override public void addColumnAndValue(String name, String value) {
        assertEquals("name", _name, name);
        assertEquals("value", convert(_value), value);
      }
    };
    Field field = field(_name);
    assertTrue("type check", field.getType().isAssignableFrom(_value.getClass()));

    mapper.map(new FieldRecord(field, _value), statement);
  }

  public static TestClass mapToJavaType(Object sqlValue, FieldRecord record, TypeMapper mapper) throws IllegalAccessException {
    TestClass instance = new TestClass();
    record.field.setAccessible(true);
    mapper.mapToJavaType(record, instance, sqlValue);
    return instance;
  }

  public static String convert(Object value) {
    if (value instanceof String || value instanceof UUID) {
      return "'" +  value + "'";
    } else if (value instanceof Date) {
      Long time = ((Date) value).getTime();
      return time.toString();
    }
    return value.toString();
  }
}
