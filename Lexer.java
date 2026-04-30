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
            
            if ((c >= '\u09E6' && c <= '\u09EF') || Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (pos < input.length() && (isBanglaDigit(input.charAt(pos)) || Character.isDigit(input.charAt(pos)))) {
                    num.append(input.charAt(pos++));
                }
                tokens.add(new Token(Token.TokenType.NUMBER, convertToEnglishDigits(num.toString())));
                continue;
            }

            if (isBanglaLetter(c)) {
                StringBuilder word = new StringBuilder();
                while (pos < input.length() && (isBanglaLetter(input.charAt(pos)) || isBanglaDigit(input.charAt(pos)))) {
                    word.append(input.charAt(pos++));
                }
                String finalWord = word.toString();
                
                if (finalWord.equals("\u09B8\u0982\u0996\u09CD\u09AF\u09BE") || 
                    finalWord.equals("\u09A7\u09B0\u09BF")) {               
                    tokens.add(new Token(Token.TokenType.TYPE, finalWord));
                } else {
                    tokens.add(new Token(Token.TokenType.IDENTIFIER, finalWord));
                }
                continue;
            }
            
            // ✅ ASCII + Unicode lookalike variants all handled
            if (c == '=')                      { tokens.add(new Token(Token.TokenType.ASSIGN,    "=")); pos++; continue; }
            if (c == '+' || c == '\uFF0B')     { tokens.add(new Token(Token.TokenType.PLUS,      "+")); pos++; continue; }
            if (c == '-' || c == '\u2212')     { tokens.add(new Token(Token.TokenType.MINUS,     "-")); pos++; continue; }
            if (c == '*' || c == '\u00D7')     { tokens.add(new Token(Token.TokenType.MULTIPLY,  "*")); pos++; continue; }
            if (c == '/' || c == '\u00F7')     { tokens.add(new Token(Token.TokenType.DIVIDE,    "/")); pos++; continue; }
            if (c == ';' || c == '\uFF1B')     { tokens.add(new Token(Token.TokenType.SEMICOLON, ";")); pos++; continue; }
            
            pos++; 
        }
        tokens.add(new Token(Token.TokenType.EOF, ""));
        return tokens;
    }

    private boolean isBanglaLetter(char c) {
        return (c >= '\u0980' && c <= '\u09FF');
    }

    private boolean isBanglaDigit(char c) {
        return (c >= '\u09E6' && c <= '\u09EF');
    }

    private String convertToEnglishDigits(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c >= '\u09E6' && c <= '\u09EF') {
                result.append((char) (c - '\u09E6' + '0'));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
