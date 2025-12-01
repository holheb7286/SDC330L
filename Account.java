/*******************************************************************
 * Name: Holly Hebert
 * Date: November 30, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Class: Account (Abstract Base Class)
 * 
 * Description:
 * This abstract class serves as the foundation for all bank account
 * types. It demonstrates:
 * 
 * - Abstraction: The class cannot be instantiated directly and
 *   requires child classes to provide their own implementation of
 *   the abstract method getInfo().
 *
 * - Constructors: Multiple constructors are provided to demonstrate
 *   constructor overloading and flexible object creation.
 *
 * - Access Specifiers:
 *       private    -> fields that should never be directly accessed
 *       protected  -> constructors used only by derived classes
 *       public     -> methods needed by outside classes
 *
 * This structure ensures proper encapsulation, code reuse, and
 * polymorphic behavior across account types.
 *******************************************************************/

public abstract class Account {

    // ------------------------------
    // PRIVATE FIELDS 
    // Cannot be accessed directly outside this class.
    // Demonstrates encapsulation & proper access control.
    // ------------------------------
    private String accountNumber;
    private double balance;

    // ----------------------------------------------------------
    // PROTECTED CONSTRUCTOR (Primary)
    // Only child classes (Checking, Savings, IRA) may call this.
    // Demonstrates abstraction: base class cannot be instantiated.
    // ----------------------------------------------------------
    protected Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // ----------------------------------------------------------
    // OVERLOADED CONSTRUCTOR
    // Sets the initial balance to $0.00 if only the account number
    // is provided. Demonstrates constructor overloading.
    // ----------------------------------------------------------
    protected Account(String accountNumber) {
        this(accountNumber, 0.0);
    }

    // ------------------------------
    // PUBLIC GETTERS
    // Provide controlled access to private fields.
    // ------------------------------
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // ----------------------------------------------------------
    // PUBLIC METHODS: deposit & withdraw
    // These update the account's balance safely and are used by
    // all derived classes and the BankManager.
    // ----------------------------------------------------------
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    // ----------------------------------------------------------
    // ABSTRACT METHOD
    // Forces each derived class to provide its own version of
    // account details. Demonstrates abstraction + polymorphism.
    // ----------------------------------------------------------
    public abstract String getInfo();
}