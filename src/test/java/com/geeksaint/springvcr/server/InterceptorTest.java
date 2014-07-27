package com.geeksaint.springvcr.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InterceptorTest {

  @Mock
  private ProxyServer proxyServer;

  @InjectMocks
  private Interceptor interceptor;

  @Test
  public void shouldBeAClientHttpRequestInterceptor(){
    assertThat(interceptor, is(instanceOf(ClientHttpRequestInterceptor.class)));
  }

  @Test
  public void shouldBindToRestTemplates(){
    RestTemplate restTemplate = mock(RestTemplate.class);
    ArrayList<ClientHttpRequestInterceptor> interceptorList = new ArrayList<ClientHttpRequestInterceptor>();
    when(restTemplate.getInterceptors()).thenReturn(interceptorList);

    Interceptor interceptor = new Interceptor(asList(restTemplate), proxyServer);

    assertThat(interceptorList, hasItem((ClientHttpRequestInterceptor)interceptor));
  }

  @Test
  public void shouldInterceptRequestAndReturnResponseFromServer() throws IOException {
    HttpRequest request = mock(HttpRequest.class);
    ClientHttpResponse response = mock(ClientHttpResponse.class);
    ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
    byte[] requestBody = "REQUEST_BODY" .getBytes();

    when(proxyServer.service(request, requestBody, execution)).thenReturn(response);

    assertThat(interceptor.intercept(request, requestBody, execution), is(response));
  }
}
