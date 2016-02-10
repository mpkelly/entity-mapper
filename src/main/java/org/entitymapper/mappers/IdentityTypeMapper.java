package org.entitymapper.mappers;

import org.entitymapper.statements.CreateTableStatement;
import org.entitymapper.statements.InsertStatement;
import org.entitymapper.statements.UpdateStatement;
import org.entitymapper.util.Fields.FieldRecord;

import java.util.List;

import static java.util.Arrays.asList;

public abstract class IdentityTypeMapper extends AbstractTypeMapper {
  private final List<String> idNames;

  protected IdentityTypeMapper(String sqlType, String... idNames) {
    super(sqlType);
    this.idNames = asList(idNames);
  }

  public boolean isId(FieldRecord record) {
    return idNames.contains(record.name);
  }

  @Override public void map(FieldRecord record, CreateTableStatement statement) {
    if (isId(record)) {
      statement.addColumn(record.name, sqlType + " auto_increment primary key");
    } else {
      super.map(record, statement);
    }
  }

  @Override public void map(FieldRecord record, InsertStatement statement) {
    if (!isId(record)) {
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
