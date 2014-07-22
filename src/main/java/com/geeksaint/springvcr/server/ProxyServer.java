package com.geeksaint.springvcr.server;

import com.geeksaint.springvcr.player.VCR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.geeksaint.springvcr.server.ServerMode.RECORD;
import static com.geeksaint.springvcr.server.ServerMode.valueOf;

@Component
public class ProxyServer {

  private ServerMode mode;
  private VCR vcr;

  @Autowired
  public ProxyServer(VCR vcr,
                     @Value("#{systemProperties['spring.vcr.mode']}")
                     String mode) {
    this.vcr = vcr;
    setMode(mode);
  }

  public ClientHttpResponse service(HttpRequest request, byte[] requestBody,
                                    ClientHttpRequestExecution execution) throws IOException {

    return mode.isRecording() ? executed(request, requestBody, execution) : recorded(request, requestBody);
  }

  private ClientHttpResponse recorded(HttpRequest request, byte[] requestBody) {
    return vcr.play(request);
  }

  private ClientHttpResponse executed(HttpRequest request, byte[] requestBody, ClientHttpRequestExecution execution) throws IOException {
    ClientHttpResponse response;
    response = execution.execute(request, requestBody);
    vcr.record(request, response);
    return response;
  }

  private void setMode(String mode) {
    String currentMode = mode == null ? RECORD.name() : mode.toUpperCase();
    this.mode = valueOf(currentMode);
  }
}
