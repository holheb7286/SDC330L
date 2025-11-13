/*******************************************************************
* Name: Holly Hebert
* Date: November 13, 2025
* Assignment: SDC330 Week 1 - Banking Inheritance & Composition
* Class: BankingDemo (Main Application)
* Description:
* This is the main entry point of the program. It demonstrates
* inheritance (Account → Checking/Savings) and composition (User
* has Accounts). Displays a welcome message and user account info.
*******************************************************************/

public class BankingDemo {
    public static void main(String[] args) {
        // Display assignment header
        System.out.println("Holly Hebert - SDC330L Week 1 - Banking Inheritance & Composition");
        System.out.println("------------------------------------------------------------\n");

        // Welcome message
        System.out.println("Welcome to Week 1 Banking App Demo!");
        System.out.println("This program demonstrates inheritance and composition using simple banking classes.\n");

        // Create a user (composition)
        User user = new User("Victor Frankenstein", "victor.frankenstein@email.com");

        // Create a checking and a savings account (inheritance)
        CheckingAccount chk = new CheckingAccount("CHK-101", 1500.00);
        SavingsAccount sav = new SavingsAccount("SAV-202", 3000.00);

        // Add accounts to user
        user.addAccount(chk);
        user.addAccount(sav);

        // Perform some transactions
        chk.deposit(250.00);
        sav.withdraw(500.00);

        // Display all user info
        System.out.println(user.getUserProfile());
        }
    }