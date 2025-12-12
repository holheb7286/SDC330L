Project Name: Java Banking Application
Project Description

The Java Banking Application is a console‑based financial management system designed to simulate 
core banking operations. The application provides full CRUD functionality for users and accounts, 
secure transaction handling, and persistent data storage through an integrated SQLite database.
Users can create customer profiles, open various account types, perform deposits and withdrawals, 
update personal details, and manage account lifecycles — all within an intuitive text‑based menu system.
This project demonstrates object‑oriented programming, database integration, error handling, and 
clean architectural separation between the user interface and backend logic.

Project Tasks
Task 1: Set Up the Development Environment
Install Java JDK
Install SQLite JDBC driver
Configure Visual Studio Code with Java extensions
Create project folder structure (src, lib, bin, etc.)

Task 2: Design the Application
Define data models (User, Account)
Create class architecture (BankManager, DatabaseHelper, BankInterface)
Plan the menu flow and user interactions
Outline CRUD operations needed

Task 3: Implement Database Support
Create SQLite database (banking.db)
Implement DatabaseHelper to initialize tables
Users table
Accounts table
Use PreparedStatements for safe DB operations

Task 4: Implement Core Application Logic
Add CRUD methods in BankManager:
Create/update/delete users
Create/delete accounts
Deposits and withdrawals (with overdraft protection)
Balance retrieval and account listing
Implement interactive wrapper methods for menu-driven input

Task 5: Build the User Interface
Create the console BankingApp class
Implement main loop, input parsing, and error handling
Add numbered menu navigation

Task 6: Test the Application
Test account creation, linking, and deletion
Test invalid input cases (text where numbers expected, negative transactions)
Validate overdraft prevention logic
Confirm database persistence across sessions

Task 7: Clean Up and Document
Remove unused Scanners and consolidate into one shared instance
Add method-level comments and Javadoc headers
Write a user-friendly README
Prepare 5‑minute demo recording
Project Skills Learned
Object-Oriented Programming (Java)
Interface-based design
Database management with SQLite
JDBC connections and prepared statements
Error handling and validation
Console-based UI design
Code modularization and separation of concerns
Version control with Git and GitHub
Technical documentation and demonstration

Language Used
Java (JDK 21): Core application logic and user interface
SQL (SQLite): Database storage and retrieval

Development Process Used
Iterative / Incremental Development
Built features in small, testable stages
Refactored architecture as requirements evolved
Maintained separation between UI and data layers
Debugging-Driven Development
Adjusted design based on Scanner conflicts, database errors, and schema mismatches

Notes
Ensure the SQLite JDBC driver is included in your project’s lib folder.
The database (banking.db) auto-creates required tables if not found.
Run the application from the project root using:

javac -cp ".;lib/sqlite-jdbc.jar" src/*.java
java -cp ".;lib/sqlite-jdbc.jar;src" BankingApp

Do not close the shared Scanner — the app relies on a single instance passed into BankManager.

For best results, run in a clean terminal to avoid buffer conflicts.
