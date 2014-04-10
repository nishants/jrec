package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static jrec.VCRMode.PLAY;
import static jrec.VCRMode.valueOf;

public class Recorder implements ClientHttpRequestInterceptor {
  private final VCRMode mode;
  private CassetteRepository cassetteRepository;

  public Recorder(CassetteRepository cassetteRepository, VCRMode mode ) {
    this.mode = mode;
    JRec.setMode(mode);
    this.cassetteRepository = cassetteRepository;
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    if (playing()) return cassetteRepository.responseFor(request);

    ClientHttpResponse response = execution.execute(request, body);
    return cassetteRepository.record(request, response);
  }

  private boolean playing() {
    return mode == PLAY;
  }
}
