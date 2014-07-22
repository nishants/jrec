package com.geeksaint.springvcr.player;

import com.geeksaint.springvcr.maker.HttpResponseMaker;
import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.geeksaint.springvcr.maker.HttpRequestMaker;
import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VCRTest {
  @InjectMocks
  private VCR vcr;

  @Mock
  private CassetteStore cassetteStore;
  private HttpRequest request;
  private ClientHttpResponse response;
  private RecordedRequest recordedRequest;
  private RecordedResponse recordedResponse;

  @Before
  public void setUp() throws Exception {
    request = make(a(HttpRequestMaker.HttpRequest));
    response = make(a(HttpResponseMaker.ClientHttpResponse));
    recordedRequest = RecordedRequest.of(request);
    recordedResponse = RecordedResponse.of(response);
  }

  @Test
  public void shouldRecordRequest(){
    vcr.record(request, response);
    verify(cassetteStore).record(recordedRequest);
  }


  @Test
  public void shouldReturnForRecordedRequest(){
    when(cassetteStore.getResponseFor(recordedRequest)).thenReturn(recordedResponse);
    assertThat((RecordedResponse) vcr.play(request), is(recordedResponse));
  }

}
