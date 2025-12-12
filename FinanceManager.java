import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                double amount = Double.parseDouble(parts[1]);
                String category = parts[2];
                String date = parts[3];
                String description = parts[4];
                transactions.add(new Transaction(id, amount, date, description, category));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(double amount, String category, String type, String date, String description){
        Transaction newTransaction = new Transaction(amount, date, description, category);
        transactions.add(newTransaction);
    }

    public void saveToFile(){
        try {
            List<String> lines = new ArrayList<>();
            for (Transaction transaction : transactions) {
                lines.add(transaction.getId() + "," + transaction.getAmount() + "," + transaction.getCategory() + "," + transaction.getDate() + "," + transaction.getDescription());
            }
            Files.write(Paths.get(filepath), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deleteTransaction(int transactionId){
        boolean removed = transactions.removeIf(t -> t.getId() == transactionId);
        if (!removed) {
            throw new IllegalArgumentException("Transaction not found");
        }
    }
}
