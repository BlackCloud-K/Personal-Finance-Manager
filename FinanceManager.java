import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
                String type = parts[1];
                double amount = Double.parseDouble(parts[2]);
                String category = parts[3];
                String date = parts[4];
                String description = parts[5];
                transactions.add(new Transaction(id, type, amount, date, description, category));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTransaction(double amount, String category, String type, String date, String description){
        if (!isValidType(type)) {
            throw new IllegalArgumentException("Transaction type must be 'Income' or 'Expense', but got: " + type);
        }
        Transaction newTransaction = new Transaction(type, amount, date, description, category);
        transactions.add(newTransaction);
    }
    
    private boolean isValidType(String type) {
        return type != null && (type.equalsIgnoreCase("Income") || type.equalsIgnoreCase("Expense"));
    }

    public void saveToFile(){
        try {
            List<String> lines = new ArrayList<>();
            for (Transaction transaction : transactions) {
                lines.add(transaction.getId() + "," + transaction.getType() + "," + transaction.getAmount() + "," + transaction.getCategory() + "," + transaction.getDate() + "," + transaction.getDescription());
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
        Set<LocalDate> datesWithData = new TreeSet<>();
        
        for (Transaction t : transactions) {
            LocalDate d = parseDate(t.getDate());
            if (d != null) {
                datesWithData.add(d);
            }
        }
        
        if (datesWithData.isEmpty()) {
            return "--------------Daily Report--------------\n"
                + "No transactions found\n"
                + "----------------------------------------------";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("--------------Daily Report--------------\n");
        
        for (LocalDate date : datesWithData) {
            String dayReport = buildPeriodReport("", date, date);
            String[] lines = dayReport.split("\n");
            if (lines.length > 1) {
                report.append("Date: ").append(date).append("\n");
                for (int i = 1; i < lines.length - 1; i++) {
                    if (lines[i].startsWith("Range:")) continue;
                    report.append(lines[i]).append("\n");
                }
                report.append("\n");
            }
        }
        
        report.append("----------------------------------------------");
        return report.toString();
    }

    public String getWeeklyReport() {
        Set<LocalDate> weekStarts = new TreeSet<>();
        
        for (Transaction t : transactions) {
            LocalDate d = parseDate(t.getDate());
            if (d != null) {
                LocalDate weekStart = d.with(DayOfWeek.MONDAY);
                weekStarts.add(weekStart);
            }
        }
        
        if (weekStarts.isEmpty()) {
            return "--------------Weekly Report--------------\n"
                + "No transactions found\n"
                + "----------------------------------------------";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("--------------Weekly Report--------------\n");
        
        for (LocalDate weekStart : weekStarts) {
            LocalDate weekEnd = weekStart.plusDays(6);
            String weekReport = buildPeriodReport("", weekStart, weekEnd);
            String[] lines = weekReport.split("\n");
            if (lines.length > 1) {
                report.append("Week: ").append(weekStart).append(" to ").append(weekEnd).append("\n");
                for (int i = 1; i < lines.length - 1; i++) {
                    if (lines[i].startsWith("Range:")) continue;
                    report.append(lines[i]).append("\n");
                }
                report.append("\n");
            }
        }
        
        report.append("----------------------------------------------");
        return report.toString();
    }

    public String getMonthlyReport() {
        Set<String> monthsWithData = new TreeSet<>();
        
        for (Transaction t : transactions) {
            LocalDate d = parseDate(t.getDate());
            if (d != null) {
                String monthKey = d.getYear() + "-" + String.format("%02d", d.getMonthValue());
                monthsWithData.add(monthKey);
            }
        }
        
        if (monthsWithData.isEmpty()) {
            return "--------------Monthly Report--------------\n"
                + "No transactions found\n"
                + "----------------------------------------------";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("--------------Monthly Report--------------\n");
        
        for (String monthKey : monthsWithData) {
            String[] parts = monthKey.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            LocalDate monthStart = LocalDate.of(year, month, 1);
            LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);
            
            String monthReport = buildPeriodReport("", monthStart, monthEnd);
            String[] lines = monthReport.split("\n");
            if (lines.length > 1) {
                report.append("Month: ").append(monthKey).append("\n");
                for (int i = 1; i < lines.length - 1; i++) {
                    if (lines[i].startsWith("Range:")) continue;
                    report.append(lines[i]).append("\n");
                }
                report.append("\n");
            }
        }
        
        report.append("----------------------------------------------");
        return report.toString();
    }


    private String buildPeriodReport(String title, LocalDate start, LocalDate end) {
        double income = 0.0;
        double expense = 0.0;

        for (Transaction t : transactions) {
            LocalDate d = parseDate(t.getDate());
            if (d == null) continue; // skip invalid date rows

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

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        
        String date = dateStr.trim();
        
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
        }
        
        if (date.length() == 8 && date.matches("\\d{8}")) {
            try {
                int year = Integer.parseInt(date.substring(0, 4));
                int month = Integer.parseInt(date.substring(4, 6));
                int day = Integer.parseInt(date.substring(6, 8));
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
            }
        }
        
        String[] parts = date.split("-");
        if (parts.length == 3) {
            try {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
            }
        }
        
        return null;
    }

    public List<Transaction> searchByDate(String date) {
        List<Transaction> results = new ArrayList<>();
        if (date == null) return results;

        String target = date.trim();
        for (Transaction t : transactions) {
            if (t.getDate() != null && t.getDate().trim().equals(target)) {
                results.add(t);
            }
        }
        return results;
    }

    public List<Transaction> searchByCategory(String category) {
        List<Transaction> results = new ArrayList<>();
        if (category == null) return results;

        String target = category.trim().toLowerCase();
        for (Transaction t : transactions) {
            String cat = t.getCategory();
            if (cat != null && cat.trim().toLowerCase().equals(target)) {
                results.add(t);
            }
        }
        return results;
    }

}
