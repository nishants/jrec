package com.geeksaint.springvcr.player.serialize;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Track {
  private RecordedRequest request;
  private RecordedResponse response;

  public Track(RecordedRequest request, RecordedResponse response) {
    this.request = request;
    this.response = response;
  }

  public static Track of(RecordedRequest request, RecordedResponse response) {
    return new Track(request, response);
  }
}
