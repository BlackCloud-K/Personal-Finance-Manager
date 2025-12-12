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
        System.out.println("Enter the date(YYYY-MM-DD): ");
        String date = scanner.nextLine();
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
}
