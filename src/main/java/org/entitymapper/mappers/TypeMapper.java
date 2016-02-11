package org.entitymapper.mappers;

import org.entitymapper.statements.*;
import org.entitymapper.util.Fields.FieldRecord;

public interface TypeMapper {

  void map(FieldRecord record, CreateTableStatement statement);

  void map(FieldRecord record, InsertStatement statement);

  void map(FieldRecord record, UpdateStatement statement);

  void map(FieldRecord record, DeleteStatement statement);

  void mapToJavaType(FieldRecord record, Object instance, Object sqlValue) throws IllegalAccessException;

  boolean canMap(FieldRecord record);

  String sqlValue(Object value);
}
