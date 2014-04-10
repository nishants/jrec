package jrec;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Disk {
  private final String platformPathSeparator;
  private final File filesHome;
  boolean archive;
  Zipper zipper;

  public Disk(String cassettesDir, Zipper zipper, Boolean archive, String fileSeparator) {
    platformPathSeparator = fileSeparator;
    filesHome = new File(cassettesDir);
    this.zipper = zipper;
    this.archive = archive;

  }

  public void saveToTestDir(Cassette cassette, String fileName) throws IOException {
    File file = new File(toTestDirectory(), fileName);
    file.getParentFile().mkdirs();
    PrintWriter out = new PrintWriter(file);
    out.println(YamlAyeOh.toYaml(cassette));
    out.flush();
    out.close();
  }

  public <T> T readFromFile(String fileName, Class<T> clazz) throws IOException {
    byte[] encoded = Files.readAllBytes(new File(toTestDirectory(), fileName).toPath());
    String yaml = Charset.forName(JRec.DEFAULT_CHARSET).decode(ByteBuffer.wrap(encoded)).toString();
    return YamlAyeOh.parseYaml(yaml, clazz);
  }

  private File toTestDirectory() {
    return new File(filesHome, JRec.getCurrentTest().replaceAll("\\.", platformPathSeparator));
  }

  //  reads all objects from the current directory
  public <T> List<T> readAllFromTestDir(Class<T> clazz) {
    List<T> objects = new ArrayList<T>();
    for (File file : toTestDirectory().listFiles()) {
      if (!file.isDirectory()) {
        try {
          objects.add(readFromFile(file.getName(), clazz));
        } catch (IOException e) {
          // ignore file
        }
      }
    }
    return objects;
  }
}
