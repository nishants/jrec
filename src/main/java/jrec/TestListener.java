package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

public interface TestListener {

  public void nextTest(String testName);
}
