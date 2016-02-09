package org.entitymapper.statements;

import org.entitymapper.mappers.TypeMapper;
import org.entitymapper.util.Fields.FieldRecord;
import org.entitymapper.util.Template;

import java.util.ArrayList;
import java.util.List;

import static org.entitymapper.util.Strings.join;

public class UpdateStatement extends Statement {
  private final List<String> updates = new ArrayList<>();
  private String clause;

  public UpdateStatement(Class<?> type) {
    super("update table [name] set [body] where [clause]");
    add("name", type.getSimpleName());
  }

  @Override public void receive(FieldRecord record, TypeMapper mapper) {
    mapper.map(record, this);
  }

  public void addColumnAndValue(String name, String value) {
    String update = new Template("[name] = [value]")
      .add("name", name)
      .add("value", value)
      .render();
    updates.add(update);
  }

  public void addWhereClause(String clause) {
    this.clause = clause;
  }

  @Override public String render() {
    add("body", join(updates, ", "));
    add("clause", clause);
    return super.render();
  }
}
