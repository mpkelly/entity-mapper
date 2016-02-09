package org.entitymapper.database.h2;

//See http://www.h2database.com/html/features.html#in_memory_databases

public class H2MemoryDatabaseClient extends H2DatabaseClient {

  //Use this constructor for a private memory database that can accept only one connection
  public H2MemoryDatabaseClient() {
    this("");
  }

  //or use this and specify a name if you want a shared memory database that can accept multiple connections
  public H2MemoryDatabaseClient(String name) {
    super("jdbc:h2:mem:" + name);
  }
}
