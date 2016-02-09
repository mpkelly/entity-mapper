package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isDouble;

public class DoubleTypeMapper extends AbstractTypeMapper {
  public DoubleTypeMapper() {
    super("double");
  }

  @Override public boolean canMap(FieldRecord record) {
    return isDouble(record.type);
  }
}
