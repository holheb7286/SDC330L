/*******************************************************************
 * Name: Holly Hebert
 * Date: November 30, 2025
 * Assignment: SDC330 Week 3 – Abstraction, Constructors, Access Specifiers
 * Interface: BankInterface
 * 
 * Description:
 * This interface defines the contract for high-level banking
 * operations in the application. It demonstrates the concept of
 * abstraction: any class that implements this interface must provide
 * concrete behavior for the defined methods.
 *
 * Although Week 3 focuses on abstract classes, constructors, and
 * access specifiers, this interface remains essential because:
 * 
 * - It enforces consistent behavior across manager classes.
 * - It separates *what* operations must be performed from *how* they
 *   are implemented (abstraction).
 * - It prepares the application for Week 4, where database-backed
 *   functionality will be added to BankManager.
 *
 * No changes to the interface are necessary for Week 3, since
 * interfaces naturally demonstrate abstraction on their own.
 *******************************************************************/

public interface BankInterface {

    // Create a new account for a user
    void createAccount(User user);

    // Display all accounts for a specific user
    void displayAccounts(User user);

    // Update user information (name, address, phone)
    void updateUserInfo(User user);

    // Process a deposit or withdrawal for an account
    void processTransaction(User user);
}