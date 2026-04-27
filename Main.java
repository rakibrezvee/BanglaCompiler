import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Here is a test line of your Bangla programming language
        String banglaCode = "shongkha a = 10 + 5;";
        
        System.out.println("Translating: " + banglaCode);
        System.out.println("---------------------------------");
        
        Lexer lexer = new Lexer(banglaCode);
        List<Token> tokens = lexer.tokenize();
        
        for (Token t : tokens) {
            System.out.println(t);
        }
    }
}