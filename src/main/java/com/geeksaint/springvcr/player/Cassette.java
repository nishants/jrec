package com.geeksaint.springvcr.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.geeksaint.springvcr.player.serialize.Track;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Cassette {
  @JsonIgnore
  public final Map<RecordedRequest, RecordedResponse> recordedResponseMap
      = new HashMap<RecordedRequest, RecordedResponse>();
  @Getter
  @Setter
  private CassetteLabel cassetteLabel;

  public Cassette(CassetteLabel cassetteLabel) {
    this.cassetteLabel = cassetteLabel;
  }

  @JsonIgnore
  public void record(RecordedRequest request, RecordedResponse response){
    recordedResponseMap.put(request, response);
  }

  @JsonProperty("trackList")
  public void setTrackList(List<Track> tracks){
    recordedResponseMap.clear();
    for(Track track : tracks){
      recordedResponseMap.put(track.getRequest(), track.getResponse());
    }
  }

  @JsonProperty("trackList")
  public List<Track> getTrackList(){
    List<Track> trackList = new ArrayList<Track>();
    for(RecordedRequest request : recordedResponseMap.keySet()){
      trackList.add(Track.of(request, recordedResponseMap.get(request)));
    }
    return trackList;
  }

  public static Cassette create(String forLabel) {
    return new Cassette(new CassetteLabel(forLabel));
  }

  public RecordedResponse responseOf(RecordedRequest request) {
    return recordedResponseMap.get(request);
  }

  @JsonIgnore
  public boolean isBlank() {
    return recordedResponseMap.isEmpty();
  }
}