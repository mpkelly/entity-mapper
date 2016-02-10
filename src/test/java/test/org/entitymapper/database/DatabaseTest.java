package test.org.entitymapper.database;

import org.entitymapper.EntityMapper;
import org.entitymapper.database.Database;
import org.entitymapper.database.DefaultDatabase;
import org.entitymapper.database.h2.H2MemoryDatabaseClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import test.org.entitymapper.EntityMapperTest;
import test.org.entitymapper.EntityMapperTest.TestClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {

  private Database database;

  public Database aDatabaseWithTablesCreated() throws SQLException {
    EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id");
    database = new DefaultDatabase(mapper, new H2MemoryDatabaseClient());
    List<Class<?>> types = new ArrayList<>();
    types.add(TestClass.class);

    database.connect();
    database.createTables(types);

    return database;
  }

  @After
  public void closeDatabase() {
    if(database != null) {
      database.disconnect();
    }
  }

  @Test public void opening_and_closing_connections() throws SQLException {
    EntityMapper mapper = EntityMapper.withLongAutoIncrementPrimaryKey("id");
    database = new DefaultDatabase(mapper, new H2MemoryDatabaseClient());

    assertFalse("not open", database.isConnected());

    database.connect();

    assertTrue("open", database.isConnected());

    database.disconnect();

    assertFalse("not open", database.isConnected());

    //Reconnect

    database.connect();

    assertTrue("open", database.isConnected());
  }

  @Test public void can_insert_and_retrieve_entities() throws SQLException {
    Database database = aDatabaseWithTablesCreated();

    database.insert(new TestClass());
    database.insert(new TestClass());

    List<TestClass> results = database.find(TestClass.class, "select * from TestClass");

    assertEquals("count ", 2, results.size());
  }

  @Test public void can_delete_entity_by_id() throws SQLException {
    Database database = aDatabaseWithTablesCreated();

    database.insert(new TestClass()); //id = 1
    database.get(TestClass.class, 1);

    int count = database.delete(TestClass.class, 1);

    List<TestClass> results = database.find(TestClass.class, "select * from TestClass");

    assertEquals("count", 1, count);
    assertEquals("size", 0, results.size());
  }

  @Test public void can_update_entity() throws SQLException {
    Database database = aDatabaseWithTablesCreated();

    database.insert(new TestClass()); //id = 1

    TestClass result = database.get(TestClass.class, 1);
    result.name = "new name";
    database.update(result);

    result = database.get(TestClass.class, 1);

    assertEquals("name", "new name", result.name);
  }
  
  @Test public void can_find_entities_with_parameterized_query() throws SQLException {
    Database database = aDatabaseWithTablesCreated();
    TestClass test = new TestClass();
    test.name = "the name";

    database.insert(test); //id = 1
    database.insert(new TestClass()); //id = 2

    List<TestClass> results = database.find(TestClass.class, "select * from TestClass where id = ? and name = ?", 1, "the name");

    assertEquals("size", 1, results.size());
    assertEquals("name", "the name", results.get(0).name);
    assertEquals("id", 1, results.get(0).id);
  }
}
