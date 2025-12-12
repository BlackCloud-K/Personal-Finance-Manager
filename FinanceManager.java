import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FinanceManager {
    private List<Transaction> transactions;
    private String filepath;

    public FinanceManager(String filepath) {
        this.filepath = filepath;
        this.transactions = new ArrayList<Transaction>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filepath), StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] parts = line.split(",");
                double amount = Double.parseDouble(parts[0]);
                String date = parts[1];
                String description = parts[2];
                String category = parts[3];
                transactions.add(new Transaction(amount, date, description, category));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(double amount, String category, String type, String date, String description){
        Transaction newTransaction = new Transaction(amount, category, date, description);
        transactions.add(newTransaction);
    }

    public void saveToFile(){
        try {
            List<String> lines = new ArrayList<>();
            for (Transaction transaction : transactions) {
                lines.add(transaction.getAmount() + "," + transaction.getCategory() + "," + transaction.getDate() + "," + transaction.getDescription());
            }
            Files.write(Paths.get(filepath), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
