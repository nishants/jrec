package com.geeksaint.springvcr.player.serialize;

import com.geeksaint.springvcr.maker.RecordedRequestMaker;
import com.geeksaint.springvcr.player.RecordedRequest;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.IOException;

import static com.geeksaint.springvcr.player.serialize.YamlIO.*;
import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.junit.Assert.assertThat;

public class RecordedRequestTest {
  @Test
  public void shouldSerialize() throws IOException {
    RecordedRequest recordedRequest = make(a(RecordedRequestMaker.Request));
    assertThat(read(toYaml(recordedRequest), RecordedRequest.class), CoreMatchers.is(recordedRequest));
  }
}
