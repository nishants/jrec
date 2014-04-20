package com.geeksaint.spring.vcr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geeksaint.spring.vcr.maker.RecordedRequestMaker;
import com.geeksaint.spring.vcr.maker.RecordedResponseMaker;
import com.geeksaint.spring.vcr.serialize.Cassette;
import com.geeksaint.spring.vcr.serialize.RecordedRequest;
import com.geeksaint.spring.vcr.serialize.RecordedResponse;
import com.geeksaint.spring.vcr.serialize.YamlIO;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static com.geeksaint.spring.vcr.maker.RecordedRequestMaker.Request;
import static com.geeksaint.spring.vcr.maker.RecordedResponseMaker.Response;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CassetteTest {


  private String cassetteName = "jrec.SomeClass.someTestMethod";
  private Cassette cassette;

  @Before
  public void setUp() throws Exception {
    cassette = Cassette.forName(cassetteName);
  }

  @Test
  public void shouldDeserialize() throws IOException {
    RecordedRequest recordedRequest = make(a(Request));
    RecordedRequest otherRequest = make(a(Request, with(RecordedRequestMaker.method, "PUT")));
    RecordedResponse recordedResponse = make(a(Response));

    RecordedResponse otherResponse = make(a(Response, with(RecordedResponseMaker.content, "some other content")));
    cassette.addTrack(recordedRequest, recordedResponse);
    cassette.addTrack(otherRequest, otherResponse);

    assertThat(deserialize(serialize(cassette)), is(cassette));
  }

  private Cassette deserialize(String yaml) throws IOException {
    return YamlIO.read(yaml, Cassette.class);
  }

  private String serialize(Cassette object) throws JsonProcessingException {
    return YamlIO.toYaml(object);
  }
}
