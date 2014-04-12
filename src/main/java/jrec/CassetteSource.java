package jrec;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CassetteSource {
  private final String platformPathSeparator;
  boolean archive;
  Zipper zipper;

  public CassetteSource(String cassettesDir, Zipper zipper, Boolean archive, String fileSeparator) {
    platformPathSeparator = fileSeparator;
    this.zipper = zipper;
    this.archive = archive;

  }

  public void save(Object cassette) throws IOException {

//    File file = new File(toTestDirectory(), fileName);
//    file.getParentFile().mkdirs();
//    PrintWriter out = new PrintWriter(file);
//    out.println(YamlAyeOh.toYaml(cassette));
//    out.flush();
//    out.close();
  }

  public <T> T readFromFile(String fileName, Class<T> clazz) throws IOException {
//    byte[] encoded = Files.readAllBytes(new File(toTestDirectory(), fileName).toPath());
//    String yaml = Charset.forName(JRecRuntTime.DEFAULT_CHARSET).decode(ByteBuffer.wrap(encoded)).toString();
//    return YamlAyeOh.parseYaml(yaml, clazz);
    return null;
  }
//
//  private File toTestDirectory() {
//    return new File(filesHome, JRecRuntTime.getCurrentTest().replaceAll("\\.", platformPathSeparator));
//  }

  //  reads all objects from the current directory
  public <T> List<T> readAllFromTestDir(Class<T> clazz) {
//    List<T> objects = new ArrayList<T>();
//    for (File file : toTestDirectory().listFiles()) {
//      if (!file.isDirectory()) {
//        try {
//          objects.addTrack(readFromFile(file.getName(), clazz));
//        } catch (IOException e) {
//          // ignore file
//        }
//      }
//    }
//    return objects;
    return null;
  }

  public Cassette cassetteFor(String cassetteName) {
    return null;
  }
}
