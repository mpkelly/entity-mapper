package org.entitymapper.statements;

import org.entitymapper.util.Template;

public class SelectStatement extends Template {
  private String clause = "";

  public SelectStatement(Class<?> type) {
    super("select * from [name][clause]");
    add("name", type.getSimpleName());
  }

  public void addWhereClause(String clause) {
    this.clause = " where " + clause;
  }

  @Override public String render() {
    add("clause", clause);
    return super.render();
  }

}
