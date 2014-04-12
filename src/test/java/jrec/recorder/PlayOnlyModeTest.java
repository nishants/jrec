package jrec.recorder;

import jrec.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayOnlyModeTest {
  private byte[] requestBody;
  private ClientHttpResponse response;
  private ClientHttpRequest request;

  private ClientHttpRequestExecution clientHttpRequestExecution;
  private CassetteRepository cassetteRepository;
  private RecordingListener recordingListener;
  private VCRMode playOnlyMode;

  @Rule public ExpectedException expectedException = ExpectedException.none();

  private ClientHttpResponse recordedResponse;
  private Recorder recorder;

  @Before
  public void setUp() throws Exception {
    requestBody = new byte[0];
    response = mock(ClientHttpResponse.class);
    request = mock(ClientHttpRequest.class);
    recordedResponse = mock(RecordedResponse.class);

    clientHttpRequestExecution = mock(ClientHttpRequestExecution.class);
    cassetteRepository = mock(CassetteRepository.class);
    recordingListener = mock(RecordingListener.class);

    when(cassetteRepository.record(request, response)).thenThrow(RuntimeException.class);

    playOnlyMode = VCRMode.PLAY;
    recorder = new Recorder(cassetteRepository, playOnlyMode);
    recorder.addRecordingListener(recordingListener);
  }

  @Test
  public void shouldInterceptPlayFromCassetteInPlayMode() throws IOException {
    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
    when(cassetteRepository.responseFor(request)).thenReturn(recordedResponse);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);
    assertThat(actualResponse, is(recordedResponse));
    assertThat(actualResponse, is(recordedResponse));
    verifyNotExecuted(cassetteRepository, clientHttpRequestExecution);
  }

  @Test
  public void shouldReturnNullResponseIfCassetteNotFoundAndNotify() throws IOException {
    when(cassetteRepository.responseFor(request)).thenReturn(null);
    ClientHttpResponse response = recorder.intercept(request, requestBody, clientHttpRequestExecution);
    assertThat(response, is(nullValue()));
    verifyNotExecuted(cassetteRepository, clientHttpRequestExecution);
  }

  @Test
  public void shouldReturnNullResponseIfErrorReadingCassette() throws IOException {
    IOException cassetteError = new IOException();
    when(cassetteRepository.responseFor(request)).thenThrow(cassetteError);
    ClientHttpResponse response = recorder.intercept(request, requestBody, clientHttpRequestExecution);
    assertThat(response, is(nullValue()));

    verify(recordingListener, times(1)).errorReadingCassette(request, cassetteError);
    verifyNotExecuted(cassetteRepository, clientHttpRequestExecution);
  }

  private void verifyNotExecuted(CassetteRepository cassetteRepository, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
    verify(cassetteRepository, never()).record(any(HttpRequest.class), any(ClientHttpResponse.class));
    verify(clientHttpRequestExecution, never()).execute(any(HttpRequest.class), any(byte[].class));
  }
}
