package jrec;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CassetteSource {
  private final String platformPathSeparator;
  private final boolean archive;
  private final Zipper zipper;

  public CassetteSource(String cassettesDir, Zipper zipper, Boolean archive, String fileSeparator) {
    platformPathSeparator = fileSeparator;
    this.zipper = zipper;
    this.archive = archive;
  }

  public void save(Object cassette) throws IOException {

  }

  public Cassette cassetteFor(String cassetteName) {
    return null;
  }
}
