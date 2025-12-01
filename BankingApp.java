/*******************************************************************
 * Name: Holly Hebert
 * Date: November 30, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Class: BankingAppWeek3 (Demonstration Application)
 * 
 * Description:
 * This small demonstration program showcases key OOP concepts:
 * 
 *  - Abstraction:
 *       The Account class is abstract and cannot be instantiated.
 *       Derived classes override the abstract getInfo() method.
 *
 *  - Constructors:
 *       Multiple constructors in the Account hierarchy allow flexible
 *       object creation (e.g., full-argument vs. accountNumber-only).
 *
 *  - Access Specifiers:
 *       - private fields (encapsulation)
 *       - protected constructors (only child classes can instantiate)
 *       - public methods (accessed externally by application classes)
 *
 *  - Instantiation & Display:
 *       Objects are instantiated with realistic data and printed.
 *
 * This Week 3 app is intentionally simple and separate from the full
 * Week 2 menu-driven application. It serves as a learning checkpoint
 * before implementing database features in Week 4.
 *******************************************************************/

public class BankingApp {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   Week 3 Demonstration - Banking App   ");
        System.out.println("            By: Holly Hebert            ");
        System.out.println("========================================\n");

        System.out.println("Welcome! This demo showcases abstraction, constructors,");
        System.out.println("and access specifiers in the Banking Application.\n");

        // ----------------------------------------------------------
        // Demonstrate Constructors & Abstraction
        // ----------------------------------------------------------

        // CheckingAccount: using FULL constructor (acct number + balance)
        CheckingAccount checking1 = new CheckingAccount("ACCT1001", 250.00);

        // CheckingAccount: using OVERLOADED constructor (acct number only)
        CheckingAccount checking2 = new CheckingAccount("ACCT1002");

        // SavingsAccount: using FULL constructor
        SavingsAccount savings1 = new SavingsAccount("ACCT2001", 500.00);

        // SavingsAccount: using OVERLOADED constructor
        SavingsAccount savings2 = new SavingsAccount("ACCT2002");

        // IRAAccount: using FULL constructor
        IRAAccount ira1 = new IRAAccount("ACCT3001", 1500.00, 300.00);

        // ----------------------------------------------------------
        // Demonstrate Polymorphism & Abstraction
        // ----------------------------------------------------------
        // Account is abstract → cannot instantiate directly.
        // But we CAN hold child objects with an Account reference.

        Account[] accounts = { checking1, checking2, savings1, savings2, ira1 };

        System.out.println("Displaying Account Information:\n");

        for (Account acc : accounts) {
            // Polymorphism: getInfo() calls the child’s overridden version.
            System.out.println(acc.getInfo());
        }

        // ----------------------------------------------------------
        // Demonstrate Access Specifiers
        // ----------------------------------------------------------
        System.out.println("Access Specifier Demonstration:\n");
        System.out.println("- Private fields (e.g., balance) cannot be accessed directly.");
        System.out.println("- Protected constructors prevent direct Account instantiation.");
        System.out.println("- Public getters and methods allow safe, controlled access.\n");

        // ----------------------------------------------------------
        // Demonstrate Composition (User has multiple Accounts)
        // ----------------------------------------------------------
        User demoUser = new User("Holly Hebert", "123 Main St", "555-1234");

        demoUser.addAccount(checking1);
        demoUser.addAccount(savings1);
        demoUser.addAccount(ira1);

        System.out.println("User & Composition Demonstration:");
        System.out.println("User Name: " + demoUser.getName());
        System.out.println("\nAccounts Belonging to This User:\n");

        for (Account acc : demoUser.getAccounts()) {
            System.out.println(acc.getInfo());
        }

        System.out.println("\nEnd of Week 3 demonstration.\n");
    }
}
