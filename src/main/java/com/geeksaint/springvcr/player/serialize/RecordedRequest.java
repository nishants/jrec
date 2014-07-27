package com.geeksaint.springvcr.player.serialize;

import lombok.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import java.net.URI;
import java.nio.charset.Charset;

import static com.geeksaint.springvcr.player.serialize.CassetteIO.DEFAULT_ENCODING;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecordedRequest {
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
