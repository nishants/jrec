package jrec;

import org.hamcrest.Matcher;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public interface RecordingListener {

  public void recorded(HttpRequest request, ClientHttpResponse response);
  public void failedToFindCassette(HttpRequest request);

  void failedToExecuteRequest(HttpRequest request);

  void errorReadingCassette(HttpRequest request, Throwable error);
}
