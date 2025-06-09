package dae.tokenizer;

import java.util.*;
import java.util.regex.*;

public class Lexer {
  private static final Map<String, Token.Type> KEYWORDS = Map.of(
    "fun", Token.Type.FUN,
    "return", Token.Type.RETURN,
    "if", Token.Type.IF,
    "else", Token.Type.ELSE,
    "print", Token.Type.PRINT,
    "str", Token.Type.STR
  );
  
  private String input;
  private int pos = 0;
  
  public Lexer(String input) {
    this.input = input;
  }
  
  private char peek() {
    return pos < input.length() ? input.charAt(pos) : '\0';
  }
  
  private char advance() {
    return pos < input.length() ? input.charAt(pos++) : '\0';
  }
  
  private void skipIfHasWhitespace() {
    while(Character.isWhitespace(peek())) advance();
  }
  
  public List<Token> tokenize() {
    List<Token> tokens = new ArrayList<>();
    skipIfHasWhitespace();
    
    while (!(pos >= input.length())) {
      char c = peek();
      
      if (Character.isLetter(c)) {
        tokens.add(readWord());
      } else if (Character.isDigit(c)) {
        tokens.add(readNumber());
      } else if (c == '"') {
        tokens.add(readString());
      } else {
        switch (c) {
          case '(': tokens.add(new Token(String.valueOf(advance()), Token.Type.LPAREN)); break;
          case ')': tokens.add(new Token(String.valueOf(advance()), Token.Type.RPAREN)); break;
          case '{': tokens.add(new Token(String.valueOf(advance()), Token.Type.LBRACE)); break;
          case '}': tokens.add(new Token(String.valueOf(advance()), Token.Type.RBRACE)); break;
          case ':': tokens.add(new Token(String.valueOf(advance()), Token.Type.COLON)); break;
          case ';': tokens.add(new Token(String.valueOf(advance()), Token.Type.SEMICOLON)); break;
          case ',': tokens.add(new Token(String.valueOf(advance()), Token.Type.COMMA)); break;
          case '.': tokens.add(new Token(String.valueOf(advance()), Token.Type.DOT)); break;
          case '=':
            advance();
            if (peek() == '=') {
              tokens.add(new Token("==", Token.Type.DOUBLE_EQUALS));
            } else {
              tokens.add(new Token("=", Token.Type.EQUALS));
            }
            break;
          case '-':
            advance();
            if (peek() == '>') {
              advance();
              tokens.add(new Token("->", Token.Type.ARROW));
            } else {
              tokens.add(new Token("-", Token.Type.UNKNOWN));
            }
            break;
          default:
            advance();
        }
      }
      skipIfHasWhitespace();
    }
    tokens.add(new Token("", Token.Type.EOF));
    return tokens;
  }
  
  private Token readWord() {
    StringBuilder sb = new StringBuilder();
    while (Character.isLetterOrDigit(peek()) || peek() == '_') sb.append(advance());
    
    String word = sb.toString();
    Token.Type type = KEYWORDS.getOrDefault(word, Token.Type.IDENTIFIER);
    
    return new Token(word, type);
  }
  
  private Token readNumber() {
    StringBuilder sb = new StringBuilder();
    while (Character.isDigit(peek())) sb.append(advance());
    
    return new Token(sb.toString(), Token.Type.NUMBER);
  }
  
  private Token readString() {
    advance(); //skip opening '"'
    StringBuilder sb = new StringBuilder();
    while (peek() != '"' && peek() != '\0') {
      if (peek() == '\\') {
        advance(); //escape char
        if (peek() != '\0') sb.append(advance());
      } else {
        sb.append(advance());
      }
    }
    if (peek() == '"') advance(); //skip closing '"'
    return new Token(sb.toString(), Token.Type.STRING);
  }
}