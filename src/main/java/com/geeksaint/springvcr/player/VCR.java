package com.geeksaint.springvcr.player;

import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Service
public class VCR {
  @Autowired
  private CassetteStore cassetteStore;

  public void record(HttpRequest request, ClientHttpResponse response){
    cassetteStore.record(RecordedRequest.of(request));
  }
  public ClientHttpResponse play(HttpRequest request){
    return cassetteStore.getResponseFor(RecordedRequest.of(request));
  }
}
