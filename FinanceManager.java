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

    public String getStatisticalSummary() {
    double income = 0.0;
    double expense = 0.0;

    for (Transaction t : transactions) {
        if (isIncome(t)) income += t.getAmount();
        else expense += t.getAmount();
    }

    double net = income - expense;

    return "--------------Statistical Summary--------------\n"
         + "Total Income: " + String.format("%.2f", income) + "\n"
         + "Total Expense: " + String.format("%.2f", expense) + "\n"
         + "Net Balance: " + String.format("%.2f", net) + "\n"
         + "----------------------------------------------";
}

public String getDailyReport() {
    LocalDate today = LocalDate.now();
    return buildPeriodReport("Daily Report", today, today);
}

public String getWeeklyReport() {
    LocalDate today = LocalDate.now();
    LocalDate start = today.with(DayOfWeek.MONDAY);
    LocalDate end = start.plusDays(6);
    return buildPeriodReport("Weekly Report", start, end);
}

public String getMonthlyReport() {
    LocalDate today = LocalDate.now();
    LocalDate start = today.withDayOfMonth(1);
    LocalDate end = start.plusMonths(1).minusDays(1);
    return buildPeriodReport("Monthly Report", start, end);
}


private String buildPeriodReport(String title, LocalDate start, LocalDate end) {
    double income = 0.0;
    double expense = 0.0;

    for (Transaction t : transactions) {
        LocalDate d;
        try {
            d = LocalDate.parse(t.getDate()); // expects YYYY-MM-DD
        } catch (Exception ex) {
            continue; // skip invalid date rows
        }

        if (d.isBefore(start) || d.isAfter(end)) continue;

        if (isIncome(t)) income += t.getAmount();
        else expense += t.getAmount();
    }

    double net = income - expense;

    return "--------------" + title + "--------------\n"
         + "Range: " + start + " to " + end + "\n"
         + "Total Income: " + String.format("%.2f", income) + "\n"
         + "Total Expense: " + String.format("%.2f", expense) + "\n"
         + "Net Balance: " + String.format("%.2f", net) + "\n"
         + "----------------------------------------------";
}

private boolean isIncome(Transaction t) {
    String type = t.getType();
    return type != null && type.equalsIgnoreCase("Income");
}
}
