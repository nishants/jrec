package com.geeksaint.springvcr.player.serialize;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static com.geeksaint.springvcr.maker.HttpResponseMaker.*;
import static com.geeksaint.springvcr.player.serialize.YamlIO.read;
import static com.geeksaint.springvcr.player.serialize.YamlIO.toYaml;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RecordedResponseTest {
  private ClientHttpResponse response;
  private RecordedResponse recordedResponse;
  private String responseBody;

  @Before
  public void setUp() throws Exception {
    responseBody = "response body";
    response = make(a(ClientHttpResponse, with(body, responseBody)));
    recordedResponse = RecordedResponse.of(response);
  }

  @Test
  public void shouldHaveCorrectValues() throws IOException {
    assertThat(recordedResponse.getRawStatusCode(), is(response.getRawStatusCode()));
    assertThat(recordedResponse.getStatusText(), is(response.getStatusText()));
    assertThat(recordedResponse.getStatusCode(), is(response.getStatusCode()));
    assertThat(recordedResponse.getHeaders(), is(response.getHeaders()));
    assertThat(toUTF8String(recordedResponse.getBody()), is(responseBody));
  }

  @Test
  public void shouldSerialize() throws IOException {
    assertThat(read(toYaml(recordedResponse), RecordedResponse.class), is(recordedResponse));
  }

  private String toUTF8String(InputStream stream) throws IOException {
    StringWriter writer = new StringWriter();
    IOUtils.copy(stream, writer, "UTF-8");
    return writer.toString();
  }
}
