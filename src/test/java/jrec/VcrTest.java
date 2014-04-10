package jrec;

import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VcrTest {

  @Test
  public void shouldSetCurrentTestName() throws NoSuchMethodException, InitializationError {
    FrameworkMethod frameworkMethod = mock(FrameworkMethod.class);
    RunNotifier notifier = mock(RunNotifier.class);
    Method targetMethod = this.getClass().getDeclaredMethod("shouldSetCurrentTestName");

    Vcr vcr = new Vcr(this.getClass());

    when(frameworkMethod.getMethod()).thenReturn(targetMethod);

    vcr.runChild(frameworkMethod, notifier);

    assertThat(JRec.getCurrentTest(), is("jrec.VcrTest.shouldSetCurrentTestName"));
  }

}
