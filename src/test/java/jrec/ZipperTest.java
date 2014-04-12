package jrec;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ZipperTest {

  private String targetPath;
  private File outputFile;

  @Before
  public void setup() {
    targetPath = getClass().getResource("/fixtures/cassettes").getFile();
    outputFile = new File(tempFile(), "cassettes.zip");
  }

  @Test
  public void shouldZipFiles() throws IOException {
    outputFile.delete();
    Zipper zipper = new Zipper();
    File target = new File(targetPath);
    zipper.zipFiles(target, outputFile);

    assertThat(outputFile.exists(), is(true));
  }

  private String tempFile() {
    return System.getProperty("java.io.tmpdir");
  }
}
