package jrec;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;

public class Vcr extends SpringJUnit4ClassRunner {

  private static ClassPathXmlApplicationContext classPathXmlApplicationContext;

  public Vcr(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  @Override
  public void run(RunNotifier notifier) {
    super.run(notifier);
  }

  @Override
  protected void runChild(FrameworkMethod frameworkMethod, RunNotifier notifier) {
    setNextCassetteDir(frameworkMethod);
    super.runChild(frameworkMethod, notifier);
  }

  private void setNextCassetteDir(FrameworkMethod frameworkMethod) {
    JRecRuntTime.setCurrentTest(testNameFor(frameworkMethod));
  }

  private String testNameFor(FrameworkMethod frameworkMethod) {
    Method method = frameworkMethod.getMethod();
    return method.getDeclaringClass().getName() + "." + method.getName();
  }
}

