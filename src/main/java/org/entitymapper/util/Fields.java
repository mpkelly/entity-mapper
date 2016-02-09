package org.entitymapper.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Fields {

  private Fields() {
  }

  public static List<FieldRecord> fields(Class<?> clazz) {
    return fields(clazz, new ArrayList<FieldRecord>());
  }

  public static List<FieldRecord> fields(Class<?> clazz, List<FieldRecord> fields) {
    for (Field field : clazz.getDeclaredFields()) {
      //Ignore references from inner class to outer scope
      if (field.getName().indexOf("$") > 1) {
        continue;
      }
      fields.add(new FieldRecord(field, null));
    }
    if (clazz.getSuperclass() != Object.class) {
      return fields(clazz.getSuperclass(), fields);
    }
    return fields;
  }

  public static List<FieldRecord> fieldsWithValue(Object instance) {
    List<FieldRecord> result = new ArrayList<>();
    for (org.entitymapper.util.Fields.FieldRecord record : fields(instance.getClass())) {
      boolean accessible = record.field.isAccessible();
      try {
        record.field.setAccessible(true);
        Object value = record.field.get(instance);
        result.add(new FieldRecord(record.field, value));
      } catch (IllegalAccessException e) {
        throw new Bug("Cannot access field {0}. Fields need to be accessible to use this method", record.field);
      } finally {
        record.field.setAccessible(accessible);
      }
    }
    return result;
  }

  public static class FieldRecord {
    public final String name;
    public final Class<?> type;
    public final Field field;
    public final Object value;

    public FieldRecord(Field field, Object value) {
      this.field = field;
      this.name = field.getName();
      this.type = field.getType();
      this.value = value;
    }
  }
}

