package jrec;

import lombok.Getter;
import lombok.Setter;

public class JRec {
  public static String DEFAULT_CHARSET = "UTF-8";
  public static final String MODE_ENV_KEY = "jrec.mode";
  public static final String CASSETTE_HOME_KEY = "jrec.cassettes.home";

  @Getter
  @Setter
  private static VCRMode mode;

  @Setter
  @Getter
  private static String currentTest;
  private static JrecContext it;

  public static void setIt(JrecContext it) {
    JRec.it = it;
  }
}
