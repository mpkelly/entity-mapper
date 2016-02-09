package test.org.entitymapper.mappers;

import org.entitymapper.mappers.TypeMapper;
import org.entitymapper.statements.CreateTableStatement;
import org.entitymapper.statements.InsertStatement;
import org.entitymapper.statements.UpdateStatement;
import org.entitymapper.util.Fields.FieldRecord;

import java.lang.reflect.Field;

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

        String expected = _value instanceof String ? "'" +  _value + "'" : _value.toString();

        assertEquals("name", _name, name);
        assertEquals("type", expected, value);
      }
    };
    Field field = field(_name);
    assertTrue("type check", field.getType().isAssignableFrom(_value.getClass()));

    mapper.map(new FieldRecord(field, _value), statement);
  }

  public static void checkUpdate(final String _name, final Object _value, TypeMapper mapper) throws NoSuchFieldException {
    UpdateStatement statement = new UpdateStatement(TestClass.class) {
      @Override public void addColumnAndValue(String name, String value) {

        String expected = _value instanceof String ? "'" +  _value + "'" : _value.toString();

        assertEquals("name", _name, name);
        assertEquals("type", expected, value);
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
}
