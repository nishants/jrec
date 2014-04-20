package com.geeksaint.spring.vcr;

import lombok.Getter;

public class SpringVcrRuntime implements TestListener {
  public static final String DEFAULT_CHARSET = "UTF-8";

  @Getter
  private final static SpringVcrRuntime runtTime = new SpringVcrRuntime();

  @Getter
  private String currentTest;

  private SpringVcrRuntime(){}

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
