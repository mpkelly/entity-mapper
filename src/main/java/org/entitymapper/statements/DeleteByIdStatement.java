package org.entitymapper.statements;

import org.entitymapper.util.Template;

public class DeleteByIdStatement extends Template {

  public DeleteByIdStatement(Class<?> type, SqlIdentity identity) {
    super("delete from [name] where [identifier] = [value]");
    add("name", type.getSimpleName());
    add("identifier", identity.id);
    add("value", identity.sqlValue);
  }
}
