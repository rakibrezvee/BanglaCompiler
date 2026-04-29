public class Token {
    public enum TokenType { 
        TYPE, IDENTIFIER, NUMBER, ASSIGN, PLUS, SEMICOLON, EOF 
    }
    
    public TokenType type;
    public String value;
    
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Token{" + "type=" + type + ", value='" + value + "'}";
    }
}
