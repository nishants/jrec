package jrec;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Vcr extends SpringJUnit4ClassRunner {

  private List<TestListener> listeners;
  private final static Object vcrLock = new Object();

  public Vcr(Class<?> clazz) throws InitializationError {
    super(clazz);
    listeners = new ArrayList<TestListener>();
    listeners.add(JRecRuntTime.getRuntTime());
  }

  @Override
  public void run(RunNotifier notifier) {
    notifyBeforeTestClass();
    super.run(notifier);
  }

  @Override
  protected void runChild(FrameworkMethod frameworkMethod, RunNotifier notifier) {
    String testName = testNameFor(frameworkMethod);
      notifyBeforeTestMethod(testName);
      super.runChild(frameworkMethod, notifier);
  }

  public void addListener(TestListener listener) {
    listeners.add(listener);
  }

  private void notifyBeforeTestMethod(String testName) {
    for (TestListener listener : listeners) {
      listener.beforeTestMethod(testName);
    }
  }

  private void notifyBeforeTestClass() {
    for (TestListener listener : listeners) {
      listener.beforeTestClass();
    }
  }

  private String testNameFor(FrameworkMethod frameworkMethod) {
    Method method = frameworkMethod.getMethod();
    return method.getDeclaringClass().getName() + "." + method.getName();
  }
}

