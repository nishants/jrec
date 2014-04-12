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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CassetteRepositoryTest {

  private String responseContent;
  private InputStream inputStream;
  private ClientHttpResponse response;
  private ClientHttpRequest request;
  private CassetteRepository cassetteRepository;
  private CassetteSource cassetteSource;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();
  private Cassette cassette;
  private String cassetteName;

  @Before
  public void setUp() throws Exception {
    responseContent = "data";
    inputStream = new ByteArrayInputStream(responseContent.getBytes());
    response = mock(ClientHttpResponse.class);
    when(response.getBody()).thenReturn(inputStream);
    request = mock(ClientHttpRequest.class);

    cassetteName = "jrec.TestSomething.testMyMethod";
    cassette = Cassette.forName(cassetteName);
    cassetteSource = mock(CassetteSource.class);
    when(cassetteSource.getCassette(cassetteName)).thenReturn(cassette);

    cassetteRepository = new CassetteRepository(cassetteSource);

    when(response.getBody()).thenReturn(inputStream);
  }

  @Test
  public void shouldThrowExceptionIfErrorCreatingFile() throws IOException {
    IOException exception = new IOException("error reading cassetteSource");
    doThrow(exception).when(cassetteSource).save(any(Cassette.class));

    expectedException.expectMessage("error reading cassetteSource");

    cassetteRepository.record(request, response, cassetteName);
  }

  @Test
  public void shouldRecordAndReturnRespnse() throws IOException {
    RecordedResponse actualResponse = (RecordedResponse)cassetteRepository.record(request, response, cassetteName);
    assertThat(actualResponse.getContent(), is("data"));
  }
}
