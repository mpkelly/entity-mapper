package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isInteger;

public class IntegerAutoIncrementIdentityTypeMapper extends IdentityTypeMapper {

  public IntegerAutoIncrementIdentityTypeMapper(String... idNames) {
    super("integer", true, idNames);
  }

  @Override public boolean canMap(FieldRecord record) {
    return isInteger(record.type);
  }
}
