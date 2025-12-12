import java.util.Scanner;

public class AddNewCLI {
    private FinanceManager manager;

    public AddNewCLI(FinanceManager manager) {
        this.manager = manager;
    }

    public void enterDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Add Transaction----------------");
        
        String type;
        while (true) {
            System.out.println("Enter the Transaction Type(Income/Expense): ");
            type = scanner.nextLine().trim();
            if (isValidType(type)) {
                break;
            } else {
                System.out.println("Invalid type! Please enter 'Income' or 'Expense'.");
            }
        }
        
        System.out.println("Enter the amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter the category: ");
        String category = scanner.nextLine();
        
        String date;
        while (true) {
            System.out.println("Enter the date(YYYY-MM-DD or YYYYMMDD): ");
            String inputDate = scanner.nextLine().trim();
            if (isValidDate(inputDate)) {
                date = formatDate(inputDate);
                break;
            } else {
                System.out.println("Invalid date format! Please enter date as YYYY-MM-DD or YYYYMMDD.");
            }
        }
        
        System.out.println("Enter the description: ");
        String description = scanner.nextLine();
        
        try {
            manager.addTransaction(amount, category, type, date, description);
            System.out.println("Transaction added successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Transaction not added.");
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
    
    private boolean isValidType(String type) {
        return type != null && (type.equalsIgnoreCase("Income") || type.equalsIgnoreCase("Expense"));
    }
    
    private boolean isValidDate(String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        
        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return isValidDateValue(date);
        }
        
        if (date.matches("\\d{8}")) {
            return isValidDateValue(formatDate(date));
        }
        
        return false;
    }
    
    private boolean isValidDateValue(String date) {
        try {
            String[] parts = date.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            
            if (year < 1000 || year > 9999) return false;
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;
            
            java.time.LocalDate.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private String formatDate(String date) {
        if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return date;
        }
        
        if (date.matches("\\d{8}")) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        }
        
        return date;
    }
}
