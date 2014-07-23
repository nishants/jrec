package com.geeksaint.springvcr.player;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CassetteLabelTest {
  @Test
  public void shouldResolveToCorrectFileName(){
    CassetteLabel cassetteLabel = new CassetteLabel("com.geeksaint.springvcr.TestMe.testName");
    assertThat(cassetteLabel.toFileName(), is("com/geeksaint/springvcr/TestMe/testName.yaml"));
  }
}
