package org.entitymapper.statements;

import org.entitymapper.util.Template;

public class DeleteByIdStatement extends Template {

  public DeleteByIdStatement(Class<?> type, SqlIdentity identity) {
    super("delete from [name] where [identity] = [value]");
    add("name", type.getSimpleName());
    add("identity", identity.id);
    add("value", identity.sqlValue);
  }
}
