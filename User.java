/*******************************************************************
 * Name: Holly Hebert
 * Date: November 13, 2025
 * Assignment: SDC330 Week 1 - Banking Inheritance & Composition
 * Class: User (Composition Demo)
 * Description:
 *   This class demonstrates composition. A User "has" accounts,
 *   which are stored in a list. It contains user details and provides
 *   methods to add accounts and display the user's full profile.
 *******************************************************************/

import java.util.ArrayList;

class User {
    private String name;
    private String email;
    private ArrayList<Account> accounts; // Composition demonstrated here

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public String getUserProfile() {
        StringBuilder profile = new StringBuilder();
        profile.append(String.format("User Name: %s\nEmail: %s\n\nAccounts:\n", name, email));

        for (Account acc : accounts) {
            profile.append(acc.getInfo()).append("\n\n");
        }

        return profile.toString();
    }
}