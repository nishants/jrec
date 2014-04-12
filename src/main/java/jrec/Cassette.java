package jrec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpRequest;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cassette {
  private Map<RecordedRequest,RecordedResponse > records = new HashMap<RecordedRequest, RecordedResponse>();
  private String testName;

  private Cassette(String testName){
    this.testName = testName;
  }

  public void addTrack(RecordedRequest recordedRequest, RecordedResponse recordedResponse) {
    records.put(recordedRequest, recordedResponse);
  }

  public RecordedResponse responseOf(RecordedRequest recordedRequest){
    return records.get(recordedRequest);
  }

  public static Cassette forName(String name){
    return new Cassette(name);
  }
}
