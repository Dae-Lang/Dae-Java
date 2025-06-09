package dae;

import dae.utils.File;
import dae.tokenizer.*;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    String code = File.readFile("/sdcard/main.cf");
    
    Lexer lexer = new Lexer(code);
    List<Token> tokens = lexer.tokenize();
    print("Tokens:");
    for (int i = 0; i < tokens.size(); i++) {
      print(i + ": " + tokens.get(i));
    }
  }
  
  private static void print(String printText) {
    System.out.println(printText);
  }
}