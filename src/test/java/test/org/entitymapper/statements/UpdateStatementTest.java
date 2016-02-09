package test.org.entitymapper.statements;

import org.entitymapper.statements.UpdateStatement;
import org.junit.Test;
import test.org.entitymapper.statements.CreateTableStatementTest.TestEntity;

import static org.junit.Assert.assertEquals;

public class UpdateStatementTest {

  @Test
  public void can_create_update_for_single_column() {
    UpdateStatement statement = new UpdateStatement(TestEntity.class);
    statement.addColumnAndValue("name", "'the name'");

    String sql = statement.render();
    assertEquals("update equals", "update TestEntity set name = 'the name'", sql);
  }

  @Test
  public void can_update_multiple_columns_with_where_clause() {
    UpdateStatement statement = new UpdateStatement(TestEntity.class);
    statement.addColumnAndValue("name", "'the name'");
    statement.addColumnAndValue("age", "99");
    statement.addWhereClause("id = 1");

    String sql = statement.render();
    assertEquals("update with where equals", "update TestEntity set name = 'the name', age = 99 where id = 1", sql);
  }
}
