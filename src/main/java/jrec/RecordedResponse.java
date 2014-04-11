package jrec;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.*;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"body", "headers"})
public class RecordedResponse implements ClientHttpResponse {

  private HttpStatus statusCode;
  private int rawStatusCode;
  private String statusText;
  private String encoding = JRecRuntTime.DEFAULT_CHARSET;
  private HttpHeaders headers;
  private String content;

  @JsonIgnore
  private InputStream body;

  public RecordedResponse(ClientHttpResponse response) throws IOException {
    setResponse(response);
  }

  private void setResponse(ClientHttpResponse response) throws IOException {
    statusCode = response.getStatusCode();
    rawStatusCode = response.getRawStatusCode();
    statusText = response.getStatusText();
    headers = response.getHeaders();
    setBody(response.getBody());
  }

  private void setBody(InputStream stream) throws IOException {
    this.content = fromStream(stream, encoding);
  }

  @Override
  public HttpStatus getStatusCode() {
    return statusCode;
  }

  @Override
  public int getRawStatusCode() {
    return rawStatusCode;
  }

  @Override
  public String getStatusText() {
    return statusText;
  }

  @Override
  public void close() {
  }

  @Override
  public InputStream getBody() throws UnsupportedEncodingException {
    return toStream(content, encoding);
  }

  private ByteArrayInputStream toStream(String content, String encoding) throws UnsupportedEncodingException {
    return new ByteArrayInputStream(content.getBytes(encoding));
  }

  @Override
  public HttpHeaders getHeaders() {
    return headers;
  }

  public void setHeaders(Map<? extends String, ? extends List<String>> values) {
    headers = new HttpHeaders();
    headers.putAll(values);
  }

  private String fromStream(InputStream inputStream, String encoding) throws IOException {
    StringWriter writer = new StringWriter();
    IOUtils.copy(inputStream, writer, encoding);
    return writer.toString();
  }
}
