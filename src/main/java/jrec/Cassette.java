package jrec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpRequest;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cassette {
  private RecordedRequest request;
  private RecordedResponse response;

  public boolean of(HttpRequest httpRequest) {
    return request.equals(new RecordedRequest(httpRequest));
  }
}
