package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isBoolean;

public class BooleanTypeMapper extends AbstractTypeMapper {

  public BooleanTypeMapper() {
    super("boolean");
  }

  @Override public boolean canMap(FieldRecord record) {
    return isBoolean(record.type);
  }
}
