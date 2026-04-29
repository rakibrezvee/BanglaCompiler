import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int pos = 0;
    private SymbolTable symbolTable;
    private int lastResult = 0; 

    public Parser(List<Token> tokens, SymbolTable symbolTable) {
        this.tokens = tokens;
        this.symbolTable = symbolTable;
    }

    public int getLastResult() {
        return lastResult;
    }

    public void parse() {
        while (pos < tokens.size() && tokens.get(pos).type != Token.TokenType.EOF) {
            parseStatement();
        }
        System.out.println("পার্সার: কোডটি সফলভাবে রান হয়েছে।");
    }

    private void parseStatement() {
        if (pos >= tokens.size()) return;
        Token current = tokens.get(pos);

        if (current.type == Token.TokenType.TYPE) {
            String varType = current.value;
            pos++; 
            
            
            if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.IDENTIFIER) {
                throw new RuntimeException("সিনট্যাক্স এরর: '" + varType + "' এর পর একটি ভেরিয়েবল নাম প্রত্যাশিত");
            }
            
            String varName = tokens.get(pos).value;
            pos++; 
            
            symbolTable.declareVariable(varName, varType);

            
            if (pos < tokens.size() && tokens.get(pos).type == Token.TokenType.ASSIGN) {
                pos++; 
                this.lastResult = parseExpression();
                System.out.println("Parser: Evaluated [" + lastResult + "] for '" + varName + "'");
                
                
                if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.SEMICOLON) {
                    throw new RuntimeException("সিনট্যাক্স এরর: লাইনের শেষে অনুপস্থিত সেমিকলন ';'");

                }
                pos++; 
            } else {
                throw new RuntimeException("সিনট্যাক্স এরর: ভেরিয়েবল ঘোষণার পর '=' প্রত্যাশিত");
            }
        } else {
            throw new RuntimeException("সিনট্যাক্স এরর: '" + current.value + "' দিয়ে শুরু হওয়া স্টেটমেন্ট স্বীকৃত নয়");
        }
    }

    private int parseExpression() {
        if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.NUMBER) {
            throw new RuntimeException("সিনট্যাক্স এরর: গাণিতিক অপারেশনের জন্য একটি সংখ্যা প্রত্যাশিত ছিল।");
        }
        
        int leftValue = Integer.parseInt(tokens.get(pos).value);
        pos++;

        
        if (pos < tokens.size() && tokens.get(pos).type == Token.TokenType.PLUS) {
            pos++; 
            if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.NUMBER) {
                throw new RuntimeException("সিনট্যাক্স এরর: '+' এর পর একটি সংখ্যা প্রত্যাশিত");
            }
            int rightValue = Integer.parseInt(tokens.get(pos).value);
            pos++; 
            return leftValue + rightValue;
        }

        return leftValue;
    }
}
