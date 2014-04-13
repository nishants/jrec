package jrec;

import org.junit.Test;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.Method;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@ContextConfiguration({"classpath:configuration/test-config.xml"})
public class VcrTest {

  @Test
  public void shouldSetCurrentTestName() throws NoSuchMethodException, InitializationError {
    FrameworkMethod frameworkMethod = mock(FrameworkMethod.class);
    RunNotifier notifier = mock(RunNotifier.class);
    Method targetMethod = this.getClass().getDeclaredMethod("shouldSetCurrentTestName");

    Vcr vcr = new Vcr(this.getClass());

    when(frameworkMethod.getMethod()).thenReturn(targetMethod);

    vcr.runChild(frameworkMethod, notifier);

    assertThat("fail fial ", is(nullValue()));
    //verify(context, times(1)).beforeTestMethod("jrec.VcrTest.shouldSetCurrentTestName");
  }

}
