package test.org.entitymapper.statements;

import org.entitymapper.statements.InsertStatement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static test.org.entitymapper.statements.CreateTableStatementTest.*;

public class InsertStatementTest {

  @Test
  public void can_create_insert_statement_for_single_column() {
    InsertStatement statement = new InsertStatement(TestEntity.class);
    statement.addColumnAndValue("name", "'the name'");

    String sql = statement.render();

    assertEquals("insert equals", "insert into TestEntity (name) values ('the name')", sql);
  }

  @Test
  public void can_create_insert_statement_for_multiple_column() {
    InsertStatement statement = new InsertStatement(TestEntity.class);
    statement.addColumnAndValue("name", "'the name'");
    statement.addColumnAndValue("age", "99");

    String sql = statement.render();

    assertEquals("insert equals", "insert into TestEntity (name, age) values ('the name', 99)", sql);
  }
}
