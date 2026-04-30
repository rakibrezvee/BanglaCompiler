public class SemanticAnalyzer {
    private SymbolTable symbolTable;

    public SemanticAnalyzer(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void validateDeclaration(String name, String type) {
        symbolTable.declareVariable(name, type);
    }

    public void validateUsage(String name) {
        symbolTable.getVariableType(name);
    }
}