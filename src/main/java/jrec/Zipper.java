package jrec;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
  public void zipFiles(File inDir, File toFile) throws IOException {
    FileOutputStream fos = new FileOutputStream(toFile);
    ZipOutputStream zos = new ZipOutputStream(fos);
    for (File file : inDir.listFiles()) {
      if (!file.isDirectory()) addToZip(file, zos);
    }
    zos.close();
  }

  private void addToZip(File file, ZipOutputStream zos) throws IOException {
    ZipEntry zipentry = new ZipEntry(file.getAbsolutePath());
    zos.putNextEntry(zipentry);
    IOUtils.copy(new FileInputStream(file), zos);
    zos.closeEntry();
  }

}
