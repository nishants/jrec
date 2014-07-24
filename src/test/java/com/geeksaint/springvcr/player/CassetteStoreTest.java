package com.geeksaint.springvcr.player;

import com.geeksaint.springvcr.player.serialize.YamlIO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static com.geeksaint.springvcr.maker.CassetteMaker.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CassetteStoreTest {


  private CassetteStore cassetteStore;
  private String cassetteLabel;

  @Before
  public void setUp() throws Exception {
    cassetteLabel = "com.geeksaint.springvcr.Test.testName";
    cassetteStore = new CassetteStore(testCassetteHomeDir());
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
