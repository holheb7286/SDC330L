/*******************************************************************
 * Name: Holly Hebert
 * Date: December 4, 2025
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
 * Minor changes in interface for Week 5.
 *******************************************************************/

public interface BankInterface {
    void createAccount(User user);
    void updateUserInfo(User user);
    void processTransaction(User user);
    void displayAccounts(User user);
}