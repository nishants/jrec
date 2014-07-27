package com.geeksaint.springvcr.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static com.geeksaint.springvcr.player.serialize.CassetteIO.DEFAULT_ENCODING;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecordedResponse implements ClientHttpResponse{
  private HttpStatus statusCode;
  private int rawStatusCode;
  private HttpHeaders headers;
  private String content;
  private String statusText;

  public RecordedResponse(ClientHttpResponse httpResponse) throws IOException {
    setResponse(httpResponse);
  }

  private void setResponse(ClientHttpResponse response) throws IOException {
    statusCode = response.getStatusCode();
    rawStatusCode = response.getRawStatusCode();
    statusText = response.getStatusText();
    headers = response.getHeaders();
    setBody(response.getBody());
  }

  private void setBody(InputStream stream) throws IOException {
    this.content = fromStream(stream, DEFAULT_ENCODING);
  }

  private String fromStream(InputStream inputStream, String encoding) throws IOException {
    StringWriter writer = new StringWriter();
    IOUtils.copy(inputStream, writer, encoding);
    return writer.toString();
  }

  public static RecordedResponse of(ClientHttpResponse httpResponse) throws IOException {
    return new RecordedResponse(httpResponse);
  }

  public void setHeaders(Map<? extends String, ? extends List<String>> values) {
    headers = new HttpHeaders();
    headers.putAll(values);
  }

  @Override
  @JsonIgnore
  public InputStream getBody() throws IOException {
    return new ByteArrayInputStream(content.getBytes());
  }

  @Override
  public void close() {}
}
