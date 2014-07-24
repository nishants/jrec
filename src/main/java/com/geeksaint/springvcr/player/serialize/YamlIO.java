package com.geeksaint.springvcr.player.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class YamlIO {
  private final static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  public static <T> T read(File file, Class<T> clazz) throws IOException {
    return mapper.readValue(new FileInputStream(file), clazz);
  }

  public static <T> T read(InputStream yaml, Class<T> clazz) throws IOException {
    return mapper.readValue(yaml, clazz);
  }

  public static <T> T read(String yaml, Class<T> clazz) throws IOException {
    return mapper.readValue(yaml, clazz);
  }

  public static String toYaml(Object arg) throws JsonProcessingException {
    return mapper.writeValueAsString(arg);
  }

  public static void writeYamlTo(File file, Object object) throws IOException {
    mapper.writeValue(file, object);
  }

}
