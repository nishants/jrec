package com.geeksaint.springvcr.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Interceptor implements ClientHttpRequestInterceptor {

  @Autowired
  private ProxyServer server;

  @Override
  public ClientHttpResponse intercept(HttpRequest request,
                                      byte[] requestBody,
                                      ClientHttpRequestExecution execution)
      throws IOException {
    return server.service(request, requestBody, execution);
  }
}
