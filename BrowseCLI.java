import java.util.List;
import java.util.Scanner;

public class BrowseCLI {
    private FinanceManager manager;

    public BrowseCLI(FinanceManager manager) {
        this.manager = manager;
    }

    public void browseTransactions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--------------Browse Transactions----------------");
        List<Transaction> transactions = manager.getTransactions();
        displayTransactions(transactions);
        System.out.println("--------------------------------");
        System.out.println("Press 1 to exit...");
        System.out.println("Press 2 delete a transaction...");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice == 1) {
            System.out.println("Exiting...");
        } else if (choice == 2) {
            chooseDelete();
        }

    }

    private void displayTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void chooseDelete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------Delete Transaction----------------");
        System.out.println("Enter the transaction ID to delete: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine();
        try {
            manager.deleteTransaction(transactionId);
        } catch (Exception e) {
            System.out.println("Transaction not found");
            System.out.println("Press Enter to back to main menu...");
            scanner.nextLine();
            return;
        }
        System.out.println("Transaction deleted successfully");
        System.out.println("Press Enter to back to main menu...");
        scanner.nextLine();
    }
}
