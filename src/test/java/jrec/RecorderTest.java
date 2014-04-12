package jrec;

import com.sun.deploy.net.HttpResponse;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecorderTest {
  private byte[] requestBody;
  private ClientHttpResponse response;
  private ClientHttpRequest request;

  private ClientHttpRequestExecution clientHttpRequestExecution;
  private CassetteRepository cassetteRepository;
  private RecordingListener recordingListener;
  private VCRMode recordOnlyMode;
  private VCRMode playOnlyMode;
  private VCRMode playAndRecordMode;

  @Rule public ExpectedException expectedException = ExpectedException.none();
  private ClientHttpResponse recordedResponse;

  @Before
  public void setUp() throws Exception {
    requestBody = new byte[0];
    response = mock(ClientHttpResponse.class);
    request = mock(ClientHttpRequest.class);
    clientHttpRequestExecution = mock(ClientHttpRequestExecution.class);
    cassetteRepository = mock(CassetteRepository.class);
    recordingListener = mock(RecordingListener.class);
    recordedResponse = mock(RecordedResponse.class);

    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
    when(cassetteRepository.record(request, response)).thenReturn(recordedResponse);

    recordOnlyMode = VCRMode.RECORD;
    playOnlyMode = VCRMode.PLAY;
    playAndRecordMode = VCRMode.PLAY_RECORD;
  }

  @Test
  public void shouldInterceptAndRecordResponseInRecordMode() throws IOException {
    Recorder recorder = new Recorder(cassetteRepository, recordOnlyMode);
    recorder.addRecordingListener(recordingListener);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    verify(cassetteRepository, times(1)).record(request, response);
    verify(recordingListener, times(1)).recorded(request, recordedResponse);
    verify(cassetteRepository, never()).responseFor(request);

    assertThat(actualResponse, is(recordedResponse));
  }

  @Test
  public void shouldNotifyIfRequestFailed() throws IOException {
    Recorder recorder = new Recorder(cassetteRepository, recordOnlyMode);
    recorder.addRecordingListener(recordingListener);
    when(clientHttpRequestExecution.execute(request, requestBody)).thenThrow(IOException.class);
    expectedException.expect(IOException.class);

    try {
      recorder.intercept(request, requestBody, clientHttpRequestExecution);
    } catch (IOException e) {
      verify(cassetteRepository, never()).record(any(HttpRequest.class), any(ClientHttpResponse.class));
      verify(cassetteRepository, never()).responseFor(any(HttpRequest.class));

      verify(recordingListener, times(1)).failedToExecuteRequest(request);
      verify(recordingListener, never()).recorded(any(HttpRequest.class), any(RecordedResponse.class));
      throw e;
    }

    assertThat("Excpeted IOException to be thrown", is(nullValue()));
  }

//
//  @Test
//  public void shouldInterceptAndRecordResponseInRecordAndPlayMode() throws IOException {
//    Recorder recorder = new Recorder(cassetteRepository, VCRMode.PLAY_RECORD);
//    recorder.intercept(request, requestBody, clientHttpRequestExecution);
//    verify(cassetteRepository, times(1)).recordOnlyMode(request, response);
//  }
//
//  @Test
//  public void shouldReturnResponseFromSavedCassettesInPlayMode() throws IOException {
//    Recorder recorder = new Recorder(cassetteRepository, VCRMode.PLAY);
//    when(cassetteRepository.responseFor(request)).thenReturn(response);
//
//    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);
//
//    assertThat(actualResponse, is(response));
//  }
}
