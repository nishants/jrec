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
    FileOutputStream fileIn = new FileOutputStream(toFile);
    ZipOutputStream zipOut = new ZipOutputStream(fileIn);
    addFilesRecursively(inDir, zipOut, inDir.listFiles());
    zipOut.close();
  }

  private void addFilesRecursively(File inDir, ZipOutputStream zipOut, File[] files) throws IOException {
    for (File file : files) {
      if (!file.isDirectory()) {
        addToZip(inDir, file, zipOut);
      } else {
        addFilesRecursively(inDir, zipOut, file.listFiles());
      }
    }
  }

  private void addToZip(File inDir, File file, ZipOutputStream zos) throws IOException {
    ZipEntry zipentry = new ZipEntry(file.getAbsolutePath().replaceFirst(inDir.getAbsolutePath(), ""));
    zos.putNextEntry(zipentry);
    IOUtils.copy(new FileInputStream(file), zos);
    zos.closeEntry();
  }
}
