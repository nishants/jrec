package jrec;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;
import static org.mockito.Mockito.*;

public class RecordedResponseTest {
  private ClientHttpResponse response;
  private RecordedResponse recordedResponse;
  private HttpHeaders httpHeaders;
  private String contents;

  @Before
  public void setUp() throws Exception {
    response = mock(ClientHttpResponse.class);
    httpHeaders = mock(HttpHeaders.class);
    contents = "response body";
    InputStream body = new ByteArrayInputStream(contents.getBytes("UTF-8"));

    when(response.getHeaders()).thenReturn(httpHeaders);
    when(response.getBody()).thenReturn(body);

    recordedResponse = RecordedResponse.of(response);
  }

  @Test
  public void shouldProxyAllMethodsOtherThanGetBody() throws IOException {
    when(response.getRawStatusCode()).thenReturn(200);
    when(response.getStatusCode()).thenReturn(HttpStatus.ACCEPTED);
    when(response.getStatusText()).thenReturn("ok");

    recordedResponse = RecordedResponse.of(response);

    assertThat(response.getStatusCode(), is(recordedResponse.getStatusCode()));
    assertThat(response.getRawStatusCode(), is(200));
    assertThat(response.getHeaders(), is(httpHeaders));
    assertThat(response.getStatusText(), is("ok"));
    assertFalse(response.getBody().equals(recordedResponse.getBody()));
  }
  @Test
  public void shouldReturnBody() throws IOException {
    assertThat(fromStream(recordedResponse.getBody(), "UTF-8"), is(contents));
  }

  @Test
  public void shouldSerialize() throws IOException {
    when(response.getRawStatusCode()).thenReturn(200);
    when(response.getStatusCode()).thenReturn(HttpStatus.ACCEPTED);
    when(response.getStatusText()).thenReturn("ok");
    when(response.getHeaders()).thenReturn(httpHeaders);

    recordedResponse.setContent("some content");
    recordedResponse.setEncoding("UTF-8");
    assertThat(fromStream(recordedResponse.getBody(), "UTF-8"), is("some content"));

    assertThat(recordedResponse, is(parseYaml(toYaml(recordedResponse))));
  }

  @Test
  public void shouldDeserialize() throws IOException {
    RecordedResponse deserialized = YamlAyeOh.parseYaml(getClass().getResourceAsStream("/fixtures/recordedResponse.yaml"), RecordedResponse.class);
    assertThat(deserialized.getRawStatusCode(), is(200));
    assertThat(deserialized.getStatusCode(), is(HttpStatus.OK));
    assertThat(deserialized.getStatusText(), is("OK"));
    assertThat(deserialized.getHeaders().size(), is(4));
    assertThat(deserialized.getHeaders().values(),
        hasItems(asList("Apache-Coyote/1.1"),
          asList("application/vnd.cat.pscs.dealer+json;charset=UTF-8"),
          asList("chunked")
            ));
  }

  @Test
  public void shouldReturnContentAsInputStream() throws IOException {
    recordedResponse.setContent("some content");
    assertThat(fromStream(recordedResponse.getBody(), "UTF-8"), is("some content"));
  }

  private String fromStream(InputStream inputStream, String encoding) throws IOException {
    StringWriter writer = new StringWriter();
    IOUtils.copy(inputStream, writer, encoding);
    return writer.toString();
  }

  private RecordedResponse parseYaml(String yaml) throws IOException {
    return YamlAyeOh.parseYaml(yaml, RecordedResponse.class);
  }

  private String toYaml(Object object) throws JsonProcessingException {
    return YamlAyeOh.toYaml(object);
  }
}
