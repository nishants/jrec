package jrec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CassetteReaderTest {

  @Test
  public void shouldSerializeFromCurrentTestDirectory() {
    String file = getClass().getClassLoader().getResource("fixtures/").getFile();
    JRecRuntTime.setCurrentTest("cassettes.testName");
  }
}
