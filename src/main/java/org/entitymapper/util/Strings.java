package org.entitymapper.util;

import java.util.List;

public final class Strings {

  public static String join(List<String> elements, String delimiter) {
    StringBuilder builder = new StringBuilder();
    int index = 0;
    for (String element : elements) {
      builder.append(element);
      if (index + 1 < elements.size()) {
        builder.append(delimiter);
      }
      index++;
    }
    return builder.toString();
  }
}
