package com.geeksaint.springvcr.player;

import com.geeksaint.springvcr.maker.RecordedRequestMaker;
import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.geeksaint.springvcr.player.serialize.Track;
import org.junit.Test;

import static com.geeksaint.springvcr.maker.RecordedRequestMaker.*;
import static com.geeksaint.springvcr.maker.RecordedResponseMaker.*;
import static com.geeksaint.springvcr.maker.TrackMaker.trackOf;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CassetteTest {
  @Test
  public void shouldRecordCassettesAndReturnListOfTracks(){
    RecordedResponse responseOne = responseWith("BAD_REQUEST");
    RecordedResponse responseTwo = responseWith("OK");

    RecordedRequest requestOne = requestWith("http/server/app/fck");
    RecordedRequest requestTwo = requestWith("http/server/app/plk");

    Cassette cassette = Cassette.create();
    cassette.record(requestOne, responseOne);
    cassette.record(requestTwo, responseTwo);

    Track trackOne = trackOf(responseOne, requestOne);
    Track trackTwo = trackOf(responseTwo, requestTwo);

    assertThat(cassette.getTrackList(), is(asList(trackOne, trackTwo)));
  }

  private RecordedRequest requestWith(String uri) {
    return make(a(Request,
        with(RecordedRequestMaker.uri, uri)));
  }

  private RecordedResponse responseWith(String status) {
    return make(a(Response,
          with(statusCode, status)));
  }
}
