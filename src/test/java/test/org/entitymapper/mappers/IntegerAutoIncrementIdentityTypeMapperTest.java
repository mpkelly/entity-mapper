package test.org.entitymapper.mappers;

import org.entitymapper.mappers.IntegerAutoIncrementIdentityTypeMapper;
import org.entitymapper.util.Fields.FieldRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerAutoIncrementIdentityTypeMapperTest extends AbstractTypeMapperTest {

  IntegerAutoIncrementIdentityTypeMapper mapper = new IntegerAutoIncrementIdentityTypeMapper();

  @Test public void can_map_integer_types() throws NoSuchFieldException {
    canMap(mapper, new FieldRecord(field("integerPrimitive")));
    canMap(mapper, new FieldRecord(field("integerObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("integerPrimitive", "integer", mapper);
    checkCreateTable("integerObject", "integer", mapper);
  }

  @Test public void adds_correct_create_table_column_definition_when_identity() throws NoSuchFieldException {
    IntegerAutoIncrementIdentityTypeMapper mapper = new IntegerAutoIncrementIdentityTypeMapper("integerPrimitive");
    checkCreateTable("integerPrimitive", "integer auto_increment primary key", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("integerObject", 1, mapper);
  }

  @Test public void does_not_insert_value_when_used_as_auto_increment_identity() throws NoSuchFieldException {
    IntegerAutoIncrementIdentityTypeMapper mapper = new IntegerAutoIncrementIdentityTypeMapper("integerObject");
    checkNoInsert("integerObject", 1, mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("integerObject", -7, mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    TestClass instance = mapToJavaType(1, new FieldRecord(field("integerPrimitive")), mapper);
    assertEquals("integerPrimitive", 1, instance.integerPrimitive);
  }
}
