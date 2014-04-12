package jrec;

import lombok.Getter;

public class JRecRuntTime {
  public static final String DEFAULT_CHARSET = "UTF-8";

  @Getter
  private static JRecContext context;

  public static void registerContext(JRecContext context) {
    JRecRuntTime.context = context;
  }
}
