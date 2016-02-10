package test.org.entitymapper.mappers;

import org.entitymapper.mappers.DateTypeMapper;
import org.entitymapper.util.Fields.FieldRecord;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateTypeMapperTest extends AbstractTypeMapperTest {

  Date date = new Date(2015 - 1900, 0, 1);
  DateTypeMapper mapper = new DateTypeMapper();

  @Test public void can_map_date_types() throws NoSuchFieldException {
    canMap(mapper, new FieldRecord(field("dateObject")));
  }

  @Test public void adds_correct_create_table_column_definition() throws NoSuchFieldException {
    checkCreateTable("dateObject", "bigint", mapper);
  }

  @Test public void adds_correct_insert_column_and_value() throws NoSuchFieldException {
    checkInsert("dateObject",date , mapper);
  }

  @Test public void adds_correct_update_column_and_value() throws NoSuchFieldException {
    checkUpdate("dateObject", date, mapper);
  }

  @Test public void maps_to_java_instance() throws NoSuchFieldException, IllegalAccessException {
    TestClass instance = mapToJavaType(date.getTime(), new FieldRecord(field("dateObject")), mapper);

    assertEquals("dateObject", date, instance.dateObject);
  }
}
