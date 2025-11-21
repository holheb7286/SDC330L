/*******************************************************************
* Name: Holly Hebert
* Date: November 20, 2025
* Assignment: SDC330 Week 2 - Banking Inheritance & Composition
* Class: SavingsAccount (Derived Class)
* Description:
* This class extends Account to represent a savings account.
* It inherits all basic account functionality and overrides the
* getInfo() method to specify the account type.
*******************************************************************/

class SavingsAccount extends Account {

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance); // Inheritance is demonstrated here
    }

@Override
    public String getInfo() {
        return "[Savings Account]\n" + super.getInfo();
    }
}