package org.entitymapper.mappers;

import org.entitymapper.statements.*;
import org.entitymapper.util.Fields.FieldRecord;

public abstract class AbstractTypeMapper implements TypeMapper {
  protected final String sqlType;

  protected AbstractTypeMapper(String sqlType) {
    this.sqlType = sqlType;
  }

  @Override public void map(FieldRecord record, CreateTableStatement statement) {
    statement.addColumn(record.name, sqlType);
  }

  @Override public void map(FieldRecord record, InsertStatement statement) {
    statement.addColumnAndValue(record.name, sqlValue(record.value));
  }

  @Override public void map(FieldRecord record, UpdateStatement statement) {
    statement.addColumnAndValue(record.name, sqlValue(record.value));
  }

  @Override public void map(FieldRecord record, DeleteStatement statement) {

  }

  @Override public void mapToJavaType(FieldRecord record, Object instance, Object sqlValue) throws IllegalAccessException {
    if (sqlValue != null) {
      record.field.set(instance, sqlValue);
    }
  }

  protected String quotedValue(Object value) {
    return "'" + value + "'";
  }

  @Override
  public String sqlValue(Object value) {
    if (value == null) {
      return "NULL";
    }
    return value.toString();
  }
}