package jrec;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class CassetteSource {
  private final String cassettesHome;
  private final String platformPathSeparator;
  private final boolean archive;
  private final Zipper zipper;
  private Cassette lastCassette;

  public CassetteSource(String cassettesHome, Zipper zipper, Boolean archive, String fileSeparator) {
    this.cassettesHome = cassettesHome;
    this.zipper = zipper;
    this.archive = archive;
    platformPathSeparator = fileSeparator;
  }

  public synchronized void save(Cassette cassette) throws IOException {
    File file = new File(dir(cassette), fileName(cassette));
    file.getParentFile().mkdirs();
    PrintWriter out = new PrintWriter(file);
    String yaml = Serializer.serialize(cassette);
    out.println(yaml);
    out.flush();
    out.close();
  }

  private String fileName(Cassette cassette) {
    return "cassette.yaml";
  }

  private File dir(Cassette cassette) {
    return new File("/Users/Nishant/Desktop/cassettes");
  }

  private void loadCassette(String label) {


  }

  public synchronized Cassette cassetteFor(String cassetteName) {
    return null;
  }
}
