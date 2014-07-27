package com.geeksaint.springvcr.player.serialize;

import java.io.IOException;

public class CassetteNotReadableException extends RuntimeException {
  public CassetteNotReadableException(IOException e) {
    super(e);
  }
}
