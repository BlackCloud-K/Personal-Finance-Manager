import java.util.Scanner;

public class ViewReportCLI {
    private FinanceManager manager;

    public ViewReportCLI(FinanceManager manager) {
        this.manager = manager;
    }

    public void chooseViewReport() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--------------View Reports----------------");
            System.out.println("Press 1 to view Daily Report");
            System.out.println("Press 2 to view Weekly Report");
            System.out.println("Press 3 to view Monthly Report");
            System.out.println("Press 4 to view Statistical Summary");
            System.out.println("Press 5 to back to main menu");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) viewDailyReport();
            else if (choice == 2) viewWeeklyReport();
            else if (choice == 3) viewMonthlyReport();
            else if (choice == 4) viewStatisticalSummary();
            else if (choice == 5) return;
            else System.out.println("Invalid choice");
        }
    }

    public void viewDailyReport() {
        System.out.println(manager.getDailyReport());
        pressEnterToContinue();
    }

    public void viewWeeklyReport() {
        System.out.println(manager.getWeeklyReport());
        pressEnterToContinue();
    }

    public void viewMonthlyReport() {
        System.out.println(manager.getMonthlyReport());
        pressEnterToContinue();
    }

    public void viewStatisticalSummary() {
        System.out.println(manager.getStatisticalSummary());
        pressEnterToContinue();
    }

    private void pressEnterToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
