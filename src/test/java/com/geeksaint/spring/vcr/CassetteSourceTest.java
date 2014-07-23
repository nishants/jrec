package com.geeksaint.spring.vcr;

import com.geeksaint.spring.vcr.maker.RecordedRequestMaker;
import com.geeksaint.spring.vcr.maker.RecordedResponseMaker;
import com.geeksaint.spring.vcr.serialize.Cassette;
import com.geeksaint.springvcr.player.serialize.YamlIO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static com.natpryce.makeiteasy.MakeItEasy.*;
import static com.geeksaint.spring.vcr.maker.RecordedRequestMaker.*;
import static com.geeksaint.spring.vcr.maker.RecordedResponseMaker.*;
import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CassetteSourceTest {
  private String testName;
  private String outputDir;
  private CassetteSource source;

  @Before
  public void setup() {
    testName = "jrec.SomeTestClass.someTestMethod";
    source = new CassetteSource(tempFile(), null);
  }

  @Test
  public void shouldSerializeFromCurrentTestDirectory() throws IOException {
    String projectRoot = fixture();
    source = new CassetteSource(projectRoot, null);

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

  @Test
  public void shouldZipCassettesAndDeleteThem() throws IOException {
    String cassetteHome = tempFile() + "/cassettes";
    copyAll(fixture(), cassetteHome);

    source = new CassetteSource(cassetteHome, new Zipper());

    source.zipUp();

    //must erase all files
    assertThat(file(cassetteHome + "/cassettes.zip").exists(), is(true));
    assertThat(filesAndSubDir(file(cassetteHome)).size(), is(1));
  }

  private List<String> filesAndSubDir(File dir) {
    List<String> files = new ArrayList<String>();
    for (File file : dir.listFiles()) {
      if (file.isDirectory()) files.addAll(filesAndSubDir(file.getAbsoluteFile()));
      files.add(file.getAbsolutePath());
    }

    return files;
  }

  private String fixture() {
    return getClass().getResource("/fixtures/cassettes").getFile();
  }

  private void copyAll(String fromDir, String toDir) throws IOException {
    File srcDir = file(fromDir);
    File destDir = file(toDir);

    destDir.mkdirs();

    copyDirectory(srcDir, destDir);
  }

  private File file(String fromDir) {
    return new File(fromDir);
  }

  private Cassette cassetteFromFile(File file) throws IOException {
    byte[] encoded = Files.readAllBytes(file.toPath());
    String yaml = Charset.forName(SpringVcrRuntime.DEFAULT_CHARSET).decode(ByteBuffer.wrap(encoded)).toString();
    return YamlIO.read(yaml, Cassette.class);
  }

  private String tempFile() {
    return System.getProperty("java.io.tmpdir");
  }

}
