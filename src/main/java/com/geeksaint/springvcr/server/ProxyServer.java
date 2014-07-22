package com.geeksaint.springvcr.server;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class ProxyServer {
  ClientHttpResponse service(HttpRequest httpRequest,
                             byte[] bytes,
                             ClientHttpRequestExecution clientHttpRequestExecution) {
    return null;
  }
}
