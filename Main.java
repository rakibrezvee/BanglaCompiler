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
        System.out.println("Note: Type your code (e.g., সংখ্যা ক = ১০ + ৫;) and press Enter.");
        System.out.print("Enter your Bangla code: ");
        
        String banglaCode = sc.nextLine(); 

        try {
            
            Lexer lexer = new Lexer(banglaCode);
            List<Token> tokens = lexer.tokenize();
            
            
            SymbolTable memory = new SymbolTable();
            Parser parser = new Parser(tokens, memory);
            parser.parse();

            
            int finalResult = parser.getLastResult();

            
            saveAndOpenReport(report, "সফল (Success)", banglaCode, 
                "কোডটি সঠিকভাবে রান হয়েছে।\nগাণিতিক ফলাফল: " + finalResult);

        } catch (Exception e) {
            
            saveAndOpenReport(report, "ত্রুটি (Error)", banglaCode, "ত্রুটির বিবরণ: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("Process finished! Check the notepad popup for results.");
        }
    }

    
    private static void saveAndOpenReport(File file, String status, String code, String message) {
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
            writer.println("|--- কম্পাইলার রিপোর্ট ---|");
            writer.println("অবস্থা: " + status);
            writer.println("সোর্স কোড: " + code);
            writer.println("---------------------------");
            writer.println("\nবিস্তারিত বিবরণ:");
            writer.println(message);
            writer.println("\n|-------------------------|");
            writer.close();

            
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception ex) {
            System.out.println("Report error: " + ex.getMessage());
        }
    }
}
