/*******************************************************************
 * Name: Holly Hebert
 * Date: December 4, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Class: IRAAccount (Derived Class)
 * 
 * Description:
 * This class represents an IRA (Individual Retirement Account) and
 * extends the abstract Account class. It demonstrates:
 *
 *  - Abstraction:
 *       IRAAccount provides a concrete implementation of the abstract
 *       getInfo() method inherited from Account.
 *
 *  - Constructors:
 *       Two constructors are used to demonstrate constructor
 *       overloading: one full constructor, and one that defaults the
 *       balance to $0.00 while requiring only account number and 
 *       yearly contribution.
 *
 *  - Access Specifiers:
 *       - private fields (encapsulation)
 *       - protected superclass constructors (restricting direct use)
 *       - public constructors (allowing instantiation by other classes)
 *******************************************************************/

public class IRAAccount extends Account {

    // Private fields to ensure proper encapsulation
    private double yearlyContribution;
    private static final double MAX_CONTRIBUTION = 6500.00;

    // --------------------------------------------------------------------
    // Constructor #1 – Full constructor (accountNumber, balance, contrib)
    // Demonstrates calling the protected superclass constructor.
    // --------------------------------------------------------------------
    public IRAAccount(String accountNumber, double balance, double yearlyContribution) {
        super(accountNumber, balance);
        this.yearlyContribution = yearlyContribution;
    }

    // --------------------------------------------------------------------
    // Constructor #2 – Overloaded constructor
    // Defaults the balance to $0.00 while setting yearly contribution.
    // --------------------------------------------------------------------
    public IRAAccount(String accountNumber, double yearlyContribution) {
        super(accountNumber); // Calls protected Account(String) constructor
        this.yearlyContribution = yearlyContribution;
    }

    // Getters and setters
    public double getYearlyContribution() {
        return yearlyContribution;
    }

    public void setYearlyContribution(double yearlyContribution) {
        this.yearlyContribution = yearlyContribution;
    }

    // --------------------------------------------------------------------
    // applyContribution:
    // Demonstrates safe business logic with access-controlled fields.
    // --------------------------------------------------------------------
    public void applyContribution() {
        if (yearlyContribution <= MAX_CONTRIBUTION) {
            deposit(yearlyContribution);
        } else {
            System.out.println("Error: Contribution exceeds maximum allowed for IRA accounts.");
        }
    }

    // --------------------------------------------------------------------
    // Implementation of abstract method from Account.
    // Demonstrates polymorphism: different account types output their
    // details in unique ways.
    // --------------------------------------------------------------------
    @Override
    public String getInfo() {
        return "[IRA Account]\n" +
               "Account Number: " + getAccountNumber() + "\n" +
               "Balance: $" + String.format("%.2f", getBalance()) + "\n" +
               "Yearly Contribution: $" + String.format("%.2f", yearlyContribution) + "\n";
    }
}