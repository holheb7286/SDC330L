/*******************************************************************
* Name: Holly Hebert
* Date: November 13, 2025
* Assignment: SDC330 Week 1 - Banking Inheritance & Composition
* Class: CheckingAccount (Derived Class)
* Description:
* This class extends Account to represent a checking account.
* It inherits all basic account functionality and overrides the
* getInfo() method to specify the account type.
*******************************************************************/

public class CheckingAccount extends Account {

    public CheckingAccount(String accountNumber, double balance) {
        super(accountNumber, balance); // Inheritance is demonstrated here
    }

@Override
    public String getInfo() {
        return "[Checking Account]\n" + super.getInfo();
    }
}