package org.entitymapper.database;

import org.entitymapper.EntityMapper;
import org.entitymapper.statements.SelectByIdStatement;
import org.entitymapper.statements.SqlIdentity;
import org.entitymapper.util.Bug;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DefaultDatabase implements Database {
  private final EntityMapper mapper;
  private final TransactionalDatabase client;

  public DefaultDatabase(EntityMapper mapper, TransactionalDatabase client) {
    this.mapper = mapper;
    this.client = client;
  }

  @Override public Boolean createTables(final List<Class<?>> entities) throws SQLException {
    return new Transactor<Boolean>(this).transact(new Transactor.UnitOfWork<Boolean>() {
      @Override public Boolean work() throws SQLException {
        for (Class item : entities) {
          String sql = mapper.createTable(item);
          client.executeUpdate(sql);
        }
        return true;
      }
    });
  }

  @Override public <T> T get(Class<T> type, Object id) throws SQLException {
    String sql = mapper.createSelect(type, id);
    List<T> results = find(type, sql);
    if (results.size() != 1) {
      throw new Bug("Expected exactly one record for query {0} but got {1}", sql, results.size());
    }
    return results.get(0);
  }

  @Override public <T> List<T> find(Class<T> type, String query) throws SQLException {
    List<Map<String, Object>> results = client.executeStatement(query);
    return mapper.map(type, results);
  }

  @Override public <T> List<T> find(Class<T> clazz, String query, Object ... params) throws SQLException {
    List<Map<String, Object>> results = client.executePreparedStatement(query, params);
    return mapper.map(clazz, results);
  }

  @Override public int insert(Object entity) throws SQLException {
    String sql = mapper.createInsert(entity);
    return client.executeUpdate(sql);
  }

  @Override public int update(Object entity) throws SQLException {
    String sql = mapper.createUpdate(entity);
    return client.executeUpdate(sql);
  }

  @Override public int delete(Object instance) throws SQLException {
    String sql = mapper.createDelete(instance);
    return client.executeUpdate(sql);
  }

  @Override public int delete(Class<?> type, Object id) throws SQLException {
    String sql = mapper.createDelete(type, id);
    return client.executeUpdate(sql);
  }

  @Override public <T> int count(Class<T> type) throws SQLException {
    return 0;
  }

  @Override public void beginTransaction() throws SQLException {
    client.beginTransaction();
  }

  @Override public void commit() throws SQLException {
    client.commit();
  }

  @Override public void rollback() throws SQLException {
    client.rollback();
  }

  @Override public boolean connect() throws SQLException {
    return client.connect();
  }

  @Override public void disconnect() {
    client.disconnect();
  }

  @Override public boolean isConnected() throws SQLException {
    return client.isConnected();
  }

  @Override public int executeUpdate(String update) throws SQLException {
    return client.executeUpdate(update);
  }

  @Override public List<Map<String, Object>> executePreparedStatement(String query, Object... params) throws SQLException {
    return client.executePreparedStatement(query, params);
  }

  @Override public List<Map<String, Object>> executeStatement(String query) throws SQLException {
    return client.executeStatement(query);
  }
}
