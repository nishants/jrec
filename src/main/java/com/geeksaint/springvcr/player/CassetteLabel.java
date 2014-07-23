package com.geeksaint.springvcr.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class CassetteLabel {
  private String label;
  private final static String EXTENSION = ".yaml";

  public CassetteLabel(String label) {
    this.label = label;
  }

  @JsonIgnore
  public String toFileName(){
    return label.replaceAll("\\.", File.separator).concat(EXTENSION);
  }
}
