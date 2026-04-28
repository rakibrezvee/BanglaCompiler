import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int pos = 0;

    public Lexer(String input) { 
        this.input = input; 
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        
        while (pos < input.length()) {
            char c = input.charAt(pos);
            
            
            if (Character.isWhitespace(c)) { 
                pos++; 
                continue; 
            }
            
            
            if (Character.isLetter(c)) {
                String word = "";
                while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
                    word += input.charAt(pos++);
                }
                if (word.equals("shongkha") || word.equals("kotha")) {
                    tokens.add(new Token(Token.TokenType.TYPE, word));
                } else {
                    tokens.add(new Token(Token.TokenType.IDENTIFIER, word));
                }
                continue;
            }
            
            if (Character.isDigit(c)) {
                String num = "";
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                    num += input.charAt(pos++);
                }
                tokens.add(new Token(Token.TokenType.NUMBER, num));
                continue;
            }
            
            
            if (c == '=') { tokens.add(new Token(Token.TokenType.ASSIGN, "=")); pos++; continue; }
            if (c == '+') { tokens.add(new Token(Token.TokenType.PLUS, "+")); pos++; continue; }
            if (c == ';') { tokens.add(new Token(Token.TokenType.SEMICOLON, ";")); pos++; continue; }
            
            pos++; 
        }
        tokens.add(new Token(Token.TokenType.EOF, ""));
        return tokens;
    }
}
