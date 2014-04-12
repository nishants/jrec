package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

public interface RecordingListener {

  public void recorded(HttpRequest request, ClientHttpResponse response);
  public void failedToFindCassette(HttpRequest request);

  void failedToExecuteRequest(HttpRequest request);
}
