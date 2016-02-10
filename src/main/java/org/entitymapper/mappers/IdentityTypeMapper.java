package org.entitymapper.mappers;

import org.entitymapper.statements.CreateTableStatement;
import org.entitymapper.statements.InsertStatement;
import org.entitymapper.statements.UpdateStatement;
import org.entitymapper.util.Fields.FieldRecord;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class IdentityTypeMapper extends AbstractTypeMapper {
  private final List<String> idNames;
  private final boolean autoIncrement;

  protected IdentityTypeMapper(String sqlType, boolean autoIncrement, String... idNames) {
    super(sqlType);
    this.autoIncrement = autoIncrement;
    this.idNames = asList(idNames);
  }

  public boolean isId(FieldRecord record) {
    return idNames.contains(record.name);
  }

  @Override public void map(FieldRecord record, CreateTableStatement statement) {
    if (isId(record)) {
      String auto = autoIncrement ? " auto_increment" : "";
      statement.addColumn(record.name, sqlType + auto + " primary key");
    } else {
      super.map(record, statement);
    }
  }

  @Override public void map(FieldRecord record, InsertStatement statement) {
    if (!autoIncrement && !isId(record)) {
      super.map(record, statement);
    }
  }

  @Override public void map(FieldRecord record, UpdateStatement statement) {
    super.map(record, statement);
    if (isId(record)) {
      statement.addWhereClause("id = " + sqlValue(record.value));
    }
  }
}
