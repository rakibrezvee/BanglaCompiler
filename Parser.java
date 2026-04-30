import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int pos = 0;
    private SemanticAnalyzer semanticAnalyzer;
    private int lastResult = 0; 

    public Parser(List<Token> tokens, SymbolTable symbolTable) {
        this.tokens = tokens;
        this.semanticAnalyzer = new SemanticAnalyzer(symbolTable);
    }

    public int getLastResult() { return lastResult; }

    public void parse() {
        while (pos < tokens.size() && tokens.get(pos).type != Token.TokenType.EOF) {
            parseStatement();
        }
    }

    private void parseStatement() {
        if (pos >= tokens.size()) return;
        Token current = tokens.get(pos);

        if (current.type == Token.TokenType.TYPE) {
            String varType = current.value;
            pos++; 
            
            if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.IDENTIFIER) {
                throw new RuntimeException("সিনট্যাক্স এরর: ভেরিয়েবল নাম প্রত্যাশিত");
            }
            
            String varName = tokens.get(pos).value;
            pos++; 
            
            semanticAnalyzer.validateDeclaration(varName, varType);

            if (pos < tokens.size() && tokens.get(pos).type == Token.TokenType.ASSIGN) {
                pos++; 
                this.lastResult = parseExpression();
                
                if (pos < tokens.size() && tokens.get(pos).type == Token.TokenType.SEMICOLON) {
                    pos++; 
                } else {
                    throw new RuntimeException("সিনট্যাক্স এরর: লাইনের শেষে অনুপস্থিত সেমিকলন ';'");
                }
            } else {
                throw new RuntimeException("সিনট্যাক্স এরর: '=' প্রত্যাশিত");
            }
        }
    }

    private int parseExpression() {
        if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.NUMBER) {
            throw new RuntimeException("সিনট্যাক্স এরর: সংখ্যা প্রত্যাশিত");
        }
        int leftValue = Integer.parseInt(tokens.get(pos).value);
        pos++;

        if (pos < tokens.size()) {
            Token op = tokens.get(pos);
            if (op.type == Token.TokenType.PLUS     || 
                op.type == Token.TokenType.MINUS    || 
                op.type == Token.TokenType.MULTIPLY || 
                op.type == Token.TokenType.DIVIDE) {
                
                pos++; 
                
                if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.NUMBER) {
                    throw new RuntimeException("সিনট্যাক্স এরর: অপারেটরের পর সংখ্যা প্রত্যাশিত");
                }
                
                int rightValue = Integer.parseInt(tokens.get(pos).value);
                pos++;

                switch (op.type) {
                    case PLUS:     return leftValue + rightValue;
                    case MINUS:    return leftValue - rightValue;
                    case MULTIPLY: return leftValue * rightValue;
                    case DIVIDE:
                        if (rightValue == 0) 
                            throw new RuntimeException("রানটাইম এরর: ০ দিয়ে ভাগ করা যায় না");
                        return leftValue / rightValue;
                    default: break;
                }
            }
        }
        return leftValue;
    }
}
