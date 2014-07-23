package com.geeksaint.springvcr.player;

import com.geeksaint.springvcr.maker.RecordedRequestMaker;
import com.geeksaint.springvcr.player.serialize.RecordedRequest;
import com.geeksaint.springvcr.player.serialize.RecordedResponse;
import com.geeksaint.springvcr.player.serialize.Track;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.geeksaint.springvcr.maker.RecordedRequestMaker.*;
import static com.geeksaint.springvcr.maker.RecordedResponseMaker.*;
import static com.geeksaint.springvcr.maker.TrackMaker.trackOf;
import static com.geeksaint.springvcr.player.serialize.YamlIO.read;
import static com.geeksaint.springvcr.player.serialize.YamlIO.toYaml;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static com.natpryce.makeiteasy.MakeItEasy.with;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class CassetteTest {

  private Cassette cassette;
  private Track trackOne;
  private Track trackTwo;
  private RecordedRequest requestOne;
  private RecordedRequest requestTwo;
  private RecordedResponse responseOne;
  private RecordedResponse responseTwo;

  @Before
  public void setUp() throws Exception {
    responseOne = responseWith("OK");
    responseTwo = responseWith("OK");

    requestOne = requestWith("http/server/app/fck");
    requestTwo = requestWith("http/server/app/plk");
    trackOne = trackOf(responseOne, requestOne);
    trackTwo = trackOf(responseTwo, requestTwo);

    cassette = Cassette.create("com.geeksaint.springvcr.TestMe.testName");
  }

  @Test
  public void shouldRecordCassettesAndReturnListOfTracks(){
    cassette.record(requestOne, responseOne);
    cassette.record(requestTwo, responseTwo);
    assertThat(cassette.getTrackList(), hasItems(trackOne, trackTwo));
  }

  @Test
  public void shouldReturnResponseFromTheAvailableTracks(){
    cassette.setTrackList(asList(trackOne, trackTwo));
    assertThat(cassette.responseOf(requestOne), is(responseOne));
    assertThat(cassette.responseOf(requestTwo), is(responseTwo));
  }

  @Test
  public void shouldSerializeAndDeserialize() throws IOException {
    cassette.record(requestOne, responseOne);
    cassette.record(requestTwo, responseTwo);
    assertThat(read(toYaml(cassette), Cassette.class), is(cassette));
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
