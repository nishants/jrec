package jrec;

import lombok.Getter;
import lombok.Setter;

public class JRecRuntTime {
  public static String DEFAULT_CHARSET = "UTF-8";
  public static final String MODE_ENV_KEY = "jrec.mode";
  public static final String CASSETTE_HOME_KEY = "jrec.cassettes.home";

  @Setter
  @Getter
  private static String currentTest;
  private static JrecContext context;

  public static void registerContext(JrecContext context) {
    JRecRuntTime.context = context;
  }
}
