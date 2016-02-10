package org.entitymapper.mappers;

public abstract class TextTypeMapper extends AbstractTypeMapper {

  protected TextTypeMapper(String sqlType) {
    super(sqlType);
  }

  @Override public String sqlValue(Object value) {
    if (value == null) {
      return super.sqlValue(null);
    }
    return quotedValue(value);
  }
}
