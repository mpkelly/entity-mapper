package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isLong;

public class LongIdentityTypeMapper extends IdentityTypeMapper {

  public LongIdentityTypeMapper(String... idNames) {
    super("bigint", idNames);
  }

  @Override public boolean canMap(FieldRecord record) {
    return isLong(record.type);
  }
}
