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
            
            System.out.println("Generated Tokens: " + tokens); 

            SymbolTable memory = new SymbolTable();
            Parser parser = new Parser(tokens, memory);
            parser.parse();

            int finalResult = parser.getLastResult();

            
            String outputContent = "ফলাফল: " + finalResult + 
                                 "\n\n---------------------------\n" +
                                 "বিস্তারিত বিবরণ:\n" +
                                 "টোকেন লিস্ট: " + tokens;

            saveAndOpenReport(report, "সফল (Success)", banglaCode, outputContent);

        } catch (Exception e) {
            saveAndOpenReport(report, "ত্রুটি (Error)", banglaCode, "ত্রুটির বিবরণ: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Result generated in notepad!");
        }
    }

    private static void saveAndOpenReport(File file, String status, String code, String message) {
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.println("|--- কম্পাইলার রিপোর্ট ---|");
            writer.println("অবস্থা: " + status);
            writer.println("সোর্স কোড: " + code);
            writer.println("\n" + message); 
            writer.println("\n|-------------------------|");
            writer.flush(); 
            writer.close();

            
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                
                Runtime.getRuntime().exec("notepad.exe " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            System.out.println("Report error: " + ex.getMessage());
        }
    }
}
