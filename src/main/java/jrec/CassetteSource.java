package jrec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static jrec.Serializer.*;

@Component
public class CassetteSource {
  private final String cassettesHome;
  private final String platformPathSeparator;
  private final static String CASSETTE_FILE_TYPE = ".yaml";
  private Cassette lastCassette;

  @Autowired
  public CassetteSource(@Value("#{systemProperties['vcr.cassettes.home']}") String cassettesHome,
                        @Value("#{systemProperties['file.separator']}") String fileSeparator) {
    this.cassettesHome = cassettesHome;
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
