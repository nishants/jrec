package jrec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class YamlAyeOh {
  public static <T> T readJson(String file, Class<T> clazz) throws IOException {
    return newMapper().readValue(from(file), clazz);
  }

  public static <T> T parseYaml(InputStream yaml, Class<T> clazz) throws IOException {
    return newMapper().readValue(yaml, clazz);
  }

  public static <T> T parseYaml(String yaml, Class<T> clazz) throws IOException {
    return newMapper().readValue(yaml, clazz);
  }

  public static String toYaml(Object arg) throws JsonProcessingException {
    return newMapper().writeValueAsString(arg);
  }
  private static ObjectMapper newMapper() {
    return new ObjectMapper(new YAMLFactory());
  }

  public static void writeToFile(File file, Object object) throws IOException {
    newMapper().writeValue(file, object);
  }

  private static InputStream from(String fixture) {
    return YamlAyeOh.class.getResourceAsStream(fixture);
  }

}
