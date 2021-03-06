Entity Mapper For Java & Android
===

A small, flexible object mapper that I use on Android projects (though can be used in any Java app). This library works with an embedded H2 database instead of SQLite that ships with Android. 

##Why H2?

I wanted to be able to test a headless version of my app without running an emulator or depending on any Android APIs. This makes testing much quicker and also increases the potential for code reuse. You can get the core logic of your app working without worrying about Android. Once that's done, you can hook it up to the Android UI code. 

Using H2 also means you get a consistent database version across all Android versions. Plus, H2 has loads of cool features and supports more datatypes than SQLite. 

## Features
- Supports long/Long (bigint), int/Integer (integer) primary keys with auto increment option.
- Requires no annotations of special base classes, so can work well with legacy/existing code.
- Supports multiple id names e.g. "id" and "_id"
- Can create different mapping rules for different entity sets
- Ships with ready-to-go JDBC boilerplate with CRUD API
- In-memory databases
- Customisable type mapping rules

## API Overview

### Entity Mapper
You can create a new instance of EntityMapper using one of the following factories

```java

  EntityMapper.withIntegerAutoIncrementPrimaryKey("id");
  
  EntityMapper.withIntegerPrimaryKey("id");
  
  EntityMapper.withLongAutoIncrementPrimaryKey("id");
  
  EntityMapper.withLongPrimaryKey("id");

```

You can also pass in multiple id (primary key) names

``` java

  //A given entity class must contain _only_ one of these ids, though different entities can have different ids
  EntityMapper.withIntegerAutoIncrementPrimaryKey("id", "_id", "ID");

```

EntityMapper can be used standalone to generate SQL, but you will likely want to use it along with a sub-type of Database such as DefaultDatabase. 

### API Quick Start

#### Create an entity

``` java

  public class MyEntity {
    private final int id;
    private final String name;
    private Date createdDate;
    ...
  }

```

#### Create a database and table for our entity

```java

  EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id");
  Database database = new DefaultDatabase(mapper, new H2MemoryDatabaseClient("test"));
  
  database.connect();
  
  List<Class<?>> entities = new ArrayList<>();
  entities.add(MyEntity.class);
  
  database.createTables(entities);

```

#### Inserting entities

```java

  database.insert(new MyEntity()); //new id with value 1 is automatically inserted

```

#### Getting and finding entities

``` java

  MyEntity entity = database.get(MyEntity.class, 1);
  
  //Parameterised queries are just normal JDBC PreparedStatements
  List<MyEntity> results = database.find(MyEntity.class, "select * from MyEntity where name = ?", "the name");
  
```

#### Updating entities

``` java

  entity.name = "new name";
  
  database.update(entity);

```

#### Deleting entities

``` java
    
  database.delete(entity);
  
  //Or 
  
  database.delete(MyEntity.class, 1);

```

#### Transactions

Whenever you need to update mulitple dependent entites, you need to make that operation atomic by wrapping them in a transaction. You can use the Transactor + UnitOfWork pattern to achieve this. Transactor will handle rollbacks and commits for you. 

``` java

  new Transactor<Boolean>(database).transact(new Transactor.UnitOfWork<Boolean>() {
    @Override public Boolean work() throws SQLException {
      database.insert(order);
      database.insert(customer);
      return true;
    }
  });

````

##More to follow..

For now, take a look at the [tests](src/test/java/test/org/entitymapper) and the API for the [Database](src/main/java/org/entitymapper/database/Database.java)
