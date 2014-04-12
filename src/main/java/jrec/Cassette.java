package jrec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpRequest;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cassette {
  private Map<RecordedRequest,RecordedResponse > records;

  public boolean has(RecordedRequest recordedRequest) {
    return records.keySet().contains(recordedRequest);
  }
}
