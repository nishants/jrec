package jrec;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Function;
import lombok.*;
import org.springframework.http.HttpHeaders;

import java.util.*;

import static com.google.common.collect.Collections2.transform;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Cassette {
  @JsonIgnore
  private Map<RecordedRequest,RecordedResponse > records = new HashMap<RecordedRequest, RecordedResponse>();

  private String label;

  private Cassette(String label){
    this.label = label;
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

  @JsonProperty("tracks")
  public void setTracks(List<Track> tracks) {
    for(Track track : tracks) records.put(track.getRequest(), track.getResponse());
  }

  @JsonProperty("tracks")
  public Collection<Track> getTracks() {
    return transform(records.entrySet(), toTrack());
  }

  private Function<Map.Entry<RecordedRequest, RecordedResponse>, Track> toTrack() {
    return new Function<Map.Entry<RecordedRequest, RecordedResponse>, Track>() {
      @Override
      public Track apply(Map.Entry<RecordedRequest, RecordedResponse> input) {
        return new Track(input.getKey(), input.getValue());
      }
    };
  }
}
