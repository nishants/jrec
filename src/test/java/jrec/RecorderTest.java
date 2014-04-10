package jrec;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.ContextConfiguration;

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

  @InjectMocks
  private Recorder recorder;

  @Mock
  private CassetteRepository cassetteRepository;

  @Before
  public void setUp() throws Exception {
    requestBody =new byte[0];
    response = mock(ClientHttpResponse.class);
    request = mock(ClientHttpRequest.class);
    clientHttpRequestExecution = mock(ClientHttpRequestExecution.class);

    when(clientHttpRequestExecution.execute(request, requestBody)).thenReturn(response);
  }

  @Test
  public void shouldInterceptAndRecordResponseInRecordMode() throws IOException {
//    recorder.playing();
    recorder.intercept(request, requestBody, clientHttpRequestExecution);
    verify(cassetteRepository, times(1)).record(request, response);
  }

  @Test
  public void shouldReturnResponseFromSavedCassettesInPlayMode() throws IOException {
    when(cassetteRepository.responseFor(request)).thenReturn(response);

    ClientHttpResponse actualResponse = recorder.intercept(request, requestBody, clientHttpRequestExecution);

    assertThat(actualResponse, is(response));
  }
}
