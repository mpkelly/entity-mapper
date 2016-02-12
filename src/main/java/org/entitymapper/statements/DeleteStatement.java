package org.entitymapper.statements;

import org.entitymapper.mappers.TypeMapper;
import org.entitymapper.util.Fields.FieldRecord;

public class DeleteStatement extends Statement {

  private String clause = "";

  public DeleteStatement(Class<?> type) {
    super("delete from [name][clause]");
    add("name", type.getSimpleName());
  }

  public void addWhereClause(String clause) {
    this.clause = " where " + clause;
  }

  @Override public String render() {
    add("clause", clause);
    return super.render();
  }

  @Override public void receive(FieldRecord record, TypeMapper mapper) {
    mapper.map(record, this);
  }
}
