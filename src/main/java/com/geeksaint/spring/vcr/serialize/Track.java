package com.geeksaint.spring.vcr.serialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeksaint.spring.vcr.serialize.RecordedRequest;
import com.geeksaint.spring.vcr.serialize.RecordedResponse;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Track implements Map.Entry<RecordedRequest, RecordedResponse>{
  private RecordedRequest request;
  private RecordedResponse response;

  @Override
  @JsonIgnore
  public RecordedRequest getKey() {
    return request;
  }

  @Override
  @JsonIgnore
  public RecordedResponse getValue() {
    return response;
  }

  @Override
  @JsonIgnore
  public RecordedResponse setValue(RecordedResponse value) {
    return response = value;
  }
}
