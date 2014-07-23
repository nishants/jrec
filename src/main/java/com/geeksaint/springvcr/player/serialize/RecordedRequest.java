package com.geeksaint.springvcr.player.serialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import java.net.URI;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
public class RecordedRequest {
  private URI uri;
  private HttpMethod method;

  public RecordedRequest(HttpRequest request) {
    this.uri = request.getURI();
    this.method = request.getMethod();
  }

  public static RecordedRequest of(HttpRequest request) {
    return new RecordedRequest(request);
  }
}
