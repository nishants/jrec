package jrec;

public enum VCRMode {
  RECORD,
  PLAY_RECORD,
  PLAY;

  public boolean recording(){
    return this == RECORD || this == PLAY_RECORD;
  }
  public boolean playing(){
    return this == PLAY_RECORD || this == PLAY_RECORD;
  }
}
