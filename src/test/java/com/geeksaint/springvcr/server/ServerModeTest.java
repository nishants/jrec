package com.geeksaint.springvcr.server;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ServerModeTest {
  @Test
  public void shouldRecordInRecordMode(){
    assertThat(ServerMode.valueOf("RECORD").isRecording(), is(true));
    assertThat(ServerMode.valueOf("PLAY").isRecording(), is(false));
  }
}
