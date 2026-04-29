import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    
    
    public static class VariableInfo {
        public String type; 

        public VariableInfo(String type) {
            this.type = type;
        }
    }

    
    private Map<String, VariableInfo> table;

    public SymbolTable() {
        this.table = new HashMap<>();
    }

    
    public void declareVariable(String name, String type) {
        if (table.containsKey(name)) {
            throw new RuntimeException("Syntax Error: Variable '" + name + "' is already declared!");
        }
        table.put(name, new VariableInfo(type));
        System.out.println("মেমোরি: '" + name + "' ভেরিয়েবলটি '" + type + "' হিসেবে সেভ করা হয়েছে।");
    }

    
    public String getVariableType(String name) {
        if (!table.containsKey(name)) {
            throw new RuntimeException("Syntax Error: Variable '" + name + "' has not been declared!");
        }
        return table.get(name).type;
    }
}
