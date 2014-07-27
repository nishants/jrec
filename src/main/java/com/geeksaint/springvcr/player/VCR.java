package com.geeksaint.springvcr.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VCR {
  @Autowired
  private CassetteStore cassetteStore;
  private Cassette cassette;

  // Load next cassette
  public void loadCassette(String cassetteLabel){
    cassette = cassetteStore.ofLabel(cassetteLabel);
  }

  // Cassette no longer needed.
  public void eject() throws IOException {
    cassetteStore.save(cassette);
  }

  public void record(HttpRequest httpRequest, byte[] requestBody, ClientHttpResponse httpResponse) throws IOException {
    cassette.record(RecordedRequest.of(httpRequest, requestBody), RecordedResponse.of(httpResponse));
  }
  public ClientHttpResponse play(HttpRequest httpRequest, byte[] requestBody){
    return cassette.responseOf(RecordedRequest.of(httpRequest, requestBody));
  }
}
