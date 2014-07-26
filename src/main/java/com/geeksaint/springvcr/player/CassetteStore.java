package com.geeksaint.springvcr.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import static com.geeksaint.springvcr.player.Cassette.create;
import static com.geeksaint.springvcr.player.CassetteLabel.*;
import static com.geeksaint.springvcr.player.serialize.CassetteIO.readFrom;
import static com.geeksaint.springvcr.player.serialize.CassetteIO.write;

@Service
public class CassetteStore {
  private String cassettesHome;

  @Autowired
  public CassetteStore(@Value("#{systemProperties['spring.vcr.cassettes.home']}") String cassettesHome){
    this.cassettesHome = cassettesHome;
  }

  public Cassette ofLabel(String label){
    Cassette cassette = readCassette(label);
    return cassette == null ? create(label) : cassette;
  }

  private Cassette readCassette(String label){
    File cassetteFile = fileFor(label);
    return cassetteFile.exists() ? readFrom(cassetteFile) : null;
  }

  private File fileFor(String label) {
    return new File(cassettesHome, fileForLabel(label));
  }

  public void save(Cassette cassette) throws IOException {
    File cassetteFile = fileFor(cassette);
    write(cassetteFile, cassette);
  }

  private File fileFor(Cassette cassette) {
    return new File(cassettesHome, cassette.getCassetteLabel().toFileName());
  }
}
