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

import java.io.IOException;

import static com.geeksaint.springvcr.maker.CassetteMaker.aCassetteWith;
import static com.geeksaint.springvcr.maker.CassetteMaker.emptyCassette;
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
  private Cassette cassette;
  private Cassette emptyCassette;
  private String cassetteLabel;
  private byte[] requestBody;

  @Before
  public void setUp() throws Exception {
    request = make(a(HttpRequestMaker.HttpRequest));
    response = make(a(HttpResponseMaker.ClientHttpResponse));
    requestBody = "request body".getBytes();
    recordedRequest = RecordedRequest.of(request, requestBody);
    recordedResponse = RecordedResponse.of(response);
    cassette = aCassetteWith(recordedRequest, recordedResponse);
    cassetteLabel = "com.geeksaint.springvcr.Test.testName";
    emptyCassette = emptyCassette(cassetteLabel);
  }

  @Test
  public void shouldRecordRequest() throws IOException {
    when(cassetteStore.ofLabel(cassetteLabel)).thenReturn(emptyCassette);

    vcr.loadCassette(cassetteLabel);
    vcr.record(request, requestBody, response);
    vcr.eject();

    verify(cassetteStore).save(cassette);
  }


  @Test
  public void shouldReturnForRecordedRequest() throws IOException {
    when(cassetteStore.ofLabel(cassetteLabel)).thenReturn(cassette);

    vcr.loadCassette(cassetteLabel);
    ClientHttpResponse returnedResponse = vcr.play(request, requestBody);

    assertThat(recordedResponse, is(returnedResponse));
  }

}
