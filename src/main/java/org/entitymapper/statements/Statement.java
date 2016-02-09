package org.entitymapper.statements;

import org.entitymapper.mappers.TypeMapper;
import org.entitymapper.util.Fields.FieldRecord;
import org.entitymapper.util.Template;

public abstract class Statement extends Template {

  public Statement(String template) {
    super(template);
  }

  public abstract void receive(FieldRecord record, TypeMapper mapper);
}
