package jrec;

import org.junit.Test;
import org.springframework.test.util.MatcherAssertionErrors;

import static org.hamcrest.CoreMatchers.is;

public class VCRModeTest {

  @Test
  public void shouldRecordInRecordingModes(){
    MatcherAssertionErrors.assertThat(VCRMode.RECORD.recording(), is(true));
    MatcherAssertionErrors.assertThat(VCRMode.PLAY_RECORD.recording(), is(true));
    MatcherAssertionErrors.assertThat(VCRMode.PLAY.recording(), is(false));
  }
  @Test
  public void shouldPlayInPlayingModes(){
    MatcherAssertionErrors.assertThat(VCRMode.RECORD.playing(), is(false));
    MatcherAssertionErrors.assertThat(VCRMode.PLAY_RECORD.playing(), is(true));
    MatcherAssertionErrors.assertThat(VCRMode.PLAY.playing(), is(true));
  }
}
