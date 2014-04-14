package jrec;

import jrec.maker.RecordedRequestMaker;
import jrec.maker.RecordedResponseMaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static jrec.maker.RecordedRequestMaker.*;
import static jrec.maker.RecordedResponseMaker.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CassetteSourceTest {
  private String testName;
  private String outputDir;
  private CassetteSource source;

  @Before
  public void setup() {
    testName = "jrec/SomeTestClass/someTestMethod";
    source = new CassetteSource(tempFile(), System.getProperty("file.separator"));
  }

  private String tempFile() {
    return System.getProperty("java.io.tmpdir");
  }

  @Test
  public void shouldSerializeFromCurrentTestDirectory() throws IOException {
    String projectRoot = getClass().getResource("/fixtures/cassettes").getFile();
    source = new CassetteSource(projectRoot, System.getProperty("file.separator"));

    Cassette cassette = source.cassetteFor("jrec/TestClassName/methodName");

    assertThat(cassette.getLabel(), is("jrec/TestClassName/methodName"));
  }

  @Test
  public void shouldSaveCassetteToCorrectDirectory() throws IOException {
    Cassette cassette = Cassette.forName(testName);
    cassette.addTrack(make(a(Request)), make(a(Response)));
    cassette.addTrack(make(a(Request, with(RecordedRequestMaker.method, "PUT"))), make(a(Response, with(RecordedResponseMaker.content, "someother content"))));

    source.save(cassette);

    String expectedRelativePath = "jrec/SomeTestClass/someTestMethod.yaml";
    File file = new File(tempFile(), expectedRelativePath);

    assertThat(file.exists(), is(true));
    assertThat(cassetteFromFile(file), is(cassette));
  }

  private Cassette cassetteFromFile(File file) throws IOException {
    byte[] encoded = Files.readAllBytes(file.toPath());
    String yaml = Charset.forName(JRecRuntTime.DEFAULT_CHARSET).decode(ByteBuffer.wrap(encoded)).toString();
    return Serializer.deserialize(yaml, Cassette.class);
  }
}
