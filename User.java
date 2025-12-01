/*******************************************************************
 * Name: Holly Hebert
 * Date: November 30, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Class: User (Composition Demo)
 * 
 * Description:
 * This class represents a bank user and demonstrates the concept of
 * COMPOSITION: a User "has" multiple Accounts, stored in a list.
 *
 * Week 3 Concepts Demonstrated:
 *
 *  - Access Specifiers:
 *        All fields are private to enforce encapsulation.
 *        Public getters/setters provide controlled access.
 *
 *  - Constructors:
 *        Multiple constructors demonstrate constructor overloading:
 *        → Full constructor (name, address, phone)
 *        → Overloaded constructor using default placeholder values
 *
 *  - Abstraction (Indirect Participation):
 *        User stores Account objects, which are instances of
 *        abstract-type references. The actual Account subclass
 *        (CheckingAccount, SavingsAccount, IRAAccount) is resolved
 *        polymorphically at runtime.
 *******************************************************************/

import java.util.ArrayList;

public class User {

    // -------------------------------------------------------------
    // Private fields ensure proper encapsulation (Week 3 requirement)
    // -------------------------------------------------------------
    private String name;
    private String address;
    private String phone;
    private ArrayList<Account> accounts;

    // -------------------------------------------------------------
    // Constructor #1 – Full constructor
    // Used for realistic application instantiation.
    // -------------------------------------------------------------
    public User(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.accounts = new ArrayList<>();
    }

    // -------------------------------------------------------------
    // Constructor #2 – Overloaded constructor
    // Demonstrates constructor overloading for Week 3.
    // Provides defaults if only the name is known.
    // -------------------------------------------------------------
    public User(String name) {
        this(name, "Unknown Address", "N/A");
    }

    // -------------------------------------------------------------
    // Composition: A User "has" accounts
    // -------------------------------------------------------------
    public void addAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    // -------------------------------------------------------------
    // Getters and Setters – Public for controlled external access
    // -------------------------------------------------------------
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

    // -------------------------------------------------------------
    // Utility: Search the user's account list by account number.
    // This supports BankManager operations.
    // -------------------------------------------------------------
    public Account findAccount(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null; // Not found
    }
}