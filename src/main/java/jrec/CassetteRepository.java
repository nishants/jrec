package jrec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CassetteRepository {
  private CassetteSource cassetteSource;

  @Autowired
  public CassetteRepository(CassetteSource cassetteSource) {
    this.cassetteSource = cassetteSource;
  }

  public ClientHttpResponse record(HttpRequest request, ClientHttpResponse response, String testName) throws IOException {
    RecordedRequest recordedRequest = RecordedRequest.of(request);
    RecordedResponse recordedResponse = RecordedResponse.of(response);

    Cassette cassette = cassetteFor(testName);
    cassette.addTrack(recordedRequest, recordedResponse);
    cassetteSource.save(cassette);

    return recordedResponse;
  }

  public ClientHttpResponse responseFor(HttpRequest request, String testName) throws IOException {
    Cassette cassette = cassetteSource.cassetteFor(testName);
    return cassette == null ?
        null : responseFor(request, cassette);
  }

  private RecordedResponse responseFor(HttpRequest request, Cassette cassette) {
    return cassette.responseOf(RecordedRequest.of(request));
  }

  public Cassette cassetteFor(String cassetteName) {
    Cassette cassette = cassetteSource.cassetteFor(cassetteName);
    return cassette == null ? Cassette.forName(cassetteName): cassette;
  }

}
