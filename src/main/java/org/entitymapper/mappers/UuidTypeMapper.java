package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isUuid;

public class UuidTypeMapper extends TextTypeMapper {

  public UuidTypeMapper() {
    super("uuid");
  }

  @Override public boolean canMap(FieldRecord record) {
    return isUuid(record.type);
  }
}
