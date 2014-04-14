//package jrec;
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.web.client.RestTemplate;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//@ContextConfiguration({"classpath:configuration/test-config.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class ApiTest {
//  @Autowired
//  @Qualifier("aRestTemplate")
//  private RestTemplate restTemplate;
//
//  @Test
//  public void shouldBeConfigurableFromXML(){
//    assertThat(restTemplate.getInterceptors().get(0).getClass(), is(Recorder.class));
//  }
//
//}
