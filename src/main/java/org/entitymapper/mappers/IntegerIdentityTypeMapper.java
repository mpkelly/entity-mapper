package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isInteger;

public class IntegerIdentityTypeMapper extends IdentityTypeMapper {

  public IntegerIdentityTypeMapper(String... idNames) {
    super("integer", idNames);
  }

  @Override public boolean canMap(FieldRecord record) {
    return isInteger(record.type);
  }
}
