package com.geeksaint.spring.vcr;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class Zipper {
  private static final int BLOCK_SIZE = 4096;

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

  public void unZip(File zipFile, File toDir) throws IOException {
    ZipInputStream input = null;
    try {
      input = readZip(zipFile);
      unZip(input, toDir);
    } finally {
      if (input != null) input.close();
    }
  }

  private static void unZip(ZipInputStream input, File toDir) throws IOException {
    for (ZipEntry nextEntry = next(input); nextEntry != null; nextEntry = next(input)) {
      if (nextEntry.isDirectory()) {
        createDir(toDir, nextEntry.getName());
      } else {
        createFile(toDir, input, nextEntry.getName());
      }
    }
  }

  private static ZipEntry next(ZipInputStream zin) throws IOException {
    return zin.getNextEntry();
  }

  private static void createFile(File toDir, ZipInputStream zin, String fileName) throws IOException {
    String dir = dirOf(fileName);
    if (dir != null) createDir(toDir, dir);
    extractFile(zin, toDir, fileName);
  }

  private static ZipInputStream readZip(File zipFile) throws FileNotFoundException {
    return new ZipInputStream(new FileInputStream(zipFile));
  }

  private static String dirOf(String name) {
    int s = name.lastIndexOf(File.separatorChar);
    return s == -1 ? null : name.substring(0, s);
  }

  private static void createDir(File toCreate, String path) {
    File dir = new File(toCreate, path);
    dir.mkdirs();
  }

  private static void extractFile(ZipInputStream in, File toDir, String name) throws IOException {
    byte[] buffer = new byte[BLOCK_SIZE];
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(toDir, name)));
    int count = -1;
    while ((count = in.read(buffer)) != -1)
      out.write(buffer, 0, count);
    out.close();
  }
}
