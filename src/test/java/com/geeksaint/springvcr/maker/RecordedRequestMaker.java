package com.geeksaint.springvcr.maker;

import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import org.springframework.http.HttpRequest;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;

public class RecordedRequestMaker {
  public static final Property<RecordedRequest, String> body = new Property<RecordedRequest, String>();
  public static final Property<RecordedRequest, String> method = new Property<RecordedRequest, String>();
  public static final Property<RecordedRequest, String> uri = new Property<RecordedRequest, String>();

  public static final Instantiator<RecordedRequest> Request = new Instantiator<RecordedRequest>() {
    public RecordedRequest instantiate(final PropertyLookup<RecordedRequest> lookup) {
      HttpRequest request = make(a(HttpRequestMaker.HttpRequest,
          with(HttpRequestMaker.uri, lookup.valueOf(uri, "http://server/app/model")),
          with(HttpRequestMaker.method, lookup.valueOf(method, "POST")),
          with(HttpRequestMaker.body, lookup.valueOf(body, "REQUEST"))
      ));

      return com.geeksaint.springvcr.player.serialize.RecordedRequest.of(request);
    }
  };
}
