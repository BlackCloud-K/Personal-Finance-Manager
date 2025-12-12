import java.util.Scanner;

public class AddNewCLI {
    private FinanceManager manager;

    public AddNewCLI(FinanceManager manager) {
        this.manager = manager;
    }

    public void enterDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Add Transaction----------------");
        System.out.println("Enter the Transaction Type(Income/Expense): ");
        String type = scanner.nextLine();
        System.out.println("Enter the amount: ");
        double amount = scanner.nextDouble();
        System.out.println("Enter the category: ");
        String category = scanner.nextLine();
        System.out.println("Enter the date(YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.println("Enter the description: ");
        String description = scanner.nextLine();
        manager.addTransaction(amount, category, type, date, description);
        System.out.println("Transaction added successfully");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
        System.out.println("--------------------------------");
        scanner.close();
    }
}
