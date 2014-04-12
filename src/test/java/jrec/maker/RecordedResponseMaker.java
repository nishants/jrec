package jrec.maker;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import jrec.RecordedResponse;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class RecordedResponseMaker {
  public static final Property<RecordedResponse, String> content = new Property();
  public static final Property<RecordedResponse, String> statusCode = new Property();
  public static final Property<RecordedResponse, String> statusText = new Property();
  public static final Property<RecordedResponse, Integer> rawStatusCode = new Property();
  public static final Property<RecordedResponse, Map<String, List<String>>> headers = new Property();

  public static final Instantiator<RecordedResponse> Response = new Instantiator<RecordedResponse>() {
    public RecordedResponse instantiate(PropertyLookup<RecordedResponse> lookup) {
      RecordedResponse recordedResponse = new RecordedResponse();
      recordedResponse.setContent(lookup.valueOf(content, "response-body"));
      recordedResponse.setStatusText(lookup.valueOf(statusText, "OK"));
      recordedResponse.setRawStatusCode(lookup.valueOf(rawStatusCode, 200));
      recordedResponse.setStatusCode(HttpStatus.valueOf(lookup.valueOf(statusCode, "OK").toUpperCase()));
      recordedResponse.setHeaders(lookup.valueOf(headers, headers()));

      return recordedResponse;
    }
  };

  private static Map<String, List<String>>  headers(){
    Map<String, List<String>> headers = new HashMap<String, List<String>>();
    headers.put("Server",asList("Apache-Coyote/1.1"));
    headers.put("Content-Type",asList("application/vnd.cat.pscs.dealer+json;charset=UTF-8"));
    headers.put("Transfer-Encoding",asList("chunked"));
    headers.put("Date",asList("Wed, 09 Apr 2014 03:29:10 GMT"));
    return headers;
  }
}