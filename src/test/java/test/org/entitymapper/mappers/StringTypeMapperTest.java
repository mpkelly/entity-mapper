package test.org.entitymapper.mappers;

import org.entitymapper.mappers.StringTypeMapper;
import org.entitymapper.util.Fields;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringTypeMapperTest extends AbstractTypeMapperTest {

  StringTypeMapper mapper = new StringTypeMapper();

  @Test public void can_map_float_types() throws NoSuchFieldException {
    canMap(mapper, new Fields.FieldRecord(field("stringObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("stringObject", "varchar", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("stringObject", "the string", mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("stringObject", "another string", mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    TestClass instance = mapToJavaType("hello, world!", new Fields.FieldRecord(field("stringObject")), mapper);

    assertEquals("stringObject", "hello, world!", instance.stringObject);
  }
}
