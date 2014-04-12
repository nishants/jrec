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

  public ClientHttpResponse record(HttpRequest request, ClientHttpResponse response) throws IOException {
    throw new RuntimeException();
  }

  public ClientHttpResponse responseFor(HttpRequest request, String nextTest) throws IOException {
    throw new RuntimeException();
  }

}
