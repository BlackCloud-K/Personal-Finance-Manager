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
            System.out.println("Press 3 to search transactions");
            System.out.println("Press 4 to view reports");
            System.out.println("Press 5 to exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            if (choice == 1) {
                AddNewCLI addNewCLI = new AddNewCLI(manager);
                addNewCLI.enterDetails();
            } else if (choice == 2) {
                BrowseCLI browseCLI = new BrowseCLI(manager);
                browseCLI.browseTransactions();
            } else if (choice == 3) {
                SearchCLI searchCLI = new SearchCLI(manager);
                searchCLI.searchMenu();
            }
            else if (choice == 4) {
                    ViewReportCLI viewReportCLI = new ViewReportCLI(manager);
                    viewReportCLI.chooseViewReport();   
            }
            else if (choice == 5) {
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
