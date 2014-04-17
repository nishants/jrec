package jrec.recorder;

import jrec.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayAndRecordModeTest {
  private byte[] requestBody;
  private ClientHttpResponse response;
  private HttpRequest request;

  private ClientHttpRequestExecution clientHttpRequestExecution;
  private CassetteRepository cassetteRepository;
  private RecordingListener recordingListener;
  private VCRMode playAndRecordMode;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private ClientHttpResponse recordedResponse;
  private Recorder recorder;
  private String testName;

  @Before
  public void setUp() throws Exception {
    requestBody = new byte[0];
    response = mock(ClientHttpResponse.class);
    request = mock(HttpRequest.class);
    recordedResponse = mock(RecordedResponse.class);

    clientHttpRequestExecution = mock(ClientHttpRequestExecution.class);
    cassetteRepository = mock(CassetteRepository.class);
    recordingListener = mock(RecordingListener.class);

    playAndRecordMode = VCRMode.PLAY_RECORD;

    //Runs in play and record mode by default
    recorder = new Recorder(cassetteRepository, null, new ArrayList());

    recorder.addRecordingListener(recordingListener);
    testName = "myPackage.subPackage.TestClass.testMethodName";
    JRecRuntTime.getRuntTime().beforeTestMethod(testName);
  }

  @Test
  public void shouldSkipInterceptingIfTestNameIsNotDefined() throws IOException {
    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
    JRecRuntTime.getRuntTime().beforeTestMethod(null);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    assertThat(actualResponse, is(response));

    verify(recordingListener, times(1)).requestSkipped(request);
    verify(recordingListener, never()).readingFromCassette(request, recordedResponse);
    verify(cassetteRepository, never()).record(anyRequest(), anyResponse(), any(String.class));

    verify(cassetteRepository, never()).record(anyRequest(), anyResponse(), any(String.class));
    verify(recordingListener, never()).recorded(anyRequest(), anyResponse());
    verify(cassetteRepository, never()).responseFor(anyRequest(), any(String.class));
    verify(recordingListener, never()).recorded(anyRequest(), anyResponse());

  }

  private ClientHttpResponse anyResponse() {
    return any(ClientHttpResponse.class);
  }

  private HttpRequest anyRequest() {
    return any(HttpRequest.class);
  }

  @Test
  public void shouldReturnRecordedResponseIfCassetteWasFound() throws IOException {
    when(cassetteRepository.responseFor(request, testName)).thenReturn(recordedResponse);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    assertThat(actualResponse, is(recordedResponse));

    verify(recordingListener, times(1)).readingFromCassette(request, recordedResponse);
    verifyRequestNotExecuted(cassetteRepository, clientHttpRequestExecution);
  }

  @Test
  public void shouldReturnExecuteRequestAndReturnRecordedResponseIfCassetteNotFound() throws IOException {
    when(cassetteRepository.responseFor(request, testName)).thenReturn(null);
    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
    when(cassetteRepository.record(request, response, testName)).thenReturn(recordedResponse);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    assertThat(actualResponse, is(recordedResponse));

    verify(recordingListener, never()).readingFromCassette(request, recordedResponse);

    verify(cassetteRepository, times(1)).record(request, response, testName);
    verify(recordingListener, times(1)).recorded(request, recordedResponse);
    verify(cassetteRepository, times(1)).responseFor(request, testName);
    verify(recordingListener, times(1)).recorded(request, recordedResponse);
  }

  @Test
  public void shouldExecuteRequestAndReplaceCassetteIfErrorReadingACassette() throws IOException {
    IOException cassetteError = new IOException();
    when(cassetteRepository.responseFor(request, testName)).thenThrow(cassetteError);

    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
    when(cassetteRepository.record(request, response, testName)).thenReturn(recordedResponse);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    assertThat(actualResponse, is(recordedResponse));

    verify(recordingListener, never()).readingFromCassette(request, recordedResponse);

    verify(cassetteRepository, times(1)).record(request, response, testName);
    verify(recordingListener, times(1)).recorded(request, recordedResponse);
    verify(cassetteRepository, times(1)).responseFor(request, testName);
  }

  @Test
  public void shouldNotifyIfFailedToCreateCassette() throws IOException {
    IOException cassetteError = new IOException();
    when(cassetteRepository.responseFor(request, testName)).thenThrow(cassetteError);
    when(cassetteRepository.record(request, response, testName)).thenThrow(cassetteError);

    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    assertThat(actualResponse, is(response));

    verify(recordingListener, never()).readingFromCassette(request, recordedResponse);
    verify(recordingListener, never()).recorded(anyRequest(), anyResponse());
    verify(recordingListener, times(1)).failedToCreateCassette(request, response, cassetteError);

    verify(cassetteRepository, times(1)).record(request, response, testName);
    verify(cassetteRepository, times(1)).responseFor(request, testName);
  }

  private void verifyRequestNotExecuted(CassetteRepository cassetteRepository, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
    verify(cassetteRepository, never()).record(anyRequest(), anyResponse(), any(String.class));
    verify(clientHttpRequestExecution, never()).execute(anyRequest(), any(byte[].class));
  }
}
