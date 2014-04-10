package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

public class CassetteRepository {
  private Disk disk;

  public CassetteRepository(Disk disk) {
    this.disk = disk;
  }

  public ClientHttpResponse record(HttpRequest request, ClientHttpResponse response) throws IOException {
    RecordedResponse recordedResponse = new RecordedResponse(response);
    Cassette cassette = new Cassette(new RecordedRequest(request), recordedResponse);
    disk.saveToTestDir(cassette, nameFor(cassette));
    return recordedResponse;
  }

  public ClientHttpResponse responseFor(HttpRequest request) throws IOException {
    disk.readAllFromTestDir(Cassette.class);
    List<Cassette> cassettes = disk.readAllFromTestDir(Cassette.class);
    return getResponseFrom(request, cassettes);
  }

  private ClientHttpResponse getResponseFrom(HttpRequest request, List<Cassette> cassettes) {
    for (Cassette cassette : cassettes) {
      if (cassette.of(request)) return cassette.getResponse();
    }
    return null;
  }

  private String nameFor(Cassette cassette) {
    return cassette.getRequest().getUri().toString().replaceAll("/", "-") + ".yaml";
  }

  private String cassetteNameFor(HttpRequest request) throws IOException {
    return request.getURI().toString().replaceAll("/", "-") + ".yaml";
  }

}
