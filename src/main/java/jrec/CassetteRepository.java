package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CassetteRepository {
  private CassetteSource cassetteSource;

  public CassetteRepository(CassetteSource cassetteSource) {
    this.cassetteSource = cassetteSource;
  }

  public ClientHttpResponse record(HttpRequest request, ClientHttpResponse response, String testName) throws IOException {
    RecordedRequest recordedRequest = RecordedRequest.of(request);
    RecordedResponse recordedResponse = RecordedResponse.of(response);

    Cassette cassette = cassetteFor(testName);
    cassette.record(recordedRequest, recordedResponse);
    cassetteSource.save(cassette);

    return recordedResponse;
  }

  public ClientHttpResponse responseFor(HttpRequest request, String testName) throws IOException {
    throw new RuntimeException();
  }

  public Cassette cassetteFor(String cassetteName) {
    return cassetteSource.getCassette(cassetteName);
  }

}
