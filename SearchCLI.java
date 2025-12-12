import java.util.List;
import java.util.Scanner;

public class SearchCLI {

    private FinanceManager manager;

    public SearchCLI(FinanceManager manager) {
        this.manager = manager;
    }

    public void searchMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--------------Search Menu----------------");
            System.out.println("Press 1 to search by date (YYYY-MM-DD)");
            System.out.println("Press 2 to search by category");
            System.out.println("Press 0 to return to main menu");
            System.out.print("Your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            if (choice == 0) {
                break;
            } else if (choice == 1) {
                System.out.print("Enter date (YYYY-MM-DD): ");
                String date = scanner.nextLine().trim();
                List<Transaction> results = manager.searchByDate(date);
                displayResults(results);
                askDelete(results, scanner);
            } else if (choice == 2) {
                System.out.print("Enter category: ");
                String category = scanner.nextLine().trim();
                List<Transaction> results = manager.searchByCategory(category);
                displayResults(results);
                askDelete(results, scanner);
            } else {
                System.out.println("Invalid choice.");
            }
        }

        // Do not close the scanner here to avoid closing System.in
    }

    private void displayResults(List<Transaction> results) {
        System.out.println("--------------Search Results----------------");
        if (results == null || results.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : results) {
                System.out.println(t.toString());
            }
        }
        System.out.println("--------------------------------------------");
    }

    private void askDelete(List<Transaction> results, Scanner scanner) {
        if (results == null || results.isEmpty()) {
            return;
        }

        System.out.print("Do you want to delete one of these transactions? (y/n): ");
        String answer = scanner.nextLine().trim();

        if (!answer.equalsIgnoreCase("y")) {
            return;
        }

        System.out.print("Enter the transaction ID to delete (or 0 to cancel): ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Invalid ID.");
            return;
        }

        if (id == 0) {
            return;
        }

        try {
            manager.deleteTransaction(id);
            System.out.println("Transaction with ID " + id + " deleted.");
        } catch (IllegalArgumentException e) {
            System.out.println("Delete failed: " + e.getMessage());
        }
    }
}
