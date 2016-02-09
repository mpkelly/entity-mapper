package org.entitymapper.util;

public final class Resources {
  private Resources() {
  }

  public static void closeQuietly(AutoCloseable closeable) {
    if (closeable == null) {
      return;
    }
    try {
      closeable.close();
    } catch (Exception ignored) {

    }
  }
}
