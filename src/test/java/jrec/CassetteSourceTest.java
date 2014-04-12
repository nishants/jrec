package jrec;

import jrec.maker.RecordedRequestMaker;
import jrec.maker.RecordedResponseMaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static jrec.maker.RecordedRequestMaker.*;
import static jrec.maker.RecordedResponseMaker.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CassetteSourceTest {
  private String cassetteName;
  private CassetteSource source;

  @Before
  public void setup() {
    cassetteName = "jrec.SomeTest.someTestMethod";
    source = new CassetteSource("resources/cassettes", null, false, System.getProperty("file.separator"));
  }

  @Test
  public void shouldSerializeFromCurrentTestDirectory() {
    String file = getClass().getClassLoader().getResource("fixtures/").getFile();
    JRecRuntTime.setCurrentTest("cassettes.label");
  }

  @Test
  public void shouldReadCassette() throws IOException {
    Cassette cassette = Cassette.forName(cassetteName);
    RecordedRequest recordedRequest = make(a(Request));
    RecordedResponse recordedResponse = make(a(Response));
    cassette.addTrack(recordedRequest, recordedResponse);
    source.save(cassette);
  }
}
