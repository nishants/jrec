package jrec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DiskTest {

  @InjectMocks
  private Disk disk;

  @Test
  public void shouldSerializeFromCurrentTestDirectory() {
    String file = getClass().getClassLoader().getResource("fixtures/").getFile();
    JRec.setCurrentTest("cassettes.testName");
  }
}
