/*******************************************************************
 * Name: Holly Hebert
 * Date: December 1, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Class: SavingsAccount (Derived Class)
 * 
 * Description:
 * This class represents a savings account and extends the abstract
 * Account class. It demonstrates:
 *
 *  - Abstraction:
 *       SavingsAccount provides a concrete implementation of the
 *       abstract getInfo() method inherited from Account.
 *
 *  - Constructors:
 *       Two constructors are included:
 *         → One that accepts an account number and starting balance
 *         → One overloaded version that defaults the balance to $0.00
 *
 *  - Access Specifiers:
 *       - Public constructors (instantiated externally)
 *       - Private fields remain encapsulated in the Account class
 *       - Account superclass constructors are protected, preventing
 *         direct instantiation of Account while still allowing child
 *         classes to call them.
 *******************************************************************/

public class SavingsAccount extends Account {

    // --------------------------------------------------------------
    // Constructor #1 – Full constructor
    // Calls the protected Account(String, double) constructor.
    // --------------------------------------------------------------
    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    // --------------------------------------------------------------
    // Constructor #2 – Overloaded constructor
    // Initializes a savings account with a default balance of $0.00.
    // Demonstrates constructor overloading for Week 3.
    // --------------------------------------------------------------
    public SavingsAccount(String accountNumber) {
        super(accountNumber);  // Calls the overloaded protected Account(String) constructor
    }

    // --------------------------------------------------------------
    // Implementation of the abstract method from Account.
    // Demonstrates polymorphism when accessed via an Account reference.
    // --------------------------------------------------------------
    @Override
    public String getInfo() {
        return "[Savings Account]\n" +
               "Account Number: " + getAccountNumber() + "\n" +
               "Balance: $" + String.format("%.2f", getBalance()) + "\n";
    }
}
