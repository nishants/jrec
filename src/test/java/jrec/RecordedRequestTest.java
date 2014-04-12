package jrec;

import com.fasterxml.jackson.core.JsonProcessingException;
import jrec.maker.RecordedRequestMaker;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecordedRequestTest {

  @Test
  public void shouldDeserialize() throws IOException {
    RecordedRequest deserialized = Serializer.deserialize(getClass().getResourceAsStream("/fixtures/recordedRequest.yaml"), RecordedRequest.class);
    assertThat(deserialized.getMethod(), is(HttpMethod.GET));
    assertThat(deserialized.getUri(), is(URI.create("http://localhost:9393/organization")));
    assertThat(deserialized.getContent(), is("request body"));
  }

  @Test
  public void equalsToARequestWithSameURIContentAndMethod(){
    RecordedRequest recordedRequest = make(a(RecordedRequestMaker.Request));
    RecordedRequest similarRequest = make(a(RecordedRequestMaker.Request));
    RecordedRequest diffBody = make(a(RecordedRequestMaker.Request,
        with(RecordedRequestMaker.content, "diff body")));
    RecordedRequest diffMethod = make(a(RecordedRequestMaker.Request,
        with(RecordedRequestMaker.method, "GET")));
    RecordedRequest diffURI = make(a(RecordedRequestMaker.Request,
        with(RecordedRequestMaker.uri, "http:localhost:8080/new-app")));

    assertThat(recordedRequest, is(similarRequest));
    assertThat(recordedRequest, not(is(diffBody)));
    assertThat(recordedRequest, not(is(diffMethod)));
    assertThat(recordedRequest, not(is(diffURI)));
  }

  @Test
  public void shouldSerialize() throws IOException {
    RecordedRequest recordedRequest = make(a(RecordedRequestMaker.Request));

    assertThat(recordedRequest, is(parseYaml(toYaml(recordedRequest))));
  }

  @Test
  public void shouldSetBody() throws UnsupportedEncodingException {
    String body = "some text";
    RecordedRequest request = new RecordedRequest();
    request.setBody(body.getBytes("UTF-8"));

    assertThat(request.getContent(), is(body));
  }

  private RecordedRequest parseYaml(String yaml) throws IOException {
    return Serializer.deserialize(yaml, RecordedRequest.class);
  }

  private String toYaml(Object object) throws JsonProcessingException {
    return Serializer.serialize(object);
  }
}
