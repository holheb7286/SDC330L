/*******************************************************************
 * Name: Holly Hebert
 * Date: November 13, 2025
 * Assignment: SDC330 Week 1 - Banking Inheritance & Composition
 * Class: Account (Base Class)
 * Description:
 *   This class represents a general bank account. It contains basic
 *   information such as account number and balance. It provides
 *   simple methods for deposits, withdrawals, and retrieving account
 *   details.
 *******************************************************************/

public class Account {
    private String accountNumber;
    private double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

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

    public String getInfo() {
        return String.format("Account Number: %s\nBalance: $%.2f", accountNumber, balance);
    }
}