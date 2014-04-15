package jrec;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

@ContextConfiguration({"classpath:configuration/test-config.xml"})
@RunWith(Vcr.class)
public class ApiTest {
  @Autowired
  @Qualifier("aRestTemplate")
  private RestTemplate restTemplate;

  @Test
  public void recorderShouldBindToRestTemplate(){
    assertThat(firstInterceptorOf(restTemplate), instanceOf(Recorder.class));
  }

  private ClientHttpRequestInterceptor firstInterceptorOf(RestTemplate restTemplate1) {
    return restTemplate1.getInterceptors().get(0);
  }

}
