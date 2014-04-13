package jrec;

public interface TestListener {
  void beforeTestMethod(String testName);
  void afterTestMethod(String testName);
  void beforeTestClass();
  void afterTestClass();
}
