package com.geeksaint.springvcr.maker;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HttpResponseMaker {
  public static final Property<ClientHttpResponse, String> statusCode = new Property<ClientHttpResponse, String>();
  public static final Property<ClientHttpResponse, String> headers = new Property<ClientHttpResponse, String>();
  public static final Property<ClientHttpResponse, String> body = new Property<ClientHttpResponse, String>();

  public static final Instantiator<ClientHttpResponse> ClientHttpResponse = new Instantiator<ClientHttpResponse>() {
    public ClientHttpResponse instantiate(final PropertyLookup<ClientHttpResponse> lookup){
      return new ClientHttpResponse(){
        @Override
        public HttpStatus getStatusCode() throws IOException {
          return HttpStatus.valueOf(lookup.valueOf(statusCode, "OK"));
        }

        @Override
        public int getRawStatusCode() throws IOException {
          return HttpStatus.valueOf(lookup.valueOf(statusCode, "OK")).value();
        }

        @Override
        public String getStatusText() throws IOException {
          return HttpStatus.valueOf(lookup.valueOf(statusCode, "OK")).getReasonPhrase();
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
          return new ByteArrayInputStream(lookup.valueOf(body, "RESPONSE").getBytes());
        }

        @Override
        public HttpHeaders getHeaders() {
          return new HttpHeaders();
        }
      };
    }
  };


}
