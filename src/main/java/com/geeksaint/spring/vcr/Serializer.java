package com.geeksaint.spring.vcr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class Serializer {

  private static final YAMLFactory YAML_FACTORY = new YAMLFactory();

  public static <T> T readJson(String file, Class<T> clazz) throws IOException {
    return newMapper().readValue(from(file), clazz);
  }

  public static <T> T deserialize(InputStream yaml, Class<T> clazz) throws IOException {
    return newMapper().readValue(yaml, clazz);
  }

  public static <T> T deserialize(String yaml, Class<T> clazz) throws IOException {
    return newMapper().readValue(yaml, clazz);
  }

  public static String serialize(Object arg) throws JsonProcessingException {
    return newMapper().writeValueAsString(arg);
  }

  public static void serializeToFile(File file, Object object) throws IOException {
    newMapper().writeValue(file, object);
  }

  private static ObjectMapper newMapper() {
    return new ObjectMapper(YAML_FACTORY);
  }

  private static InputStream from(String fixture) {
    return Serializer.class.getResourceAsStream(fixture);
  }

}
