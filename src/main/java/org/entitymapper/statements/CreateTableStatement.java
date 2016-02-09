package org.entitymapper.statements;

import org.entitymapper.mappers.TypeMapper;
import org.entitymapper.util.Fields;
import org.entitymapper.util.Fields.FieldRecord;
import org.entitymapper.util.Template;

import java.util.ArrayList;
import java.util.List;

import static org.entitymapper.util.Strings.join;

public class CreateTableStatement extends Statement {

  private final List<String> columns = new ArrayList<>();

  public CreateTableStatement(Class<?> type) {
    super("create table if not exists [name] ([body])");
    add("name", type.getSimpleName());
  }

  public void addColumn(String name, String type) {
    String column = new Template("[name] [type]")
      .add("name", name)
      .add("type", type)
      .render();
    columns.add(column);
  }

  @Override public String render() {
    String body = join(columns, ", ");
    add("body", body);
    return super.render();
  }

  @Override public void receive(FieldRecord record, TypeMapper mapper) {
    mapper.map(record, this);
  }
}
