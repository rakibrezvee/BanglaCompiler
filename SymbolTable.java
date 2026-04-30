import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    public static class VariableInfo {
        public String type; 
        public VariableInfo(String type) {
            this.type = type;
        }
    }

    private Map<String, VariableInfo> table = new HashMap<>();

    public void declareVariable(String name, String type) {
        if (table.containsKey(name)) {
            throw new RuntimeException("সেম্যান্টিক এরর: '" + name + "' ভেরিয়েবলটি আগেই ডিক্লেয়ার করা হয়েছে!");
        }
        table.put(name, new VariableInfo(type));
    }

    public String getVariableType(String name) {
        if (!table.containsKey(name)) {
            throw new RuntimeException("সেম্যান্টিক এরর: '" + name + "' ভেরিয়েবলটি ডিক্লেয়ার করা হয়নি!");
        }
        return table.get(name).type;
    }
}
