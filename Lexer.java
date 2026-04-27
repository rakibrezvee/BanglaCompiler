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
            
            // Ignore spaces
            if (Character.isWhitespace(c)) { 
                pos++; 
                continue; 
            }
            
            // Read words (Keywords or Variables)
            if (Character.isLetter(c)) {
                String word = "";
                while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
                    word += input.charAt(pos++);
                }
                // Check if it's our Bangla data type "shongkha" or "kotha"
                if (word.equals("shongkha") || word.equals("kotha")) {
                    tokens.add(new Token(Token.TokenType.TYPE, word));
                } else {
                    tokens.add(new Token(Token.TokenType.IDENTIFIER, word));
                }
                continue;
            }
            
            // Read numbers
            if (Character.isDigit(c)) {
                String num = "";
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                    num += input.charAt(pos++);
                }
                tokens.add(new Token(Token.TokenType.NUMBER, num));
                continue;
            }
            
            // Read symbols
            if (c == '=') { tokens.add(new Token(Token.TokenType.ASSIGN, "=")); pos++; continue; }
            if (c == '+') { tokens.add(new Token(Token.TokenType.PLUS, "+")); pos++; continue; }
            if (c == ';') { tokens.add(new Token(Token.TokenType.SEMICOLON, ";")); pos++; continue; }
            
            pos++; // move forward if unrecognized
        }
        tokens.add(new Token(Token.TokenType.EOF, ""));
        return tokens;
    }
}