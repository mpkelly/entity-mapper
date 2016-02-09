package org.entitymapper.util;

import java.util.HashMap;
import java.util.Map;

public class Template {
  private final String template;
  private final Map<String, String> parameters = new HashMap<>();

  public Template(String template) {
    this.template = template;
  }

  public Template add(String name, Object value) {
    String key = "[" + name + "]";
    parameters.put(key, value.toString());
    return this;
  }

  public String render() {
    StringBuilder output = new StringBuilder(template);
    for (String key : parameters.keySet()) {
      replace(key, parameters.get(key), output);
    }
    return output.toString();
  }

  public static void replace(String target, String replacement, StringBuilder builder) {
    int indexOfTarget;
    while ((indexOfTarget = builder.indexOf(target)) >= 0) {
      builder.replace(indexOfTarget, indexOfTarget + target.length(), replacement);
    }
  }
}
