/*******************************************************************
 * Name: Holly Hebert
 * Date: December 11, 2025
 * Assignment: SDC330 Week 5 – Final
 * Class: BankingApp (Final Demo)
 *
 * Final Notes:
 *  - Uses a single shared Scanner to avoid resource conflicts.
 *  - All interactive methods moved into BankManager.
 *  - Main class cleanly drives menu navigation only.
 *******************************************************************/

import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("      Week 5 - Banking Application      ");
        System.out.println("            By: Holly Hebert            ");
        System.out.println("========================================\n");

        DatabaseHelper dbHelper = new DatabaseHelper();
        Scanner scanner = new Scanner(System.in);
        BankManager manager = new BankManager(dbHelper, scanner);  // Shared Scanner injected

        boolean quit = false;

        while (!quit) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1) Create User");
            System.out.println("2) List Users");
            System.out.println("3) Create Account (link to user optional)");
            System.out.println("4) List All Accounts");
            System.out.println("5) List Accounts for User");
            System.out.println("6) Deposit");
            System.out.println("7) Withdraw");
            System.out.println("8) Update User Info");
            System.out.println("9) Delete Account");
            System.out.println("10) Delete User");
            System.out.println("11) Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        manager.createUserInteractive();
                        break;
                    case "2":
                        manager.displayAllUsers();
                        break;
                    case "3":
                        manager.createAccountInteractive();
                        break;
                    case "4":
                        manager.displayAllAccounts();
                        break;
                    case "5":
                        manager.displayAccountsByUser();
                        break;
                    case "6":
                        manager.depositInteractive();
                        break;
                    case "7":
                        manager.withdrawInteractive();
                        break;
                    case "8":
                        manager.updateUserInteractive();
                        break;
                    case "9":
                        manager.deleteAccountInteractive();
                        break;
                    case "10":
                        manager.deleteUserInteractive();
                        break;
                    case "11":
                        quit = true;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }

        // Leave Scanner open to avoid premature System.in closure
    }
}