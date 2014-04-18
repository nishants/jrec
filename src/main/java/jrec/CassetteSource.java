package jrec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;

import static jrec.Serializer.*;

@Component
public class CassetteSource {
  private final String cassettesHome;
  private final String platformPathSeparator;
  private Zipper zipper;
  private final static String CASSETTE_FILE_TYPE = ".yaml";
  private Cassette lastCassette;
  private static final String CASSETTE_ARCHIVE = "cassettes.zip";

  @Autowired
  public CassetteSource(@Value("#{systemProperties['vcr.cassettes.home']}") String cassettesHome,
                        @Value("#{systemProperties['file.separator']}") String fileSeparator,
                        Zipper zipper) {
    this.cassettesHome = cassettesHome;
    platformPathSeparator = fileSeparator;
    this.zipper = zipper;
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

  public void zipUp() throws IOException {
    zipper.zipFiles(file(cassettesHome),
        file(cassettesHome + platformPathSeparator + CASSETTE_ARCHIVE));
    eraseAll(file(cassettesHome), CASSETTE_ARCHIVE);

  }

  private void eraseAll(File inDir, String exceptFile) {
    FilenameFilter filter = filter(inDir, exceptFile);

    for (File file : inDir.listFiles(filter)) {
      if (file.isDirectory()) {
        deleteDir(file);
      } else {
        file.delete();
      }
    }
  }

  private void deleteDir(File dir) {
    for (File file : dir.listFiles()) {
      if (file.isDirectory()) {
        deleteDir(file);
      }
      file.delete();
    }
    dir.delete();
  }

  private FilenameFilter filter(final File inDir, final String exceptFile) {
    return new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return !(dir.equals(inDir) && name.equals(exceptFile));
      }
    };
  }

  private File file(String pathname) {
    return new File(pathname);
  }

  public void zipOut() {
  }

  private File fileFor(String cassetteLabel) {
    String relativePath = cassetteLabel.replaceAll("\\.", platformPathSeparator) + CASSETTE_FILE_TYPE;
    return new File(cassettesHome, relativePath);
  }

  private Cassette readFrom(File file) throws IOException {
    byte[] encoded = Files.readAllBytes(file.toPath());
    String yaml = Charset.forName(JRecRuntTime.DEFAULT_CHARSET).decode(ByteBuffer.wrap(encoded)).toString();
    return deserialize(yaml, Cassette.class);
  }
}
