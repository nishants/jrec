package jrec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CassetteRepositoryTest {

  private String responseContent;
  private InputStream inputStream;
  private ClientHttpResponse response;
  private ClientHttpRequest request;
  private CassetteRepository cassetteRepository;
  private CassetteReader cassetteReader;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setUp() throws Exception {
    responseContent = "data";
    inputStream = new ByteArrayInputStream(responseContent.getBytes());

    response = mock(ClientHttpResponse.class);
    request = mock(ClientHttpRequest.class);
    cassetteReader = mock(CassetteReader.class);
    cassetteRepository = new CassetteRepository(cassetteReader);

    when(response.getBody()).thenReturn(inputStream);
  }

  @Test
  public void shouldThrowExceptionIfErrorCreatingFile() throws IOException {
    IOException exception = new IOException("error reading cassetteReader");
    doThrow(exception).when(cassetteReader).saveToTestDir(any(Object.class), any(String.class));

    expectedException.expectMessage("error reading cassetteReader");

    cassetteRepository.record(request, response, "");
  }
}
