package test.org.entitymapper.mappers;

import org.entitymapper.mappers.UuidTypeMapper;
import org.entitymapper.util.Fields.FieldRecord;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UuidTypeMapperTest extends AbstractTypeMapperTest {

  UUID uuid = UUID.fromString("d8a2126f-a6b8-4a30-8730-5cf4bd9da364");
  UuidTypeMapper mapper = new UuidTypeMapper();

  @Test public void can_map_uuid_types() throws NoSuchFieldException {
    canMap(mapper, new FieldRecord(field("uuidObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("uuidObject", "uuid", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("uuidObject", uuid, mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("uuidObject", uuid, mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    UUID uuid = UUID.randomUUID();
    TestClass instance = mapToJavaType(uuid, new FieldRecord(field("uuidObject")), mapper);
    assertEquals("uuidObject", uuid, instance.uuidObject);
  }
}
