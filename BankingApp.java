/*******************************************************************
 * Name: Holly Hebert
 * Date: December 2, 2025
 * Assignment: SDC330 Week 4 – Database Interactions (SQLite)
 * Class: BankingApp
 * Purpose:
 *  - Provides a console menu for full CRUD operations using SQLite.
 *  - Works with the updated BankManager, ensuring all DB operations
 *    correctly record deposits, withdrawals, user creation, and deletes.
 *******************************************************************/

import java.util.List;
import java.util.Scanner;

public class BankingApp {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Week 4 Demonstration - Banking App   ");
        System.out.println("            By: Holly Hebert            ");
        System.out.println("========================================\n");

        System.out.println("Welcome! This demo shows full CRUD with SQLite.");
        System.out.println("IMPORTANT:");
        System.out.println("User IDs are shown in 'List Users' (Option 2).");
        System.out.println("Account numbers are auto-generated AND displayed.");
        System.out.println("Always reference the printed ID/Account Number.\n");

        DatabaseHelper dh = new DatabaseHelper();
        BankManager manager = new BankManager(dh);
        Scanner scanner = new Scanner(System.in);

        boolean quit = false;

        while (!quit) {
            System.out.println("\n--- Main Menu (Week 4 DB) ---");
            System.out.println("1. Create User");
            System.out.println("2. List Users (WITH IDs)");
            System.out.println("3. Create Account (for user)");
            System.out.println("4. List All Accounts");
            System.out.println("5. List Accounts for User");
            System.out.println("6. Deposit / Withdraw");
            System.out.println("7. Update User Info");
            System.out.println("8. Delete Account");
            System.out.println("9. Delete User");
            System.out.println("10. Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1": {
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Address: ");
                    String addr = scanner.nextLine();
                    System.out.print("Phone: ");
                    String ph = scanner.nextLine();

                    int newId = manager.createUser(name, addr, ph);
                    if (newId > 0) System.out.println("Created user with ID: " + newId);
                    else System.out.println("Failed to create user.");
                    break;
                }

                case "2": {
                    List<String> usersFormatted = manager.getAllUsersFormatted();
                    System.out.println("--- Users (with IDs) ---");
                    if (usersFormatted.isEmpty()) System.out.println("No users found.");
                    else usersFormatted.forEach(System.out::println);
                    break;
                }

                case "3": {
                    System.out.print("User ID to attach account (or press Enter for none): ");
                    String uidStr = scanner.nextLine().trim();
                    Integer uid = null;
                    if (!uidStr.isEmpty()) {
                        try { uid = Integer.parseInt(uidStr); } catch (Exception ignored) {}
                    }

                    System.out.print("Account type (Checking/Savings/IRA): ");
                    String type = scanner.nextLine();

                    System.out.print("Starting balance: ");
                    double bal = Double.parseDouble(scanner.nextLine().trim());

                    String acctNum = "ACCT" + (int)(Math.random() * 100000);

                    boolean created = manager.createAccountRecord(acctNum, bal, type, uid);
                    System.out.println(created ? "Created account: " + acctNum
                                               : "Failed to create account.");
                    break;
                }

                case "4": {
                    List<String> accounts = manager.getAllAccountsFormatted();
                    System.out.println("--- All Accounts ---");
                    if (accounts.isEmpty()) System.out.println("No accounts.");
                    else accounts.forEach(System.out::println);
                    break;
                }

                case "5": {
                    System.out.print("Enter User ID: ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    List<String> accts = manager.getAccountsByUser(userId);
                    System.out.println("--- Accounts for User " + userId + " ---");
                    if (accts.isEmpty()) System.out.println("No accounts for that user.");
                    else accts.forEach(System.out::println);
                    break;
                }

                case "6": {
                    System.out.print("Account number: ");
                    String accNum = scanner.nextLine().trim();

                    System.out.print("Deposit or Withdraw (D/W): ");
                    String direction = scanner.nextLine().trim().toUpperCase();

                    System.out.print("Amount: ");
                    double amt = Double.parseDouble(scanner.nextLine().trim());

                    double delta = direction.equals("D") ? amt : -amt;

                    boolean ok = manager.updateAccountBalance(accNum, delta);
                    System.out.println(ok ? "Transaction applied." : "Transaction failed.");
                    break;
                }

                case "7": {
                    System.out.print("User ID to update: ");
                    int uid = Integer.parseInt(scanner.nextLine().trim());

                    System.out.print("New name: ");
                    String newName = scanner.nextLine();

                    System.out.print("New address: ");
                    String newAddr = scanner.nextLine();

                    System.out.print("New phone: ");
                    String newPhone = scanner.nextLine();

                    boolean updated = manager.updateUser(uid, newName, newAddr, newPhone);
                    System.out.println(updated ? "User updated." : "Update failed.");
                    break;
                }

                case "8": {
                    System.out.print("Account number to delete: ");
                    String acctDel = scanner.nextLine();
                    boolean delOk = manager.deleteAccount(acctDel);
                    System.out.println(delOk ? "Account deleted." : "Delete failed.");
                    break;
                }

                case "9": {
                    System.out.print("User ID to delete: ");
                    int delUid = Integer.parseInt(scanner.nextLine());
                    boolean delUserOk = manager.deleteUser(delUid);
                    System.out.println(delUserOk ? "User deleted." : "Delete failed.");
                    break;
                }

                case "10":
                    quit = true;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}