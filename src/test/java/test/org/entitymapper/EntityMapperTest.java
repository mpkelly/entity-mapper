package test.org.entitymapper;

import org.entitymapper.EntityMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EntityMapperTest {

  public static class TestClass {
    public int id;
    public String name;
  }

  public static class TestClass2 {
    public String name;
  }

  @Test public void can_generate_create_table_sql() {
    EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id");
    String sql = mapper.createTable(TestClass.class);
    assertEquals("create table", "create table if not exists TestClass (id integer auto_increment primary key, name varchar)", sql);
  }

  @Test public void can_generate_insert_statement_sql() {
    EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id" , "otherId");
    TestClass testClass = new TestClass();
    testClass.name = "the name";

    String sql = mapper.createInsert(testClass);
    assertEquals("insert", "insert into TestClass (name) values ('the name')", sql);
  }

  @Test public void can_generate_update_statement_sql() {
    EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id", "_id");
    TestClass testClass = new TestClass();
    testClass.name = "the name";
    testClass.id = 2;

    String sql = mapper.createUpdate(testClass);
    assertEquals("insert", "update TestClass set id = 2, name = 'the name' where id = 2", sql);
  }

  @Test public void can_create_instance_from_database_row() throws InstantiationException, IllegalAccessException {
    EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id");

    TestClass testClass = mapper.map(TestClass.class, row(2, "the name"));

    assertEquals("id", 2, testClass.id);
    assertEquals("id", "the name", testClass.name);
  }

  @Test public void can_create_instances_of_different_type_from_database_rows() throws InstantiationException, IllegalAccessException {
    EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id");
    List<Map<String, Object>> rows = new ArrayList<>();
    rows.add(row(1, "name 1"));
    rows.add(row(2, "name 2"));

    List<TestClass2> results = mapper.map(TestClass2.class, rows);

    assertEquals("size", 2, results.size());
    assertEquals("name 1", "name 1", results.get(0).name);
    assertEquals("name 2", "name 2", results.get(1).name);
  }

  public static Map<String, Object> row(final int id, final String name) {
    return new HashMap<String, Object>() {{
      put("id", id);
      put("name", name);
    }};
  }
}
