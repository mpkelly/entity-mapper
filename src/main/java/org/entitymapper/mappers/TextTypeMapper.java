package org.entitymapper.mappers;

public abstract class TextTypeMapper extends AbstractTypeMapper {

  protected TextTypeMapper(String sqlType) {
    super(sqlType);
  }

  @Override protected String value(Object value) {
    if (value == null) {
      return super.value(null);
    }
    return "'" + value + "'";
  }
}
