package jrec;

import jrec.maker.RecordedRequestMaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CassetteRepositoryTest {

  private String responseContent;
  private InputStream inputStream;
  private RecordedResponse recordedResponse;
  private ClientHttpResponse response;
  private RecordedRequest recordedRequest;
  private ClientHttpRequest request;

  @Before
  public void setUp() throws Exception {
    responseContent = "data";
    inputStream = new ByteArrayInputStream(responseContent.getBytes());

    response = mock(ClientHttpResponse.class);
    request = mock(ClientHttpRequest.class);

    when(response.getBody()).thenReturn(inputStream);

    recordedRequest = make(a(RecordedRequestMaker.Request));
    recordedResponse = RecordedResponse.of(response);
  }

  @Test
  public void shouldRecordAndReturnRecordedMessage(){



  }
}
