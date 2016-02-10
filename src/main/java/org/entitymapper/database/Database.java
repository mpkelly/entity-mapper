package org.entitymapper.database;

import java.sql.SQLException;
import java.util.List;

public interface Database extends TransactionalDatabase {

  <T> T get(Class<T> type, Object id) throws SQLException;

  <T> List<T> find(Class<T> type, String query) throws SQLException;

  <T> List<T> find(Class<T> type, String query, Object ... params) throws SQLException;

  int insert(Object entity) throws SQLException;

  int update(Object entity) throws SQLException;

  int delete(Class<?> type, Object id) throws SQLException;

  Boolean createTables(final List<Class<?>> entities) throws SQLException;
}
