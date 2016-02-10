package org.entitymapper.mappers;

import org.entitymapper.statements.CreateTableStatement;
import org.entitymapper.statements.InsertStatement;
import org.entitymapper.statements.UpdateStatement;
import org.entitymapper.util.Fields.FieldRecord;

public interface TypeMapper {

  void map(FieldRecord record, CreateTableStatement statement);

  void map(FieldRecord record, InsertStatement statement);

  void map(FieldRecord record, UpdateStatement statement);

  boolean canMap(FieldRecord record);

  void mapToJavaType(FieldRecord record, Object instance, Object sqlValue) throws IllegalAccessException;

  String sqlValue(Object value);
}
