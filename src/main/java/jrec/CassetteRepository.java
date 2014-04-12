package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class CassetteRepository {
  private CassetteReader cassetteReader;
  private Cassette cassette;


  public CassetteRepository(CassetteReader cassetteReader) {
    this.cassetteReader = cassetteReader;
  }

  public ClientHttpResponse record(HttpRequest request, ClientHttpResponse response, String cassetteName) throws IOException {
    RecordedRequest recordedRequest = RecordedRequest.of(request);
    RecordedResponse recordedResponse = RecordedResponse.of(response);
    Cassette cassette = getCassette();
    cassette.add(recordedRequest, recordedResponse);
//    return cassetteReader.saveToTestDir();
    return null;
  }

  public ClientHttpResponse responseFor(HttpRequest request, String nextTest) throws IOException {
    throw new RuntimeException();
  }

  public Cassette getCassette() {
    return cassette;
  }

  public void setCassette(Cassette cassette) {
    this.cassette = cassette;
  }
}
