package dae.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class File {
  
  public static String readFile(String path) {
    StringBuilder sb = new StringBuilder();
    try (FileReader fr = new FileReader(path)) {
      char[] buff = new char[1024];
      int length;

      while ((length = fr.read(buff)) > 0) {
        sb.append(new String(buff, 0, length));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return sb.toString();
  }
  
}