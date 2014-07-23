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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseMaker {
  public static final Property<ClientHttpResponse, String> statusCode = new Property<ClientHttpResponse, String>();
  public static final Property<ClientHttpResponse, Map<String, List<String>>> headers = new Property<ClientHttpResponse, Map<String, List<String>>>();
  public static final Property<ClientHttpResponse, String> body = new Property<ClientHttpResponse, String>();

  public static final Instantiator<ClientHttpResponse> ClientHttpResponse = new Instantiator<ClientHttpResponse>() {
    public ClientHttpResponse instantiate(final PropertyLookup<ClientHttpResponse> lookup){
      final HttpHeaders httpHeaders = new HttpHeaders();
      Map<String, List<String>> headerValues = lookup.valueOf(headers, new HashMap<String, List<String>>());
      httpHeaders.putAll(headerValues);
      final ByteArrayInputStream body = new ByteArrayInputStream(lookup.valueOf(HttpResponseMaker.body, "RESPONSE").getBytes());
      final String status = HttpStatus.valueOf(lookup.valueOf(statusCode, "OK")).getReasonPhrase();

      return new ClientHttpResponse(){
        @Override
        public HttpStatus getStatusCode() throws IOException {
          return HttpStatus.valueOf(status);
        }

        @Override
        public int getRawStatusCode() throws IOException {
          return HttpStatus.valueOf(status).value();
        }

        @Override
        public String getStatusText() throws IOException {
          return status;
        }

        @Override
        public void close() {

        }

        @Override
        public InputStream getBody() throws IOException {
          return body;
        }

        @Override
        public HttpHeaders getHeaders() {
          return httpHeaders;
        }
      };
    }
  };


}
