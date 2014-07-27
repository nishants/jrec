package com.geeksaint.springvcr.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Component
public class Interceptor implements ClientHttpRequestInterceptor {
  private final ProxyServer server;

  @Autowired
  public Interceptor(List<RestTemplate> restTemplates, ProxyServer server) {
    intercept(restTemplates);
    this.server = server;
  }

  private void intercept(List<RestTemplate> restTemplates) {
    if(restTemplates == null ) return;
    for(RestTemplate restTemplate : restTemplates){
      restTemplate.getInterceptors().add(this);
    }
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request,
                                      byte[] requestBody,
                                      ClientHttpRequestExecution execution)
      throws IOException {
    return server.service(request, requestBody, execution);
  }
}
