package org.entitymapper.statements;

public class SqlIdentity {
  public final String id;
  public final String sqlValue;

  public SqlIdentity(String id, String sqlValue) {
    this.id = id;
    this.sqlValue = sqlValue;
  }
}
