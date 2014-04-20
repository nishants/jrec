package com.geeksaint.spring.vcr.maker;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import com.geeksaint.spring.vcr.RecordedRequest;
import org.springframework.http.HttpMethod;

import java.net.URI;

public class RecordedRequestMaker {
  public static final Property<RecordedRequest, String> uri = new Property();
  public static final Property<RecordedRequest, String> method = new Property();
  public static final Property<RecordedRequest, String> content = new Property();

  public static final Instantiator<RecordedRequest> Request = new Instantiator<RecordedRequest>() {
    public RecordedRequest instantiate(PropertyLookup<RecordedRequest> lookup) {
      RecordedRequest recordedRequest = new RecordedRequest();
      recordedRequest.setUri(URI.create(lookup.valueOf(uri, "http:localhost:8080/myapp")));
      recordedRequest.setMethod(HttpMethod.valueOf(lookup.valueOf(method, "POST")));
      recordedRequest.setContent(lookup.valueOf(content, "request body"));
      return recordedRequest;
    }
  };
}
