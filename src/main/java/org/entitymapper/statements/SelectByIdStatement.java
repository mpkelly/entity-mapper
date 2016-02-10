package org.entitymapper.statements;

import org.entitymapper.statements.SqlIdentity;
import org.entitymapper.util.Template;

public class SelectByIdStatement extends Template {

  public SelectByIdStatement(Class<?> type, SqlIdentity identity) {
    super("select * from [name] where [identifier] = [value]");
    add("name", type.getSimpleName());
    add("identifier", identity.id);
    add("value", identity.sqlValue);
  }
}
