/*******************************************************************
 * Name: Holly Hebert
 * Date: November 30, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Class: BankManager
 * 
 * Description:
 * This class implements the BankInterface and provides concrete 
 * behavior for high-level banking operations such as creating 
 * accounts, displaying account lists, updating user information, 
 * and processing transactions.
 * 
 * Week 3 Notes:
 * - This class demonstrates **interface-based abstraction**, since 
 *   BankManager must provide implementations for all methods 
 *   defined in BankInterface.
 *
 * - Although Week 3 focuses primarily on abstract classes,
 *   constructors, and access specifiers, this class remains unchanged
 *   from Week 2 because it does not itself require modification to 
 *   demonstrate those concepts. 
 *
 * - BankManager will be used again in Week 4 when database 
 *   functionality is introduced.
 *******************************************************************/

import java.util.Scanner;

public class BankManager implements BankInterface {

    // Scanner is private, demonstrating encapsulation and correct access specifier usage.
    private Scanner scanner = new Scanner(System.in);

    /*
     * Creates a new account for the given User object.
     * This method demonstrates:
     *   - Interface implementation (method required by BankInterface)
     *   - Object instantiation of derived classes (Checking, Savings, IRA)
     *   - Composition (User "has" a list of accounts)
    */
    @Override
    public void createAccount(User user) {
        System.out.println("\n--- Create New Account ---");
        System.out.println("Choose an account type:");
        System.out.println("1. Checking");
        System.out.println("2. Savings");
        System.out.println("3. IRA");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer

        // Collect initial balance for the new account
        System.out.print("Enter starting balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        // Generate a simple random account number
        String accountNumber = "ACCT" + (int)(Math.random() * 10000);

        switch (choice) {
            case 1:
                user.addAccount(new CheckingAccount(accountNumber, balance));
                System.out.println("Checking account created!");
                break;

            case 2:
                user.addAccount(new SavingsAccount(accountNumber, balance));
                System.out.println("Savings account created!");
                break;

            case 3:
                System.out.print("Enter yearly contribution amount: ");
                double contribution = scanner.nextDouble();
                scanner.nextLine();
                user.addAccount(new IRAAccount(accountNumber, balance, contribution));
                System.out.println("IRA account created!");
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    @Override
    public void displayAccounts(User user) {
        System.out.println("\n--- Account Summary ---");

        // Demonstrates polymorphism by calling getInfo() on different account types.
        for (Account acc : user.getAccounts()) {
            System.out.println(acc.getInfo());
        }
    }

    @Override
    public void updateUserInfo(User user) {
        System.out.println("\n--- Update User Information ---");
        System.out.println("1. Update Name");
        System.out.println("2. Update Address");
        System.out.println("3. Update Phone Number");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("New name: ");
                user.setName(scanner.nextLine());
                break;

            case 2:
                System.out.print("New address: ");
                user.setAddress(scanner.nextLine());
                break;

            case 3:
                System.out.print("New phone number: ");
                user.setPhone(scanner.nextLine());
                break;

            default:
                System.out.println("Invalid option.");
        }

        System.out.println("User information updated!");
    }

    @Override
    public void processTransaction(User user) {
        System.out.println("\n--- Transaction Menu ---");
        System.out.print("Enter account number: ");
        String acctNum = scanner.nextLine();

        Account acc = user.findAccount(acctNum);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (choice == 1) {
            acc.deposit(amount);
            System.out.println("Deposit successful.");
        } else if (choice == 2) {
            acc.withdraw(amount);
        } else {
            System.out.println("Invalid option.");
        }
    }
}