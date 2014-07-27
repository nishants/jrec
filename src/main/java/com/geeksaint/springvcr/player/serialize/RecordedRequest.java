package com.geeksaint.springvcr.player.serialize;

import lombok.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import java.net.URI;
import java.nio.charset.Charset;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecordedRequest {
  public static String DEFAULT_ENCODING = "UTF-8";

  private String body;
  private URI uri;
  private HttpMethod method;

  public RecordedRequest(HttpRequest request, byte[] requestBody) {
    this.uri = request.getURI();
    this.method = request.getMethod();
    this.body = new String(requestBody, Charset.forName(DEFAULT_ENCODING));
  }

  public static RecordedRequest of(HttpRequest request, byte[] requestBody) {
    return new RecordedRequest(request, requestBody);
  }
}
