package com.geeksaint.springvcr.server;

import com.geeksaint.springvcr.player.VCR;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProxyServerTest {
  @Mock
  private VCR vcr;
  private HttpRequest request;
  private ClientHttpResponse response;
  private ClientHttpRequestExecution execution;
  private byte[] requestBody;

  @Before
  public void setUp() throws Exception {
    request = mock(HttpRequest.class);
    response = mock(ClientHttpResponse.class);
    execution = mock(ClientHttpRequestExecution.class);
    requestBody = "REQUEST_BODY".getBytes();
  }

  @Test
  public void shouldExecuteAndRecordRequestInRecordMode() throws IOException {
    ProxyServer server = new ProxyServer(vcr, "record");
    when(execution.execute(request, requestBody)).thenReturn(response);

    assertThat(server.service(request, requestBody, execution), is(response));
    verify(vcr).record(request, response);
  }
  @Test
  public void shouldReturnRecordedResponseInPlayMode() throws IOException {
    ProxyServer server = new ProxyServer(vcr, "play");
    when(vcr.play(request)).thenReturn(response);

    assertThat(server.service(request, requestBody, execution), is(response));
    verify(execution, never()).execute(request, requestBody);
    verify(vcr, never()).record(request, response);
  }

  @Test
  public void defaultModeMustBeRecording() throws IOException {
    ProxyServer server = new ProxyServer(vcr, null);
    when(execution.execute(request, requestBody)).thenReturn(response);

    assertThat(server.service(request, requestBody, execution), is(response));
    verify(vcr).record(request, response);
  }
}
