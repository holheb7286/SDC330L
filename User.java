/*******************************************************************
 * Name: Holly Hebert
 * Date: November 20, 2025
 * Assignment: SDC330 Week 1 - Banking Inheritance & Composition
 * Class: User (Composition Demo)
 * Description:
 * This class demonstrates composition. A User "has" accounts,
 * which are stored in a list. It contains user details and provides
 * methods to add accounts and display the user's full profile.
 *******************************************************************/

import java.util.ArrayList;

public class User {

    private String name;
    private String address;
    private String phone;
    private ArrayList<Account> accounts;

    // Constructor used in BankingApp (Week 2 design)
    public User(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.accounts = new ArrayList<>();
    }

    // --- Composition Methods ---

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    // --- User Info Getters/Setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // --- Utility Method for BankManager ---
    public Account findAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null; // Not found
    }
}