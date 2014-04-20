package com.geeksaint.spring.vcr;

import com.geeksaint.spring.vcr.Zipper;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ZipperTest {

  private String targetPath;
  private File outputFile;
  private Zipper zipper;

  @Before
  public void setup() {
    targetPath = getClass().getResource("/fixtures/cassettes").getFile();
    outputFile = new File(tempFile(), "cassettes.zip");
    zipper = new Zipper();
  }

  @Test
  public void shouldZipFiles() throws IOException {
    outputFile.delete();
    Zipper zipper = new Zipper();
    File target = new File(targetPath);
    zipper.zipFiles(target, outputFile);

    assertThat(outputFile.exists(), is(true));
  }

  @Test
  public void shouldUnZipToDestination() throws IOException {
    File zipFile = file(getClass().getResource("/fixtures/cassettes").getFile() + "/cassettes.zip");
    File target = file(tempFile());

    File expectedOutput = file(target, "cassettes/jrec/TestClassName/methodName.yaml");
    expectedOutput.delete();

    assertThat(expectedOutput.exists(), is(false));
    assertThat(zipFile.exists(), is(true));

    zipper.unZip(zipFile, target);

    assertThat(expectedOutput.exists(), is(true));
  }

  private File file(File target, String name) {
    return new File(target, name);
  }

  private File file(String file) {
    return new File(file);
  }

  private String tempFile() {
    return System.getProperty("java.io.tmpdir");
  }
}
