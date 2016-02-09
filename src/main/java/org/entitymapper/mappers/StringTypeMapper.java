package org.entitymapper.mappers;

import org.entitymapper.util.Fields;

import static org.entitymapper.util.Types.isString;

public class StringTypeMapper extends TextTypeMapper {

  public StringTypeMapper() {
    super("varchar");
  }

  @Override public boolean canMap(Fields.FieldRecord record) {
    return isString(record.type);
  }
}
