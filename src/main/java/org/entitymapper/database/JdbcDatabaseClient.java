package org.entitymapper.database;

import org.entitymapper.util.Bug;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.entitymapper.util.Resources.closeQuietly;

public class JdbcDatabaseClient implements TransactionalDatabase {
  private final String url;
  private Connection connection;

  public JdbcDatabaseClient(String driver, String url) {
    this.url = url;
    loadDriver(driver);
  }

  private void loadDriver(String driver) {
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      throw new Bug(e, "this class depends on {0} driver which wasn't found on the classpath", driver);
    }
  }

  @Override public synchronized boolean connect() throws SQLException {
    if(!isConnected()) {
      connection = DriverManager.getConnection(url);
    }
    return true;
  }

  @Override
  public synchronized  boolean isConnected() throws SQLException {
    return connection != null && !connection.isClosed();
  }

  @Override public void disconnect() {
    closeQuietly(connection);
  }

  @Override public int executeUpdate(String update) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      return statement.executeUpdate(update);
    }
  }

  @Override public List<Map<String, Object>> executeQuery(String query) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      return map(statement.executeQuery(query));
    }
  }

  private List<Map<String, Object>> map(ResultSet resultSet) throws SQLException {
    ResultSetMetaData metaData = resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();
    List<Map<String, Object>> results = new ArrayList<>();

    while (resultSet.next()) {
      Map<String, Object> row = new LinkedHashMap<>();
      for (int i = 1; i <= columnCount; i++) {
        row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
      }
      results.add(row);
    }
    return results;
  }

  @Override public void beginTransaction() throws SQLException {
    connection.setAutoCommit(false);
  }

  @Override public void commit() throws SQLException {
    connection.commit();
  }

  @Override public void rollback() throws SQLException {
    connection.rollback();
  }
}
