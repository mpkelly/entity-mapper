package org.entitymapper.database;

import java.sql.SQLException;

public class Transactor<T> {
  private final TransactionalDatabase database;

  public Transactor(TransactionalDatabase database) {
    this.database = database;
  }

  public T transact(UnitOfWork<T> work) throws SQLException {
    boolean transactionActive = false;
    try {
      database.beginTransaction();
      transactionActive = true;

      T result = work.work();
      database.commit();

      transactionActive = false;
      return result;
    } catch (Exception e) {
      if (transactionActive) {
        try {
          database.rollback();
        } catch (SQLException rollback) {
          rollback.printStackTrace();
          throw new SQLException("Encountered an error and then could not rollback transaction", e);
        }
      }
      throw e;
    }
  }

  public static abstract class UnitOfWork<T> {
    public abstract T work() throws SQLException;
  }
}
