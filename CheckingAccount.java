/*******************************************************************
 * Name: Holly Hebert
 * Date: November 30, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Class: CheckingAccount (Derived Class)
 * 
 * Description:
 * This class represents a checking account and extends the abstract
 * Account class. It demonstrates:
 *
 *  - Abstraction:
 *       Account is an abstract base class that defines the common
 *       structure and behavior of all account types. CheckingAccount
 *       provides a concrete implementation of the abstract getInfo()
 *       method.
 *
 *  - Constructors:
 *       Multiple constructors are used to demonstrate constructor
 *       overloading. One constructor uses both account number and 
 *       balance; another defaults the initial balance to $0.00.
 *
 *  - Access Specifiers:
 *       The Account class uses private fields and protected
 *       constructors. This class uses public constructors so it can
 *       be instantiated by other parts of the application.
 *******************************************************************/

public class CheckingAccount extends Account {

    // --------------------------------------------------------------
    // Constructor #1 – Full constructor (account number + balance)
    // Demonstrates calling the protected superclass constructor.
    // --------------------------------------------------------------
    public CheckingAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    // --------------------------------------------------------------
    // Constructor #2 – Overloaded constructor (account number only)
    // Initializes a checking account with a default balance of $0.00.
    // Demonstrates constructor overloading for Week 3.
    // --------------------------------------------------------------
    public CheckingAccount(String accountNumber) {
        super(accountNumber);   // Calls overloaded protected constructor in Account
    }

    // --------------------------------------------------------------
    // Implementation of abstract method from Account.
    // Demonstrates polymorphism: Account references will call this
    // version when referring to a CheckingAccount instance.
    // --------------------------------------------------------------
    @Override
    public String getInfo() {
        return "[Checking Account]\n" + super.getAccountNumber() +
               "\nBalance: $" + String.format("%.2f", super.getBalance()) + "\n";
    }
}