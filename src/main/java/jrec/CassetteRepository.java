package jrec;

import lombok.Setter;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

public class CassetteRepository {
  private Disk disk;
  private String currentTest;
  private Cassette cassette;


  public CassetteRepository(Disk disk) {
    this.disk = disk;
  }

  public ClientHttpResponse record(HttpRequest request, ClientHttpResponse response, String cassetteName) throws IOException {
    RecordedRequest recordedRequest = RecordedRequest.of(request);
    RecordedResponse recordedResponse = RecordedResponse.of(response);
    Cassette cassette = getCassette();
    cassette.add(recordedRequest, recordedResponse);
//    return disk.saveToTestDir();
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
