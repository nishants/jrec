package jrec;

import lombok.Getter;
import lombok.Setter;

public class SpringVcrRuntime implements TestListener {
  public static final String DEFAULT_CHARSET = "UTF-8";

  @Getter
  private final static SpringVcrRuntime runtTime = new SpringVcrRuntime();

  public static String getCurrentTest(){
    return runtTime.getTestName();
  }

  public static void setCurrentTest(String testName){
    runtTime.setTestName(testName);
  }

  public void registerRecorder(Recorder recorder){
    runtTime.setRecorder(recorder);
  }

  @Getter
  @Setter
  private String testName;

  @Setter
  private Recorder recorder;

  @Override
  public void beforeTestMethod(String testName) {
    this.testName = testName;
    recorder.setNextTest(testName);
  }

  @Override
  public void afterTestMethod(String testName) {
    this.testName = null;
    recorder.setNextTest(null);
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
