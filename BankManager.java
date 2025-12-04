/*******************************************************************
 * Name: Holly Hebert
 * Date: December 2, 2025
 * Assignment: SDC330 Week 4 – Database Interactions (SQLite)
 * Class: BankManagerDB (Database-enabled Bank Manager)
 *
 * Purpose:
 *  - Implements core CRUD operations using SQLite via JDBC.
 *  - Implements BankInterface so it can be swapped with the
 *    non-DB BankManager if desired.
 *
 * Week 4 notes:
 *  - Demonstrates use of constructors (accepts DatabaseHelper).
 *  - Demonstrates access specifiers: DB helper is private, methods
 *    are public as required by the interface.
 *******************************************************************/

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankManager implements BankInterface {

    // Private DatabaseHelper instance encapsulated inside this manager
    private DatabaseHelper dbHelper;

    // Constructor: requires DatabaseHelper (dependency injection pattern)
    public BankManager(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
        // Ensure database schema exists
        dbHelper.initializeDatabase();
    }

    // -------------------- USER CRUD --------------------

    // Create a new user and return the generated user_id (or -1 on error)
    public int createUser(String name, String address, String phone) {
        String sql = "INSERT INTO users(name, address, phone) VALUES(?, ?, ?);";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);
            int affected = ps.executeUpdate();

            if (affected == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return -1;
    }

    // Read: get list of all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, name, address, phone FROM users;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String addr = rs.getString("address");
                String phone = rs.getString("phone");
                User u = new User(name, addr, phone);
                // set user id? our User class doesn't have field for id;
                // if you want to store DB id, extend User class. For now, we
                // keep realistic storage of accounts in DB and User is a lightweight object.
                users.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    // Update user info - by user_id
    public boolean updateUser(int userId, String newName, String newAddress, String newPhone) {
        String sql = "UPDATE users SET name = ?, address = ?, phone = ? WHERE user_id = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newName);
            ps.setString(2, newAddress);
            ps.setString(3, newPhone);
            ps.setInt(4, userId);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // Delete user by id (also deletes accounts via foreign key logic if configured)
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // -------------------- ACCOUNT CRUD --------------------

    // Create account and associate to a user_id (pass type like "Checking", "Savings", "IRA")
    public boolean createAccountRecord(String accountNumber, double balance, String type, Integer userId) {
        String sql = "INSERT INTO accounts(account_number, balance, type, user_id) VALUES(?,?,?,?);";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);
            ps.setDouble(2, balance);
            ps.setString(3, type);
            if (userId == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, userId);
            }
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }

    // Read: get all accounts
    public List<String> getAllAccountsRaw() {
        List<String> out = new ArrayList<>();
        String sql = "SELECT account_id, account_number, balance, type, user_id FROM accounts;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(String.format("ID:%d Num:%s Type:%s Balance:%.2f UserID:%s",
                        rs.getInt("account_id"),
                        rs.getString("account_number"),
                        rs.getString("type"),
                        rs.getDouble("balance"),
                        rs.getObject("user_id") == null ? "null" : String.valueOf(rs.getInt("user_id")) ));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching accounts: " + e.getMessage());
        }
        return out;
    }

    // Read: get accounts for a specific user_id
    public List<String> getAccountsByUser(int userId) {
        List<String> out = new ArrayList<>();
        String sql = "SELECT account_id, account_number, balance, type FROM accounts WHERE user_id = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(String.format("ID:%d Num:%s Type:%s Balance:%.2f",
                            rs.getInt("account_id"),
                            rs.getString("account_number"),
                            rs.getString("type"),
                            rs.getDouble("balance")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user accounts: " + e.getMessage());
        }
        return out;
    }

    // Update: deposit or withdraw (adjust balance)
    public boolean updateAccountBalance(String accountNumber, double amountDelta) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amountDelta);
            ps.setString(2, accountNumber);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
            return false;
        }
    }

    // Delete: delete account by account number
    public boolean deleteAccount(String accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountNumber);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
            return false;
        }
    }

    // -------------------- BankInterface methods (stubs that call DB operations) --------------------
    // These implement the BankInterface and adapt interactive behavior to DB-backed actions.
    // For Week 4 the interactive prompts are included here for convenience.
    @Override
    public void createAccount(User user) {
        // For backward compatibility with interface - create a DB account and optionally link to user
        // We'll prompt for minimal input here
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            System.out.print("Enter account type (Checking/Savings/IRA): ");
            String type = scanner.nextLine().trim();
            System.out.print("Enter starting balance: ");
            double balance = Double.parseDouble(scanner.nextLine().trim());
            String acctNum = "ACCT" + (int)(Math.random() * 100000);

            // Note: linking to DB user requires knowing user_id. In Week 4 we provide a menu
            // option that asks for numeric user_id; here we simply insert the account without link.
            boolean ok = createAccountRecord(acctNum, balance, type, null);
            System.out.println(ok ? "Account created with number: " + acctNum : "Failed to create account.");
        }
    }

    @Override
    public void displayAccounts(User user) {
        // For the DB manager we will just list all accounts (user param ignored)
        List<String> rows = getAllAccountsRaw();
        if (rows.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        System.out.println("--- All Accounts ---");
        for (String r : rows) System.out.println(r);
    }

    @Override
    public void updateUserInfo(User user) {
        // Since our Users are stored in DB, prompt for user_id and update
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            System.out.print("Enter user ID to update: ");
            int uid = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("New name: ");
            String n = scanner.nextLine();
            System.out.print("New address: ");
            String a = scanner.nextLine();
            System.out.print("New phone: ");
            String p = scanner.nextLine();
            boolean ok = updateUser(uid, n, a, p);
            System.out.println(ok ? "User updated." : "Failed to update user (check ID).");
        }
    }

    @Override
    public void processTransaction(User user) {
        // For DB manager allow deposit/withdraw by account number
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            System.out.print("Enter account number: ");
            String acct = scanner.nextLine().trim();
            System.out.print("Deposit or Withdraw (D/W): ");
            String t = scanner.nextLine().trim().toUpperCase();
            System.out.print("Amount: ");
            double amt = Double.parseDouble(scanner.nextLine().trim());
            double delta = (t.equals("D")) ? amt : -amt;
            boolean ok = updateAccountBalance(acct, delta);
            System.out.println(ok ? "Transaction successful." : "Transaction failed (check account).");
        }
    }
}