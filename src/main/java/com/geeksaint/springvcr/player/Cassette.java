package com.geeksaint.springvcr.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.geeksaint.springvcr.player.serialize.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = "recordedResponseMap")
public class Cassette {
  public Map<RecordedRequest, RecordedResponse> recordedResponseMap
      = new HashMap<RecordedRequest, RecordedResponse>();

  public void record(RecordedRequest request, RecordedResponse response){
    recordedResponseMap.put(request, response);
  }

  public void setTrackList(List<Track> tracks){
    //set tracks to the map.
  }
  public List<Track> getTrackList(){
    List<Track> trackList = new ArrayList<Track>();
    for(RecordedRequest request : recordedResponseMap.keySet()){
      trackList.add(Track.of(request, recordedResponseMap.get(request)));
    }
    return trackList;
  }

  public static Cassette create() {
    return new Cassette();
  }
}