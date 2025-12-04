/*******************************************************************
 * Name: Holly Hebert
 * Date: December 2, 2025
 * Assignment: SDC330 Week 4 – Database Interactions (SQLite)
 * Class: DatabaseHelper
 *
 * Purpose:
 *  - Provides a simple helper for establishing a JDBC connection
 *    to an SQLite database file (banking.db).
 *  - Ensures required tables (users, accounts) are created if
 *    they do not already exist.
 *
 * Notes:
 *  - Requires sqlite-jdbc on the classpath.
 *  - Demonstrates encapsulation (private helper methods) and
 *    safe resource handling (try-with-resources).
 *******************************************************************/

import java.sql.*;

public class DatabaseHelper {

    // Database filename
    private static final String DB_URL = "jdbc:sqlite:banking.db";

    // Constructor - no-op but shows constructor usage for Week 3/4
    public DatabaseHelper() {
        // Could accept config in future
    }

    // Get a connection to the database
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Initialize DB schema (create tables if missing)
    public void initializeDatabase() {
        String createUsers = ""
            + "CREATE TABLE IF NOT EXISTS users ("
            + "  user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "  name TEXT NOT NULL,"
            + "  address TEXT,"
            + "  phone TEXT"
            + ");";

        String createAccounts = ""
            + "CREATE TABLE IF NOT EXISTS accounts ("
            + "  account_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "  account_number TEXT NOT NULL UNIQUE,"
            + "  balance REAL NOT NULL,"
            + "  type TEXT NOT NULL,"
            + "  user_id INTEGER,"
            + "  FOREIGN KEY(user_id) REFERENCES users(user_id)"
            + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createUsers);
            stmt.execute(createAccounts);

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}