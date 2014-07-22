package com.geeksaint.springvcr.player;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class VCR {
  public void record(HttpRequest request, byte[] requestBody, ClientHttpResponse response){}
  public void record(HttpRequest request){}
  public ClientHttpResponse play(HttpRequest request, byte[] requestBody){
    return null;
  }
}
