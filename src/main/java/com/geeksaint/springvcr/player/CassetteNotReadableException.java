package com.geeksaint.springvcr.player;

import java.io.IOException;

public class CassetteNotReadableException extends RuntimeException {
  public CassetteNotReadableException(IOException e) {
    super(e);
  }
}
