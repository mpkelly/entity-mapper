package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;
import java.util.Date;
import static org.entitymapper.util.Types.isDate;

public class DateTypeMapper extends AbstractTypeMapper {

  public DateTypeMapper() {
    super("bigint");
  }

  @Override protected String value(Object value) {
    if (value == null) {
      return super.value(null);
    }
    Long time = ((Date)value).getTime();
    return time.toString();
  }

  @Override public void mapToJavaType(FieldRecord record, Object instance, Object sqlValue) throws IllegalAccessException {
    Long time = (Long) sqlValue;
    if (time != null) {
      super.mapToJavaType(record, instance, new Date(time));
    }
  }

  @Override public boolean canMap(FieldRecord record) {
    return isDate(record.type);
  }
}
