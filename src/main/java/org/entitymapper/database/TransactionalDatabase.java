package org.entitymapper.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface TransactionalDatabase {

  void beginTransaction() throws SQLException;

  void commit() throws SQLException;

  void rollback() throws SQLException;

  boolean connect() throws SQLException;

  void disconnect();

  boolean isConnected() throws SQLException;

  int executeUpdate(String update) throws SQLException;

  List<Map<String, Object>> executePreparedStatement(String query, Object... params) throws SQLException;

  List<Map<String, Object>> executeStatement(String query) throws SQLException;
}
