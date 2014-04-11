package jrec;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URI;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class RecordedRequest {
  private URI uri;
  private HttpMethod method;
  private String content;

  public RecordedRequest(HttpRequest request) {
    uri = request.getURI();
    method = request.getMethod();
  }

  public void setBody(byte[] data) throws UnsupportedEncodingException {
    content = new String(data, JRecRuntTime.DEFAULT_CHARSET);
  }
}
