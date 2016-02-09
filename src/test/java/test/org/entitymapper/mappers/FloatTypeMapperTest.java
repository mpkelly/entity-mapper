package test.org.entitymapper.mappers;

import org.entitymapper.mappers.FloatTypeMapper;
import org.entitymapper.util.Fields;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FloatTypeMapperTest extends AbstractTypeMapperTest {

  FloatTypeMapper mapper = new FloatTypeMapper();

  @Test public void can_map_float_types() throws NoSuchFieldException {
    canMap(mapper, new Fields.FieldRecord(field("floatPrimitive")));
    canMap(mapper, new Fields.FieldRecord(field("floatObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("floatPrimitive", "real", mapper);
    checkCreateTable("floatObject", "real", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("floatObject", 2.2F, mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("floatObject", 3.22111F, mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    TestClass instance = mapToJavaType(2.2F, new Fields.FieldRecord(field("floatPrimitive")), mapper);

    assertEquals("floatPrimitive", 2.2F, instance.floatPrimitive, 0.0);
  }
}
