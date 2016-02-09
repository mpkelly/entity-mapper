package org.entitymapper.database;

import java.sql.SQLException;
import java.util.List;

public interface Database extends TransactionalDatabase {

  <T> List<T> find(Class<T> clazz, String query) throws SQLException;

  <T> List<T> find(Class<T> clazz, String where, Object... params) throws SQLException;

  int insert(Object entity) throws SQLException;

  int update(Object entity) throws SQLException;

  int delete(Class<?> clazz, int id) throws SQLException;

  <T> T get(Class<T> clazz, int id) throws SQLException;

  Boolean createTables(final List<Class<?>> entities) throws SQLException;
}
