package dae.tokenizer;

public class Token {
  public String value;
  public Type type;
  
  public static enum Type {
    //words
    FUN, RETURN, IF, ELSE, PRINT,
    //types
    STR, IDENTIFIER, STRING, NUMBER,
    //simbols
    LPAREN, RPAREN, LBRACE, RBRACE, COLON, ARROW, SEMICOLON, COMMA, EQUALS, DOT,
    //operators
    DOUBLE_EQUALS,
    //end
    EOF,
    UNKNOWN
  }
  
  public Token(String value, Type type) {
    this.value = value;
    this.type = type;
  }
  
  @Override
  public String toString() {
    return "{\n Text: " + value + ",\nType: " + type + "\n}";
  }
  
}