package com.geeksaint.springvcr.player.serialize;

import com.geeksaint.springvcr.player.Cassette;

import java.io.File;
import java.io.IOException;

import static com.geeksaint.springvcr.player.serialize.YamlIO.read;
import static com.geeksaint.springvcr.player.serialize.YamlIO.writeYamlTo;

public class CassetteIO {
  public final static String DEFAULT_ENCODING = "UTF-8";

  public static Cassette readFrom(File cassetteFile) {
    Cassette cassette = null;
    try {
      cassette = read(cassetteFile, Cassette.class);
    }catch (IOException e) {
      throw new CassetteNotReadableException(e);
    }
    return cassette;
  }

  public static void write(File cassetteFile, Cassette cassette) throws IOException {
    cassetteFile.getParentFile().mkdirs();
    writeYamlTo(cassetteFile, cassette);
  }
}
