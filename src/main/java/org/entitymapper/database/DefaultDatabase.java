package org.entitymapper.database;

import org.entitymapper.util.Bug;
import org.entitymapper.EntityMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DefaultDatabase implements Database, TransactionalDatabase {
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
          System.out.println(sql);
          client.executeUpdate(sql);
        }
        return true;
      }
    });
  }

  @Override public <T> T get(Class<T> clazz, int id) throws SQLException {
    return null;
  }

  @Override public <T> List<T> find(Class<T> clazz, String query) throws SQLException {
    List<Map<String, Object>> results = client.executeQuery(query);
    try {
      return mapper.map(clazz, results);
    } catch (IllegalAccessException e) {
      throw new Bug("");
    } catch (InstantiationException e) {
      throw new Bug("");
    }
  }

  @Override public <T> List<T> find(Class<T> clazz, String where, Object... params) throws SQLException {
    return null;
  }

  @Override public int insert(Object entity) throws SQLException {
    String sql = mapper.createInsert(entity);
    return client.executeUpdate(sql);
  }

  @Override public int update(Object entity) throws SQLException {
    String sql = mapper.createInsert(entity);
    return client.executeUpdate(sql);
  }

  @Override public int delete(Class<?> clazz, int id) throws SQLException {
    return -1;
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

  @Override public int executeUpdate(String update) throws SQLException {
    return client.executeUpdate(update);
  }

  @Override public List<Map<String, Object>> executeQuery(String query) throws SQLException {
    return client.executeQuery(query);
  }
}
