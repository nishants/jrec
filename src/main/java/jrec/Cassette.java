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
  private String testName;

  public Cassette(String testName){
    this.testName = testName;
  }

  public boolean has(RecordedRequest recordedRequest) {
    return records.keySet().contains(recordedRequest);
  }

  public void add(RecordedRequest recordedRequest, RecordedResponse recordedResponse) {
    records.put(recordedRequest, recordedResponse);
  }
}
