package test.org.entitymapper.database;

import org.entitymapper.EntityMapper;
import org.entitymapper.database.Database;
import org.entitymapper.database.DefaultDatabase;
import org.entitymapper.database.h2.H2MemoryDatabaseClient;
import org.junit.Test;
import test.org.entitymapper.EntityMapperTest;
import test.org.entitymapper.EntityMapperTest.TestClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseTest {

  @Test public void opening_and_closing_connections() throws SQLException {
    EntityMapper mapper = EntityMapper.withLongAutoIncrementPrimaryKey("id");
    Database database = new DefaultDatabase(mapper, new H2MemoryDatabaseClient());

    assertFalse("not open", database.isConnected());

    database.connect();

    assertTrue("open", database.isConnected());

    database.disconnect();

    assertFalse("not open", database.isConnected());

    //Reconnect

    database.connect();

    assertTrue("open", database.isConnected());

    database.disconnect();
  }

  @Test public void can_call_create_tables_multiple_times() throws SQLException {
    EntityMapper mapper = EntityMapper.withLongAutoIncrementPrimaryKey("id");
    Database database = new DefaultDatabase(mapper, new H2MemoryDatabaseClient());

    List<Class<?>> types = new ArrayList<>();
    types.add(TestClass.class);

    database.connect();

    database.createTables(types);
    database.createTables(types);

    //Verify table exists
    List<TestClass> find = database.find(TestClass.class, "select * from TestClass");

    database.disconnect();
  }
}
