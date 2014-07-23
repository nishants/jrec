package com.geeksaint.springvcr.maker;

import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import org.springframework.http.client.ClientHttpResponse;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;

public class RecordedResponseMaker {
  public static final Property<RecordedResponse, String> statusCode = new Property<RecordedResponse, String>();
  public static final Property<RecordedResponse, String> headers = new Property<RecordedResponse, String>();
  public static final Property<RecordedResponse, String> body = new Property<RecordedResponse, String>();

  public static final Instantiator<RecordedResponse> Response = new Instantiator<RecordedResponse>() {
    public RecordedResponse instantiate(final PropertyLookup<RecordedResponse> lookup){
      ClientHttpResponse response = make(a(HttpResponseMaker.ClientHttpResponse,
          with(HttpResponseMaker.statusCode, lookup.valueOf(statusCode, "OK")),
          with(HttpResponseMaker.body, lookup.valueOf(body, "RESPONSE"))
      ));
      return RecordedResponse.of(response);
    }
  };
}
