package com.geeksaint.springvcr.maker;

import com.geeksaint.springvcr.player.RecordedRequest;
import com.geeksaint.springvcr.player.RecordedResponse;
import com.geeksaint.springvcr.player.Track;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;

public class TrackMaker {
  public static final Property<Track, RecordedRequest> request = new Property<Track, RecordedRequest>();
  public static final Property<Track, RecordedResponse> response = new Property<Track, RecordedResponse>();

  public static final Instantiator<Track> Track = new Instantiator<Track>() {
    public Track instantiate(final PropertyLookup<Track> lookup){
      RecordedRequest recordedRequest = lookup.valueOf(request, make(a(RecordedRequestMaker.Request)));
      RecordedResponse recordedResponse = lookup.valueOf(response, make(a(RecordedResponseMaker.Response)));

      return com.geeksaint.springvcr.player.Track.of(recordedRequest, recordedResponse);
    }
  };

  public static Track trackOf(RecordedRequest requestOne, RecordedResponse responseOne) {
    return make(a(TrackMaker.Track, with(TrackMaker.request, requestOne), with(TrackMaker.response, responseOne)));
  }
}
