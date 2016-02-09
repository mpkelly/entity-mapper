package org.entitymapper.database.h2;

import org.entitymapper.database.JdbcDatabaseClient;

// As part of the URL you can pass in additional flags. See link below:
// http://www.h2database.com/html/features.html#database_url
public class H2DatabaseClient extends JdbcDatabaseClient {

  public H2DatabaseClient(String url) {
    super("org.h2.Driver", url + ";database_to_upper=false");
  }
}
