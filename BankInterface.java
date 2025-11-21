/*******************************************************************
 * Name: Holly Hebert
 * Date: November 20, 2025
 * Assignment: SDC330 Week 2 – Bank Interface  
 * Interface: BankInterface  
 * Description:
 * This interface defines the contract for banking operations that
 * can be performed in the application. It will be implemented by
 * classes such as BankManager to ensure consistent behavior and
 * abstraction of core banking functionality.
 *******************************************************************/

public interface BankInterface {

    // Create a new account for a user
    void createAccount(User user);

    // Display all accounts for a specific user
    void displayAccounts(User user);

    // Update the user information (e.g., name, address, phone)
    void updateUserInfo(User user);

    // Process a transaction (deposit or withdrawal) for a user's account
    void processTransaction(User user);
}