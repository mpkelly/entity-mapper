package org.entitymapper.statements;

import org.entitymapper.mappers.TypeMapper;
import org.entitymapper.util.Fields.FieldRecord;

import java.util.ArrayList;
import java.util.List;

import static org.entitymapper.util.Strings.join;

public class InsertStatement extends Statement {
  private final List<String> columns = new ArrayList<>();
  private final List<String> values = new ArrayList<>();

  public InsertStatement(Class<?> type) {
    super("insert into [name] ([columns]) values ([values])");
    add("name", type.getSimpleName());
  }

  public void addColumnAndValue(String name, String value) {
    columns.add(name);
    values.add(value);
  }

  @Override public String render() {
    add("columns", join(columns, DELIMITER));
    add("values", join(values, DELIMITER));
    return super.render();
  }

  @Override public void receive(FieldRecord record, TypeMapper mapper) {
    mapper.map(record, this);
  }
}
