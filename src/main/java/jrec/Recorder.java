package jrec;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Recorder implements ClientHttpRequestInterceptor {
  private final VCRMode mode;
  private CassetteRepository cassetteRepository;
  private Set<RecordingListener> recordingListeners;

  public Recorder(CassetteRepository cassetteRepository, VCRMode mode) {
    this.mode = mode;
    this.cassetteRepository = cassetteRepository;
    recordingListeners = new HashSet<RecordingListener>();
  }

  public void addRecordingListener(RecordingListener listener) {
    recordingListeners.add(listener);
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    ClientHttpResponse response = null;

    if (mode.recording()) return record(request, body, execution);


//    if (mode.playing()) {
//      ClientHttpResponse recordedResponse = cassetteRepository.responseFor(request);
//      if(recordedResponse!=null){
//        return recordedResponse;
//      }
//    }


    return null;
  }

  private ClientHttpResponse record(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    ClientHttpResponse response = null;
    try {
      response = execution.execute(request, body);
    } catch (IOException e) {
      notifyRequestFailed(request);
      throw e;
    }
    return recordedResponse(request, response);
  }

  private ClientHttpResponse recordedResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
    ClientHttpResponse recordedResponse = cassetteRepository.record(request, response);
    notifyRecorded(request, recordedResponse);
    return recordedResponse;
  }

  private void notifyRecorded(HttpRequest request, ClientHttpResponse response) {
    for (RecordingListener listener : recordingListeners) listener.recorded(request, response);
  }

  private void notifyRequestFailed(HttpRequest request) {
    for (RecordingListener listener : recordingListeners) listener.failedToExecuteRequest(request);
  }
}
