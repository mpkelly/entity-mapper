package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isLong;

public class LongAutoIncrementIdentityTypeMapper extends IdentityTypeMapper {

  public LongAutoIncrementIdentityTypeMapper(String... idNames) {
    super("bigint", true, idNames);
  }

  @Override public boolean canMap(FieldRecord record) {
    return isLong(record.type);
  }
}
