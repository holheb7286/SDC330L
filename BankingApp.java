/*
* Name: Holly Hebert
* Date: November 20, 2025
* Assignment: SDC330 Week 2 - Banking Application
* Class: BankingApp
* Description:
* This is the main application class for the Banking Application.
* It provides a console-based interface for users to interact
* with the banking system, demonstrating OOP concepts such as
* inheritance, polymorphism, composition, and interface implementation.
*/

import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankManager manager = new BankManager();

        // Create a sample user (Week 2 requires realistic object instantiation)
        User user = new User("Holly Hebert", "123 Main St", "555-1234");

        System.out.println("========================================");
        System.out.println("     Week 2 - Banking Application     ");
        System.out.println("            By: Holly Hebert          ");
        System.out.println("========================================");

        System.out.println("\nWelcome! This application demonstrates OOP concepts:");
        System.out.println("- Inheritance");
        System.out.println("- Polymorphism");
        System.out.println("- Composition");
        System.out.println("- Interface implementation");
        System.out.println("\nChoose an action from the menu.\n");

        int choice;

        do {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Create Account");
            System.out.println("2. Display Accounts");
            System.out.println("3. Update User Info");
            System.out.println("4. Process Transaction");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manager.createAccount(user);
                    break;

                case 2:
                    manager.displayAccounts(user);
                    break;

                case 3:
                    manager.updateUserInfo(user);
                    break;

                case 4:
                    manager.processTransaction(user);
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 5);

        scanner.close();
    }
}