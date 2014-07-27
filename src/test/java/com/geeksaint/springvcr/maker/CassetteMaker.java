package com.geeksaint.springvcr.maker;

import com.geeksaint.springvcr.player.Cassette;
import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.geeksaint.springvcr.player.serialize.Track;
import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;

import java.util.List;

import static com.geeksaint.springvcr.maker.TrackMaker.trackOf;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;

public class CassetteMaker {
  public static final Property<Cassette, List<Track>> trackList = new Property<Cassette, List<Track>>();
  public static final Property<Cassette, String> label = new Property<Cassette, String>();

  public static final Instantiator<Cassette> Cassette = new Instantiator<Cassette>() {
    public Cassette instantiate(final PropertyLookup<Cassette> lookup) {
      String cassetteLabel = lookup.valueOf(label, "com.geeksaint.springvcr.Test.testName");
      Cassette cassette = com.geeksaint.springvcr.player.Cassette.create(cassetteLabel);
      List<Track> tracks = lookup.valueOf(trackList, asList(trackOf(make(a(RecordedRequestMaker.Request)), make(a(RecordedResponseMaker.Response)))));
      cassette.setTrackList(tracks);
      return cassette;
    }
  };

  public static Cassette aCassetteWith(RecordedRequest recordedRequest, RecordedResponse recordedResponse) {
    return make(a(Cassette,
        with(trackList, asList(
          trackOf(recordedRequest, recordedResponse))
        ))
    );
  }


  public static Cassette emptyCassette(String cassetteLabel) {
    return com.geeksaint.springvcr.player.Cassette.create(cassetteLabel);
  }
}
