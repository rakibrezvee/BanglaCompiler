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
                throw new RuntimeException("Syntax Error: Expected a variable name after '" + varType + "'");
            }
            
            String varName = tokens.get(pos).value;
            pos++; 
            
            symbolTable.declareVariable(varName, varType);

            
            if (pos < tokens.size() && tokens.get(pos).type == Token.TokenType.ASSIGN) {
                pos++; 
                this.lastResult = parseExpression();
                System.out.println("Parser: Evaluated [" + lastResult + "] for '" + varName + "'");
                
                
                if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.SEMICOLON) {
                    throw new RuntimeException("Syntax Error: Missing semicolon ';' at the end of the line");
                }
                pos++; 
            } else {
                throw new RuntimeException("Syntax Error: Expected '=' after variable declaration");
            }
        } else {
            throw new RuntimeException("Syntax Error: Unrecognized statement starting with '" + current.value + "'");
        }
    }

    private int parseExpression() {
        if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.NUMBER) {
            throw new RuntimeException("Syntax Error: Expected a number for math operation");
        }
        
        int leftValue = Integer.parseInt(tokens.get(pos).value);
        pos++;

        
        if (pos < tokens.size() && tokens.get(pos).type == Token.TokenType.PLUS) {
            pos++; 
            if (pos >= tokens.size() || tokens.get(pos).type != Token.TokenType.NUMBER) {
                throw new RuntimeException("Syntax Error: Expected a number after '+'");
            }
            int rightValue = Integer.parseInt(tokens.get(pos).value);
            pos++; 
            return leftValue + rightValue;
        }

        return leftValue;
    }
}