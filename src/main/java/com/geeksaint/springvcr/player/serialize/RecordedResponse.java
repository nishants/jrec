package com.geeksaint.springvcr.player.serialize;

import lombok.EqualsAndHashCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;

@EqualsAndHashCode

public class RecordedResponse implements ClientHttpResponse{
  private ClientHttpResponse httpResponse;

  public RecordedResponse(ClientHttpResponse httpResponse) {

    this.httpResponse = httpResponse;
  }

  public static RecordedResponse of(ClientHttpResponse httpResponse) {
    return new RecordedResponse(httpResponse);
  }

  @Override
  public HttpStatus getStatusCode() throws IOException {
    return null;
  }

  @Override
  public int getRawStatusCode() throws IOException {
    return 0;
  }

  @Override
  public String getStatusText() throws IOException {
    return null;
  }

  @Override
  public void close() {

  }

  @Override
  public InputStream getBody() throws IOException {
    return null;
  }

  @Override
  public HttpHeaders getHeaders() {
    return null;
  }
}
