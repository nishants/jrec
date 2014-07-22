package com.geeksaint.springvcr.maker;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import java.net.URI;

public class HttpRequestMaker {
  public static final Property<HttpRequest, String> body = new Property<HttpRequest, String>();
  public static final Property<HttpRequest, String> method = new Property<HttpRequest, String>();
  public static final Property<HttpRequest, String> uri = new Property<HttpRequest, String>();

  public static final Instantiator<HttpRequest> HttpRequest = new Instantiator<HttpRequest>() {
    public HttpRequest instantiate(final PropertyLookup<HttpRequest> lookup){
      return new HttpRequest() {
        @Override
        public HttpMethod getMethod() {
          return (HttpMethod.valueOf(lookup.valueOf(method, "POST")));
        }

        @Override
        public URI getURI() {
          return URI.create(lookup.valueOf(uri, "http://server/app/model"));
        }

        @Override
        public HttpHeaders getHeaders() {
          return new HttpHeaders();
        }
      };
    }
  };


}
