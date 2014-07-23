package com.geeksaint.springvcr.player.serialize;

import com.geeksaint.springvcr.maker.TrackMaker;
import org.junit.Test;

import java.io.IOException;

import static com.geeksaint.springvcr.player.serialize.YamlIO.read;
import static com.geeksaint.springvcr.player.serialize.YamlIO.toYaml;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrackTest {

  @Test
  public void shouldSerialize() throws IOException {
    Track track = make(a(TrackMaker.Track));
    assertThat(read(toYaml(track), Track.class), is(track));
  }
}
