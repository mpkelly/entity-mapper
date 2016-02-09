package test.org.entitymapper.statements;

import org.entitymapper.statements.CreateTableStatement;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class CreateTableStatementTest {

  public static class TestEntity {}

  @Test
  public void generates_correct_sql_for_single_column() {
    CreateTableStatement statement = new CreateTableStatement(TestEntity.class);
    statement.addColumn("id", "integer");

    String sql = statement.render();

    assertEquals("create equals", "create table if not exists TestEntity (id integer)", sql);
  }


  @Test
  public void generates_correct_sql_for_multple_columns() {
    CreateTableStatement statement = new CreateTableStatement(TestEntity.class);
    statement.addColumn("id", "integer");
    statement.addColumn("name", "varchar");

    String sql = statement.render();

    assertEquals("create equals", "create table if not exists TestEntity (id integer, name varchar)", sql);
  }
}
