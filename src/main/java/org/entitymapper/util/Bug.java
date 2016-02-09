package org.entitymapper.util;

import static java.text.MessageFormat.format;

//Used to indicate a developer error
public class Bug extends RuntimeException {
  public Bug(String message, Object... args) {
    super(format(message, args));
  }

  public Bug(Exception e, String message, Object... args) {
    super(format(message, args), e);
  }
}

