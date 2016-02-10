package test.org.entitymapper.mappers;

import org.entitymapper.mappers.LongIdentityTypeMapper;
import org.entitymapper.util.Fields.FieldRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LongIdentityTypeMapperTest extends AbstractTypeMapperTest {

  LongIdentityTypeMapper mapper = new LongIdentityTypeMapper();

  @Test public void can_map_long_types() throws NoSuchFieldException {
    canMap(mapper, new FieldRecord(field("longPrimitive")));
    canMap(mapper, new FieldRecord(field("longObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("longPrimitive", "bigint", mapper);
    checkCreateTable("longObject", "bigint", mapper);
  }

  @Test public void adds_correct_create_table_column_definition_when_identity() throws NoSuchFieldException {
    LongIdentityTypeMapper mapper = new LongIdentityTypeMapper("longPrimitive");
    checkCreateTable("longPrimitive", "bigint auto_increment primary key", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("longObject", 1L, mapper);
  }

  @Test public void does_not_insert_value_when_used_as_auto_increment_identity() throws NoSuchFieldException {
    LongIdentityTypeMapper mapper = new LongIdentityTypeMapper("longObject");
    checkNoInsert("longObject", 1L, mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("longObject", -7L, mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    TestClass instance = mapToJavaType(1L, new FieldRecord(field("longPrimitive")), mapper);
    assertEquals("longPrimitive", 1L, instance.longPrimitive);
  }

}
