package org.entitymapper;

import org.entitymapper.database.DefaultDatabase;
import org.entitymapper.database.JdbcDatabaseClient;
import org.entitymapper.database.h2.H2MemoryDatabaseClient;

import java.util.*;

public class Main {

  public static class Test {
    int age = 3;
    long id;
    float f;
    double d;
    Date date;
    boolean bool;
    String text;
    UUID uuid = UUID.randomUUID();
  }

  public static void main(String[] args) throws Exception {

    EntityMapper mapper = EntityMapper.withLongAutoIncrementPrimaryKey("id");
    DefaultDatabase database = new DefaultDatabase(mapper, new H2MemoryDatabaseClient());

    try {
      database.connect();
      System.out.println(database.isConnected());
      database.createTables(Arrays.<Class<?>>asList(Test.class));
      database.insert(new Test());

      List<Test> results = database.find(Test.class, "select * from Test");

      System.out.println(results.get(0).id);
    } finally {
      database.disconnect();
      System.out.println(database.isConnected());
    }
  }
}
