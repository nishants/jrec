package com.geeksaint.springvcr.player.serialize;

import lombok.EqualsAndHashCode;
import org.springframework.http.HttpRequest;

@EqualsAndHashCode
public class RecordedRequest {
  private HttpRequest request;

  public RecordedRequest(HttpRequest request) {
    this.request = request;
  }

  public static RecordedRequest of(HttpRequest request) {
    return new RecordedRequest(request);
  }
}
