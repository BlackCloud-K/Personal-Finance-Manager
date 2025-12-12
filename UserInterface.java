import java.util.Scanner;

public class UserInterface {
    private FinanceManager manager;

    public UserInterface(String filepath) {
        this.manager = new FinanceManager(filepath);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
        System.out.println("--------------Main Menu----------------");
        System.out.println("Press 1 to add a new transaction");
        System.out.println("Press 2 to browse transactions");
        System.out.println("Press 3 to exit");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        if (choice == 1) {
            AddNewCLI addNewCLI = new AddNewCLI(manager);
            addNewCLI.enterDetails();
        } else if (choice == 2) {
            BrowseCLI browseCLI = new BrowseCLI(manager);
            browseCLI.browseTransactions();
        } else if (choice == 3) {
            System.out.println("Exiting...");
            break;
        } else {
            System.out.println("Invalid choice");
            }
        }
        manager.saveToFile();
        scanner.close();
    }
}
