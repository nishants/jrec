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
public class RecorderOnlyModeTest {
  private byte[] requestBody;
  private ClientHttpResponse response;
  private ClientHttpRequest request;

  private ClientHttpRequestExecution clientHttpRequestExecution;
  private CassetteRepository cassetteRepository;
  private RecordingListener recordingListener;
  private VCRMode recordOnlyMode;

  @Rule public ExpectedException expectedException = ExpectedException.none();
  private ClientHttpResponse recordedResponse;
  private Recorder recorder;
  private String testName;

  @Before
  public void setUp() throws Exception {
    requestBody = new byte[0];
    response = mock(ClientHttpResponse.class);
    request = mock(ClientHttpRequest.class);
    testName = "myPackage.subPackage.TestClass.testMethodName";
    clientHttpRequestExecution = mock(ClientHttpRequestExecution.class);
    cassetteRepository = mock(CassetteRepository.class);
    recordingListener = mock(RecordingListener.class);
    recordedResponse = mock(RecordedResponse.class);

    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
    when(cassetteRepository.record(request, response)).thenReturn(recordedResponse);

    recordOnlyMode = VCRMode.RECORD;
    recorder = new Recorder(cassetteRepository, recordOnlyMode);
    recorder.addRecordingListener(recordingListener);
    recorder.nextTest(testName);
  }

  @Test
  public void shouldInterceptAndRecordResponseInRecordMode() throws IOException {
    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    verify(cassetteRepository, times(1)).record(request, response);
    verify(recordingListener, times(1)).recorded(request, recordedResponse);
    verify(cassetteRepository, never()).responseFor(request, testName);
    verify(recordingListener, times(1)).recorded(request, recordedResponse);

    assertThat(actualResponse, is(recordedResponse));
  }

  @Test
  public void shouldNotifyIfRequestFailed() throws IOException {
    when(clientHttpRequestExecution.execute(request, requestBody)).thenThrow(IOException.class);
    expectedException.expect(IOException.class);

    try {
      recorder.intercept(request, requestBody, clientHttpRequestExecution);
    } catch (IOException e) {
      verify(cassetteRepository, never()).record(any(HttpRequest.class), any(ClientHttpResponse.class));
      verify(cassetteRepository, never()).responseFor(any(HttpRequest.class), any(String.class));

      verify(recordingListener, times(1)).failedToExecuteRequest(request);
      verify(recordingListener, never()).recorded(any(HttpRequest.class), any(RecordedResponse.class));
      throw e;
    }

    assertThat("Excpeted IOException to be thrown", is(nullValue()));
  }
}
