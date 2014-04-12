package jrec;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.Getter;

import java.util.List;

import static com.google.common.base.Objects.firstNonNull;

@Component
public class JRecContext implements TestListener{

  private String DEFAULT_CASSETTES_HOME = "resources/cassettes";
  @Getter
  private String nextTestName;
  private Recorder recorder;

  @Autowired
  public JRecContext(List<RestTemplate> restTemplateList,
                     @Value("#{systemProperties['jrec.mode']}") String vcrMode,
                     @Value("#{systemProperties['jrec.cassettes.home']}") String cassettesHome,
                     @Value("#{systemProperties['jrec.cassettes.archive']}") String archive,
                     @Value("#{systemProperties['file.separator']}") String filSeparator) {
    setRecorder(restTemplateList, vcrMode, cassettesHome, archive, filSeparator);
    JRecRuntTime.registerContext(this);
  }

  private void setRecorder(List<RestTemplate> restTemplateList, String vcrMode, String cassettesHome, String archive,String filSeparator) {
    Zipper zipper = new Zipper();
    CassetteSource cassetteSource = new CassetteSource(
        firstNonNull(cassettesHome,DEFAULT_CASSETTES_HOME),
        zipper,
        Boolean.valueOf(archive),
        filSeparator);

    CassetteRepository cassetteRepository = new CassetteRepository(cassetteSource);
    recorder = new Recorder(cassetteRepository, mode(vcrMode));
    for (RestTemplate restTemplate : restTemplateList){
      restTemplate.getInterceptors().add(recorder);
    }
  }

  private VCRMode mode(String vcrMode) {
    if (vcrMode == null) return VCRMode.PLAY_RECORD ;
    return VCRMode.valueOf(vcrMode.toUpperCase());
  }

  @Override
  public void beforeTestMethod(String testName) {
    nextTestName = testName;
    recorder.setNextTest(testName);
  }

  @Override
  public void beforeTestClass() {
    // Do Nothing
  }
}
