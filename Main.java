import java.util.List;
import java.io.PrintWriter;
import java.io.File;
import java.awt.Desktop;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in, "UTF-8");
        File report = new File("compiler_result.txt");

        System.out.println("--- Bangla Compiler ---");
        System.out.print("Enter your Bangla code: ");
        String banglaCode = sc.nextLine(); 

        try {
            Lexer lexer = new Lexer(banglaCode);
            List<Token> tokens = lexer.tokenize();
            SymbolTable memory = new SymbolTable();
            Parser parser = new Parser(tokens, memory);
            parser.parse();

            String outputContent = "ফলাফল: " + parser.getLastResult() + "\nটোকেন: " + tokens;
            saveAndOpenReport(report, "সফল", banglaCode, outputContent);

        } catch (Exception e) {
            saveAndOpenReport(report, "ত্রুটি", banglaCode, e.getMessage());
        } finally {
            sc.close();
        }
    }

    private static void saveAndOpenReport(File file, String status, String code, String message) {
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.println("অবস্থা: " + status + "\nকোড: " + code + "\n" + message);
            writer.flush();
            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
        } catch (Exception ex) { 
            ex.printStackTrace(); // ✅ FIXED: was e.printStackTrace()
        }
    }
}
