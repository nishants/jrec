package jrec;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ZipperTest {
  @Test
  public void shouldZipFiles() throws IOException {
    Zipper zipper = new Zipper();
    File dir = new File("/Users/Nishant/Desktop/cassettes");
    File toFile = new File("/Users/Nishant/Desktop/cassettes/cassettes.zip");
    zipper.zipFiles(dir,toFile );
  }
}
