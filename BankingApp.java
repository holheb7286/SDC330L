/*******************************************************************
 * Name: Holly Hebert
 * Date: December 2, 2025
 * Assignment: SDC330 Week 4 – Database Interactions (SQLite)
 * Class: BankingAppWeek4 (Demonstration)
 *
 * Purpose:
 *  - Small console menu that demonstrates full CRUD operations
 *    against an SQLite database via BankManagerDB.
 *  - Shows example flows for creating users/accounts, reading lists,
 *    updating deposits/withdrawals, and deleting accounts/users.
 *
 * Instructions:
 *  - When entering IDs, enter numeric user_id shown in "List Users".
 *  - Account numbers are auto-generated and displayed when created.
 *  - This demo stores data in banking.db in the working directory.
 *******************************************************************/

import java.util.List;
import java.util.Scanner;
public class BankingApp {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Week 4 Demonstration - Banking App   ");
        System.out.println("            By: Holly Hebert            ");
        System.out.println("========================================\n");

        System.out.println("Welcome! This demo shows basic database CRUD using SQLite.");
        System.out.println("Notes: enter numeric IDs when prompted (user_id). Account numbers are");
        System.out.println("auto-generated. Deleting a user will not automatically delete accounts");
        System.out.println("unless you implement cascading FK; this demo keeps behavior explicit.\n");

        DatabaseHelper dh = new DatabaseHelper();
        BankManager manager = new BankManager(dh);
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("\n--- Main Menu (Week 4 DB) ---");
            System.out.println("1. Create User");
            System.out.println("2. List Users");
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
                case "1":
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

                case "2":
                    List<User> users = manager.getAllUsers();
                    System.out.println("--- Users ---");
                    if (users.isEmpty()) System.out.println("No users found.");
                    else {
                        // Note: User class doesn't carry DB id by design; to show ids,
                        // you'd add an id field to User. For now we print name/address.
                        for (User u : users) {
                            System.out.println("Name: " + u.getName() + " | Address: " + u.getAddress() + " | Phone: " + u.getPhone());
                        }
                    }
                    break;

                case "3":
                    System.out.print("User ID to attach account (or press Enter for none): ");
                    String uidStr = scanner.nextLine().trim();
                    Integer uid = null;
                    if (!uidStr.isEmpty()) {
                        try { uid = Integer.parseInt(uidStr); } catch (NumberFormatException e) { uid = null; }
                    }
                    System.out.print("Account type (Checking/Savings/IRA): ");
                    String typ = scanner.nextLine();
                    System.out.print("Starting balance: ");
                    double bal = Double.parseDouble(scanner.nextLine().trim());
                    String acctNum = "ACCT" + (int)(Math.random() * 100000);
                    boolean created = manager.createAccountRecord(acctNum, bal, typ, uid);
                    System.out.println(created ? "Created account: " + acctNum : "Failed to create account.");
                    break;

                case "4":
                    List<String> allAccts = manager.getAllAccountsRaw();
                    System.out.println("--- All Accounts ---");
                    if (allAccts.isEmpty()) System.out.println("No accounts.");
                    else allAccts.forEach(System.out::println);
                    break;

                case "5":
                    System.out.print("Enter user ID: ");
                    int qid = Integer.parseInt(scanner.nextLine().trim());
                    List<String> userAccounts = manager.getAccountsByUser(qid);
                    System.out.println("--- Accounts for User " + qid + " ---");
                    if (userAccounts.isEmpty()) System.out.println("No accounts for that user.");
                    else userAccounts.forEach(System.out::println);
                    break;

                case "6":
                    System.out.print("Account number: ");
                    String accn = scanner.nextLine().trim();
                    System.out.print("Deposit or Withdraw (D/W): ");
                    String t = scanner.nextLine().trim().toUpperCase();
                    System.out.print("Amount: ");
                    double am = Double.parseDouble(scanner.nextLine().trim());
                    double delta = t.equals("D") ? am : -am;
                    boolean ok = manager.updateAccountBalance(accn, delta);
                    System.out.println(ok ? "Transaction applied." : "Transaction failed.");
                    break;

                case "7":
                    System.out.print("Enter user ID to update: ");
                    int uid2 = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("New name: ");
                    String nn = scanner.nextLine();
                    System.out.print("New address: ");
                    String na = scanner.nextLine();
                    System.out.print("New phone: ");
                    String np = scanner.nextLine();
                    boolean updated = manager.updateUser(uid2, nn, na, np);
                    System.out.println(updated ? "User updated." : "Update failed.");
                    break;

                case "8":
                    System.out.print("Account number to delete: ");
                    String delAcct = scanner.nextLine().trim();
                    boolean delOk = manager.deleteAccount(delAcct);
                    System.out.println(delOk ? "Account deleted." : "Delete failed.");
                    break;

                case "9":
                    System.out.print("User ID to delete: ");
                    int delUid = Integer.parseInt(scanner.nextLine().trim());
                    boolean delUserOk = manager.deleteUser(delUid);
                    System.out.println(delUserOk ? "User deleted." : "Delete failed.");
                    break;

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