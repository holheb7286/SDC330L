/*******************************************************************
* Name: Holly Hebert
* Date: November 20, 2025
* Assignment: SDC330 Week 2 - Banking Inheritance & Composition
* Class: IRAAccount (Derived Class)
* Description:
* This class represents an IRA (Individual Retirement Account),
* inheriting from the abstract Account class. It overrides the
* getInfo method to provide customized output and includes a method
* to apply a yearly contribution with a cap check.
*******************************************************************/

public class IRAAccount extends Account {

    private double yearlyContribution;
    private static final double MAX_CONTRIBUTION = 6500.00;

    public IRAAccount(String accountNumber, double balance, double yearlyContribution) {
        super(accountNumber, balance);
        this.yearlyContribution = yearlyContribution;
    }

    public double getYearlyContribution() {
        return yearlyContribution;
    }

    public void setYearlyContribution(double yearlyContribution) {
        this.yearlyContribution = yearlyContribution;
    }

    public void applyContribution() {
        if (yearlyContribution <= MAX_CONTRIBUTION) {
            deposit(yearlyContribution);
        } else {
            System.out.println("Error: Contribution exceeds maximum allowed for IRA accounts.");
        }
    }

    @Override
    public String getInfo() {
        // Demonstrates POLYMORPHISM: each account overrides getInfo() differently
        return "IRA Account\n" +
               "Account Number: " + getAccountNumber() + "\n" +
               "Balance: $" + getBalance() + "\n" +
               "Yearly Contribution: $" + yearlyContribution + "\n";
    }
}
