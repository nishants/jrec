package jrec;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static jrec.Serializer.*;

public class CassetteSource {
  private final String cassettesHome;
  private final String platformPathSeparator;
  private final boolean archive;
  private final Zipper zipper;
  private final static String CASSETTE_FILE_TYPE = ".yaml";
  private Cassette lastCassette;

  public CassetteSource(String cassettesHome, Zipper zipper, Boolean archive, String fileSeparator) {
    this.cassettesHome = cassettesHome;
    this.zipper = zipper;
    this.archive = archive;
    platformPathSeparator = fileSeparator;
  }

  public synchronized Cassette cassetteFor(String testName) {
    try {
      return readFrom(fileFor(testName));
    } catch (IOException e) {
      return null;
    }
  }

  public synchronized void save(Cassette cassette) throws IOException {
    File file = fileFor(cassette.getLabel());
    file.getParentFile().mkdirs();
    file.delete();
    serializeToFile(file, cassette);
  }

  private File fileFor(String cassetteLabel) {
    String relativePath = cassetteLabel.replaceAll("\\.", platformPathSeparator)+ CASSETTE_FILE_TYPE;
    return new File(cassettesHome, relativePath);
  }

  private Cassette readFrom(File file) throws IOException {
    byte[] encoded = Files.readAllBytes(file.toPath());
    String yaml = Charset.forName(JRecRuntTime.DEFAULT_CHARSET).decode(ByteBuffer.wrap(encoded)).toString();
    return deserialize(yaml, Cassette.class);
  }
}
