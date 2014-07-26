package com.geeksaint.springvcr.player;

import com.geeksaint.springvcr.maker.RecordedRequestMaker;
import com.geeksaint.springvcr.maker.RecordedResponseMaker;
import com.geeksaint.springvcr.player.serialize.CassetteIO;
import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.geeksaint.springvcr.player.serialize.YamlIO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static com.geeksaint.springvcr.maker.CassetteMaker.*;
import static com.geeksaint.springvcr.player.CassetteLabel.fileForLabel;
import static com.geeksaint.springvcr.player.serialize.CassetteIO.readFrom;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CassetteStoreTest {


  private CassetteStore cassetteStore;
  private String cassetteLabel;
  private RecordedRequest recordedRequest;
  private RecordedResponse recordedResponse;

  @Before
  public void setUp() throws Exception {
    cassetteLabel = "com.geeksaint.springvcr.Test.testName";
    cassetteStore = new CassetteStore(testCassetteHomeDir());
    recordedRequest = make(a(RecordedRequestMaker.Request));
    recordedResponse = make(a(RecordedResponseMaker.Response));

  }

  @Test
  public void shouldReadCassetteFromCassettesHomeByCassetteLabel() throws IOException {
    Cassette cassette = make(a(Cassette, with(label, cassetteLabel)));
    createTestFileFor(cassette);

    assertThat(cassetteStore.ofLabel(cassetteLabel), is(cassette));
  }

  @Test
  public void shouldCreateNewBlankCassetteIfFileDoesNotExist(){
    Cassette cassette = cassetteStore.ofLabel("nonExistingcasstteLabel");
    assertThat(cassette.isBlank(), is(true));
  }

  @Test
  public void shouldSaveCassette() throws IOException {
    String cassetteLabel = "com.geeksaint.TestClass.testName.nonExistingcasstteLabel";
    Cassette cassette = cassetteStore.ofLabel(cassetteLabel);
    cassette.record(recordedRequest, recordedResponse);
    File expectedCassetteFile = new File(
        testCassetteHomeDir(),
        fileForLabel(cassetteLabel)
    );
    expectedCassetteFile.delete();

    cassetteStore.save(cassette);

    assertThat(readFrom(expectedCassetteFile), is(cassette));
  }

  private void createTestFileFor(Cassette cassette) throws IOException {
    File cassetteFile = new File(testCassetteHomeDir(), cassette.getCassetteLabel().toFileName());
    cassetteFile.getParentFile().mkdirs();
    cassetteFile.delete();
    YamlIO.writeYamlTo(cassetteFile, cassette);
  }

  private String testCassetteHomeDir() {
    return System.getProperty("java.io.tmpdir") + "/springvcr/cassettes";
  }
}
