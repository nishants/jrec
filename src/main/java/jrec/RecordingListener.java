package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public interface RecordingListener {

  public void recorded(HttpRequest request, ClientHttpResponse response);
  public void failedToFindCassette(HttpRequest request);

  void failedToExecuteRequest(HttpRequest request);

  void errorReadingCassette(HttpRequest request, Throwable error);

  void readingFromCassette(HttpRequest request, ClientHttpResponse recordedResponse);

  void failedToCreateCassette(HttpRequest request, ClientHttpResponse recordedResponse, Throwable cassetteError);
}
