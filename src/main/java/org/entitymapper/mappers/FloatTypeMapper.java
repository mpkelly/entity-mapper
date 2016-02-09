package org.entitymapper.mappers;

import org.entitymapper.util.Fields.FieldRecord;

import static org.entitymapper.util.Types.isFloat;

public class FloatTypeMapper extends AbstractTypeMapper {
  public FloatTypeMapper() {
    super("real");
  }

  @Override public boolean canMap(FieldRecord record) {
    return isFloat(record.type);
  }
}
