package test.org.entitymapper.mappers;

import org.entitymapper.mappers.DoubleTypeMapper;
import org.entitymapper.util.Fields.FieldRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoubleTypeMapperTest extends AbstractTypeMapperTest {

  DoubleTypeMapper mapper = new DoubleTypeMapper();

  @Test public void can_map_double_types() throws NoSuchFieldException {
    canMap(mapper, new FieldRecord(field("doublePrimitive")));
    canMap(mapper, new FieldRecord(field("doubleObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("doublePrimitive", "double", mapper);
    checkCreateTable("doubleObject", "double", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("doubleObject", 2.2, mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("doubleObject", 3.22111, mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    TestClass instance = mapToJavaType(2.2, new FieldRecord(field("doublePrimitive")), mapper);

    assertEquals("doublePrimitive", 2.2, instance.doublePrimitive, 0.0);
  }
}
