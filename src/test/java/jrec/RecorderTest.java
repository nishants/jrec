package jrec;

import com.sun.org.apache.regexp.internal.recompile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecorderTest {
  private byte[] requestBody;
  private ClientHttpResponse response;
  private ClientHttpRequest request;

  ClientHttpRequestExecution clientHttpRequestExecution;
  private CassetteRepository cassetteRepository;

  @Before
  public void setUp() throws Exception {
    requestBody =new byte[0];
    response = mock(ClientHttpResponse.class);
    request = mock(ClientHttpRequest.class);
    clientHttpRequestExecution = mock(ClientHttpRequestExecution.class);
    cassetteRepository = mock(CassetteRepository.class);

    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
  }

  @Test
  public void shouldInterceptAndRecordResponseInRecordMode() throws IOException {
    Recorder recorder = new Recorder(cassetteRepository, VCRMode.PLAY_RECORD);
    recorder.intercept(request, requestBody, clientHttpRequestExecution);
    verify(cassetteRepository, times(1)).record(request, response);
  }

  @Test
  public void shouldReturnResponseFromSavedCassettesInPlayMode() throws IOException {
    Recorder recorder = new Recorder(cassetteRepository, VCRMode.PLAY);
    when(cassetteRepository.responseFor(request)).thenReturn(response);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    assertThat(actualResponse, is(response));
  }
}
