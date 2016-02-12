package org.entitymapper.util;

import org.entitymapper.EntityMapper;
import org.entitymapper.database.Database;
import org.entitymapper.database.DefaultDatabase;
import org.entitymapper.database.Transactor;
import org.entitymapper.database.h2.H2MemoryDatabaseClient;

import java.sql.SQLException;

public class Main {

//  public static void main(String[] args) {
//    EntityMapper mapper = EntityMapper.withIntegerAutoIncrementPrimaryKey("id");
//    final Database database = new DefaultDatabase(mapper, new H2MemoryDatabaseClient("test"));
//
//    entity.name = "new name";
//
//
//    database.delete(entity);
//
//    //Or
//
//    database.delete(MyEntity.class, 1);
//
//    new Transactor<Boolean>(database).transact(new Transactor.UnitOfWork<Boolean>() {
//      @Override public Boolean work() throws SQLException {
//        database.insert(order);
//        database.insert(customer);
//        return true;
//      }
//    });
//  }
}
