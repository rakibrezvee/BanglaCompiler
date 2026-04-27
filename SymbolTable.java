import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    
    // A simple class to hold a variable's data type
    public static class VariableInfo {
        public String type; // e.g., "shongkha" or "kotha"

        public VariableInfo(String type) {
            this.type = type;
        }
    }

    // The actual memory bank (HashMap)
    private Map<String, VariableInfo> table;

    public SymbolTable() {
        this.table = new HashMap<>();
    }

    // Add a new variable to memory
    public void declareVariable(String name, String type) {
        if (table.containsKey(name)) {
            throw new RuntimeException("Syntax Error: Variable '" + name + "' is already declared!");
        }
        table.put(name, new VariableInfo(type));
        System.out.println("Symbol Table: Saved variable '" + name + "' as type '" + type + "'");
    }

    // Check what type a variable is (used for Type Checking)
    public String getVariableType(String name) {
        if (!table.containsKey(name)) {
            throw new RuntimeException("Syntax Error: Variable '" + name + "' has not been declared!");
        }
        return table.get(name).type;
    }
}