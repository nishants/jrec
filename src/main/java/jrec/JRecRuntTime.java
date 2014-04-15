package jrec;

import lombok.Getter;
import lombok.Setter;

public class JRecRuntTime implements TestListener {
  public static final String DEFAULT_CHARSET = "UTF-8";

  @Getter
  private final static JRecRuntTime runtTime = new JRecRuntTime();

  @Getter
  private String currentTest;

  private JRecRuntTime(){}

  @Override
  public void beforeTestMethod(String testName) {
    currentTest = testName;
  }

  @Override
  public void afterTestMethod(String testName) {
    currentTest = null;
  }

  @Override
  public void beforeTestClass() {
    //unzip here
  }

  @Override
  public void afterTestClass() {
    //Zip here
  }
}
