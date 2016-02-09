package test.org.entitymapper.mappers;

import org.entitymapper.mappers.BooleanTypeMapper;
import org.entitymapper.util.Fields;
import org.entitymapper.util.Fields.FieldRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BooleanTypeMapperTest extends AbstractTypeMapperTest {

  BooleanTypeMapper mapper = new BooleanTypeMapper();

  @Test public void can_map_boolean_types() throws NoSuchFieldException {
    canMap(mapper, new FieldRecord(field("booleanPrimitive")));
    canMap(mapper, new FieldRecord(field("booleanObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("booleanPrimitive", "boolean", mapper);
    checkCreateTable("booleanObject", "boolean", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("booleanObject", true, mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("booleanObject", true, mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    TestClass instance = mapToJavaType(true, new Fields.FieldRecord(field("booleanPrimitive")), mapper);

    assertEquals("booleanPrimitive", true, instance.booleanPrimitive);
  }
}
