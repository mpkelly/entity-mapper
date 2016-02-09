package org.entitymapper.util;

import java.util.Date;
import java.util.UUID;

public final class Types {

  private Types() {
  }

  public static boolean isInteger(Class<?> type) {
    return int.class.isAssignableFrom(type) || Integer.class.isAssignableFrom(type);
  }

  public static boolean isLong(Class<?> type) {
    return long.class.isAssignableFrom(type) || Long.class.isAssignableFrom(type);
  }

  public static boolean isBoolean(Class<?> type) {
    return boolean.class.isAssignableFrom(type) || Boolean.class.isAssignableFrom(type);
  }

  public static boolean isDouble(Class<?> type) {
    return double.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type);
  }

  public static boolean isFloat(Class<?> type) {
    return float.class.isAssignableFrom(type) || Float.class.isAssignableFrom(type);
  }

  public static boolean isString(Class<?> type) {
    return String.class.isAssignableFrom(type);
  }

  public static boolean isUuid(Class<?> type) {
    return UUID.class.isAssignableFrom(type);
  }

  public static boolean isDate(Class<?> type) {
    return Date.class.isAssignableFrom(type);
  }

}
