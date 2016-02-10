package test.org.entitymapper.database;


import org.entitymapper.database.TransactionalDatabase;
import org.entitymapper.database.Transactor;
import org.entitymapper.database.Transactor.UnitOfWork;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TransactorTest {

  public static class Checks {
    public boolean beginTransactionCalled;
    public boolean commitCalled;
    public boolean rollbackCalled;
  }

  public TransactionalDatabase createDatabase(final Checks checks, final boolean errorOnCommit, final boolean errorOnBegin) {
    return new TransactionalDatabase() {
      @Override public void beginTransaction() throws SQLException {
        checks.beginTransactionCalled = true;
        if (errorOnBegin) {
          throw new SQLException("boom!");
        }
      }

      @Override public void commit() throws SQLException {
        checks.commitCalled = true;
        if (errorOnCommit) {
          throw new SQLException("boom!");
        }
      }

      @Override public void rollback() throws SQLException {
        checks.rollbackCalled = true;
      }

      @Override public boolean connect() throws SQLException {return false;}
      @Override public void disconnect() {}
      @Override public boolean isConnected() throws SQLException { return false;}
      @Override public int executeUpdate(String update) throws SQLException {return 0;}
      @Override public List<Map<String, Object>> executeStatement(String query) throws SQLException {return null;}
      @Override public List<Map<String, Object>> executePreparedStatement(String query, Object... params) throws SQLException {return null;}
    };
  }

  @Test public void follows_correct_transaction_lifecycle_when_there_is_no_error() throws SQLException {
    Checks checks = new Checks();
    TransactionalDatabase database = createDatabase(checks, false, false);
    String result = new Transactor<String>(database).transact(new UnitOfWork<String>() {
      @Override public String work() throws SQLException {
        return "ok";
      }
    });

    assertEquals("result", "ok", result);
    assertTrue("begin", checks.beginTransactionCalled);
    assertTrue("commit", checks.commitCalled);
    assertFalse("no rollback", checks.rollbackCalled);
  }

  @Test public void follows_correct_transaction_lifecycle_when_there_is_an_error_on_commit() {
    Checks checks = new Checks();
    TransactionalDatabase database = createDatabase(checks, true, false);

    String result = null;
    boolean exception = false;
    try {
      result = new Transactor<String>(database).transact(new UnitOfWork<String>() {
        @Override public String work() throws SQLException {
          return "ok";
        }
      });
    } catch (SQLException expected) {
      exception = true;
    }

    assertEquals("result", null, result);
    assertTrue("begin", checks.beginTransactionCalled);
    assertTrue("commit", checks.commitCalled);
    assertTrue("rollback", checks.rollbackCalled);
    assertTrue("exception", exception);
  }

  @Test public void follows_correct_transaction_lifecycle_when_there_is_an_error_during_work() {
    Checks checks = new Checks();
    TransactionalDatabase database = createDatabase(checks, false, false);

    String result = null;
    boolean exception = false;
    try {
      result = new Transactor<String>(database).transact(new UnitOfWork<String>() {
        @Override public String work() throws SQLException {
          throw new RuntimeException("boom!");
        }
      });
    } catch (Exception expected) {
      exception = true;
    }

    assertEquals("result", null, result);
    assertTrue("begin", checks.beginTransactionCalled);
    assertFalse("commit", checks.commitCalled);
    assertTrue("rollback", checks.rollbackCalled);
    assertTrue("exception", exception);
  }

  @Test public void follows_correct_transaction_lifecycle_when_there_is_an_error_during_begin() {
    Checks checks = new Checks();
    TransactionalDatabase database = createDatabase(checks, false, true);

    String result = null;
    boolean exception = false;
    try {
      result = new Transactor<String>(database).transact(new UnitOfWork<String>() {
        @Override public String work() throws SQLException {
          return "ok";
        }
      });
    } catch (Exception expected) {
      exception = true;
    }

    assertEquals("result", null, result);
    assertTrue("begin", checks.beginTransactionCalled);
    assertFalse("commit", checks.commitCalled);
    assertFalse("no rollback", checks.rollbackCalled);
    assertTrue("exception", exception);
  }
}
