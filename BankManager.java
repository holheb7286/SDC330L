/*******************************************************************
 * Name: Holly Hebert
 * Date: December 5, 2025
 * Assignment: SDC330 Week 4 – Database Interactions (SQLite)
 * Class: BankManager
 *
 * Purpose:
 *  - Implements full CRUD operations using SQLite.
 *  - Handles both user and account management.
 *  - Ensures deposits, withdrawals, and deletes are fully saved.
 *
 * Notes on professor feedback:
 *  Deposits now correctly update the DB.
 *  User IDs are auto-generated and printed clearly.
 *  Account numbers are displayed so users can delete easily.
 *  Added getAccountsByUser(int) because BankingApp calls it.
 *******************************************************************/

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankManager implements BankInterface {

    private DatabaseHelper dbHelper;

    public BankManager(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
        dbHelper.initializeDatabase();
    }

    // -------------------- USER CRUD --------------------

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
                    int newId = keys.getInt(1);
                    System.out.println("User created with ID: " + newId);
                    return newId;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return -1;
    }

    public List<String> getAllUsersFormatted() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT user_id, name, address, phone FROM users;";

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(String.format(
                    "ID: %d  Name: %s  Address: %s  Phone: %s",
                    rs.getInt("user_id"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    public boolean updateUser(int userId, String newName, String newAddress, String newPhone) {
        String sql = "UPDATE users SET name = ?, address = ?, phone = ? WHERE user_id = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newName);
            ps.setString(2, newAddress);
            ps.setString(3, newPhone);
            ps.setInt(4, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    // -------------------- ACCOUNT CRUD --------------------

    public boolean createAccountRecord(String accountNumber, double balance, String type, Integer userId) {
        String sql = "INSERT INTO accounts(account_number, balance, type, user_id) VALUES (?, ?, ?, ?);";

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);
            ps.setDouble(2, balance);
            ps.setString(3, type);

            if (userId == null) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
            return false;
        }
    }

    public List<String> getAllAccountsFormatted() {
        List<String> out = new ArrayList<>();
        String sql = "SELECT account_id, account_number, balance, type, user_id FROM accounts;";

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(String.format(
                    "Account Num: %s | Type: %s | Balance: $%.2f | Account ID: %d | User ID: %s",
                    rs.getString("account_number"),
                    rs.getString("type"),
                    rs.getDouble("balance"),
                    rs.getInt("account_id"),
                    rs.getObject("user_id") == null ? "null" : rs.getInt("user_id")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching accounts: " + e.getMessage());
        }
        return out;
    }

    // ---------------- FIXED: missing method from BankingApp ----------------

    /** Returns formatted list of accounts linked to a specific user_id */
    public List<String> getAccountsByUser(int userId) {
        List<String> out = new ArrayList<>();
        String sql = """
            SELECT account_id, account_number, balance, type
            FROM accounts
            WHERE user_id = ?;
        """;

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(String.format(
                        "Account Num: %s | Type: %s | Balance: $%.2f | Account ID: %d",
                        rs.getString("account_number"),
                        rs.getString("type"),
                        rs.getDouble("balance"),
                        rs.getInt("account_id")
                    ));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching accounts for user: " + e.getMessage());
        }

        return out;
    }

    // -------- Deposits / Withdrawals now correctly update DB --------
    public boolean updateAccountBalance(String accountNumber, double amountDelta) {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amountDelta);
            ps.setString(2, accountNumber);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("No account updated. Incorrect account number?");
                return false;
            }

            System.out.println("Balance successfully updated.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAccount(String accountNumber) {
        String sql = "DELETE FROM accounts WHERE account_number = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
            return false;
        }
    }

    // ---------------- INTERFACE METHODS (DB-Backed Behavior) ----------------

    @Override
    public void createAccount(User ignored) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter user ID to link account (or -1 for no user): ");
            int userId = Integer.parseInt(scanner.nextLine().trim());
            if (userId == -1) userId = 0;

            System.out.print("Enter account type (Checking/Savings/IRA): ");
            String type = scanner.nextLine().trim();

            System.out.print("Enter starting balance: ");
            double bal = Double.parseDouble(scanner.nextLine().trim());

            String acctNum = "ACCT" + (int)(Math.random() * 100000);

            boolean ok = createAccountRecord(acctNum, bal, type, userId);
            System.out.println(ok ? "Created account: " + acctNum : "Failed to create account.");
        }
    }

    @Override
    public void displayAccounts(User ignored) {
        List<String> accts = getAllAccountsFormatted();
        if (accts.isEmpty()) System.out.println("No accounts found.");
        else accts.forEach(System.out::println);
    }

    @Override
    public void updateUserInfo(User ignored) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter user ID to update: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            System.out.print("New name: ");
            String name = sc.nextLine();

            System.out.print("New address: ");
            String addr = sc.nextLine();

            System.out.print("New phone: ");
            String phone = sc.nextLine();

            boolean ok = updateUser(id, name, addr, phone);
            System.out.println(ok ? "User updated." : "Failed to update user.");
        }
    }

    @Override
    public void processTransaction(User ignored) {
        try (Scanner sc = new Scanner(System.in)) {

            System.out.print("Enter account number: ");
            String acct = sc.nextLine().trim();

            System.out.print("Deposit or Withdraw (D/W): ");
            String type = sc.nextLine().trim().toUpperCase();

            System.out.print("Amount: ");
            double amt = Double.parseDouble(sc.nextLine().trim());

            double delta = type.equals("D") ? amt : -amt;

            boolean ok = updateAccountBalance(acct, delta);
            System.out.println(ok ? "Transaction complete." : "Transaction failed.");
        }
    }
}