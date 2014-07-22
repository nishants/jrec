package com.geeksaint.springvcr.server;

import lombok.Getter;

@Getter
public enum ServerMode {
  PLAY(false),
  RECORD(true);

  //if false, request must not be forwarded to remote.
  private boolean recording;

  ServerMode(boolean record) {
    this.recording = record;
  }
}
