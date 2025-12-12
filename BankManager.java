/*******************************************************************
 * Name: Holly Hebert
 * Date: December 9, 2025
 * Assignment: SDC330 Week 4 – Database Interactions (SQLite)
 * Class: BankManager (DB-enabled, Shared Scanner)
 *
 * Notes:
 *  - ONE shared Scanner passed from BankingApp.
 *  - All Scanner input uses that shared Scanner.
 *  - Interactive wrapper methods replace old Scanner-creating methods.
 *******************************************************************/

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankManager implements BankInterface {

    private final DatabaseHelper dbHelper;
    private final Scanner scanner;

    public BankManager(DatabaseHelper dbHelper, Scanner scanner) {
        this.dbHelper = dbHelper;
        this.scanner = scanner;
        dbHelper.initializeDatabase();
    }

    // -------------------- USER CRUD --------------------

    public int createUser(String name, String address, String phone) {
        final String sql = "INSERT INTO users(name, address, phone) VALUES(?, ?, ?);";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phone);
            if (ps.executeUpdate() == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return -1;
    }

    public List<String> getAllUsersFormatted() {
        final String sql = "SELECT user_id, name, address, phone FROM users ORDER BY user_id;";
        List<String> rows = new ArrayList<>();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rows.add(String.format(
                        "UserID: %d | Name: %s | Address: %s | Phone: %s",
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return rows;
    }

    public boolean updateUser(int userId, String newName, String newAddress, String newPhone) {
        final String sql = "UPDATE users SET name=?, address=?, phone=? WHERE user_id=?;";
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
        final String sql = "DELETE FROM users WHERE user_id=?;";
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

    public boolean createAccountRecord(String acct, double balance, String type, Integer userId) {
        final String sql = "INSERT INTO accounts(account_number, balance, type, user_id) VALUES(?,?,?,?);";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acct);
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
        final String sql = "SELECT account_id, account_number, balance, type, user_id FROM accounts ORDER BY account_id;";
        List<String> rows = new ArrayList<>();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rows.add(String.format(
                        "AccountID: %d | Num: %s | Type: %s | Balance: $%.2f | UserID: %s",
                        rs.getInt("account_id"),
                        rs.getString("account_number"),
                        rs.getString("type"),
                        rs.getDouble("balance"),
                        rs.getObject("user_id") == null ? "null" : rs.getInt("user_id")));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching accounts: " + e.getMessage());
        }
        return rows;
    }

    public List<String> getAccountsByUser(int userId) {
        final String sql = "SELECT account_id, account_number, balance, type FROM accounts WHERE user_id=? ORDER BY account_id;";
        List<String> rows = new ArrayList<>();
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(String.format(
                            "AccountID: %d | Num: %s | Type: %s | Balance: $%.2f",
                            rs.getInt("account_id"),
                            rs.getString("account_number"),
                            rs.getString("type"),
                            rs.getDouble("balance")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching accounts: " + e.getMessage());
        }
        return rows;
    }

    public Double getBalance(String acct) {
        final String sql = "SELECT balance FROM accounts WHERE account_number=?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, acct);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.err.println("Error reading balance: " + e.getMessage());
        }
        return null;
    }

    public boolean deposit(String acct, double amount) {
        if (amount <= 0) return false;
        final String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, acct);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error depositing: " + e.getMessage());
            return false;
        }
    }

    public boolean withdraw(String acct, double amount) {
        if (amount <= 0) return false;
        final String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number=? AND balance >= ?;";
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setString(2, acct);
            ps.setDouble(3, amount);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error withdrawing: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAccount(String acct) {
        try (Connection conn = dbHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM accounts WHERE account_number = ?;")) {
            ps.setString(1, acct);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
            return false;
        }
    }

    // -------------------- INTERACTIVE METHODS --------------------

    public void createUserInteractive() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String addr = scanner.nextLine();
        System.out.print("Phone: ");
        String ph = scanner.nextLine();
        int id = createUser(name, addr, ph);
        System.out.println(id > 0 ? "Created user with ID: " + id : "Failed to create user.");
    }

    public void createAccountInteractive() {
        System.out.print("User ID (Enter for none): ");
        String uidStr = scanner.nextLine().trim();
        Integer uid = uidStr.isEmpty() ? null : Integer.parseInt(uidStr);
        System.out.print("Account type: ");
        String type = scanner.nextLine();
        System.out.print("Starting balance: ");
        double bal = Double.parseDouble(scanner.nextLine());
        String acct = "ACCT" + (int)(Math.random() * 100000);
        boolean ok = createAccountRecord(acct, bal, type, uid);
        System.out.println(ok ? "Created account: " + acct : "Failed to create account.");
    }

    public void displayAllUsers() {
        List<String> list = getAllUsersFormatted();
        if (list.isEmpty()) System.out.println("No users.");
        else list.forEach(System.out::println);
    }

    public void displayAllAccounts() {
        List<String> list = getAllAccountsFormatted();
        if (list.isEmpty()) System.out.println("No accounts.");
        else list.forEach(System.out::println);
    }

    public void displayAccountsByUser() {
        System.out.print("User ID: ");
        int uid = Integer.parseInt(scanner.nextLine());
        List<String> list = getAccountsByUser(uid);
        if (list.isEmpty()) System.out.println("No accounts for that user.");
        else list.forEach(System.out::println);
    }

    public void depositInteractive() {
        System.out.print("Account number: ");
        String acct = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(scanner.nextLine());
        System.out.println(deposit(acct, amt) ? "Deposit applied." : "Deposit failed.");
    }

    public void withdrawInteractive() {
        System.out.print("Account number: ");
        String acct = scanner.nextLine();
        System.out.print("Amount: ");
        double amt = Double.parseDouble(scanner.nextLine());
        System.out.println(withdraw(acct, amt) ? "Withdrawal applied." : "Withdrawal failed.");
    }

    public void updateUserInteractive() {
        System.out.print("User ID: ");
        int uid = Integer.parseInt(scanner.nextLine());
        System.out.print("New name: ");
        String n = scanner.nextLine();
        System.out.print("New address: ");
        String a = scanner.nextLine();
        System.out.print("New phone: ");
        String p = scanner.nextLine();
        System.out.println(updateUser(uid, n, a, p) ? "User updated." : "Failed.");
    }

    public void deleteAccountInteractive() {
        System.out.print("Account number: ");
        String acct = scanner.nextLine();
        System.out.println(deleteAccount(acct) ? "Deleted." : "Delete failed.");
    }

    public void deleteUserInteractive() {
        System.out.print("User ID: ");
        int uid = Integer.parseInt(scanner.nextLine());
        System.out.println(deleteUser(uid) ? "User deleted." : "Failed.");
    }

    // ---------------- INTERFACE OVERRIDES ----------------

    @Override
    public void createAccount(User user) {
        createAccountInteractive();
    }

    @Override
    public void updateUserInfo(User user) {
        updateUserInteractive();
    }

    @Override
    public void processTransaction(User user) {
        System.out.println("processTransaction() not used in this version.");
    }

    @Override
    public void displayAccounts(User user) {
        displayAllAccounts();
    }
}