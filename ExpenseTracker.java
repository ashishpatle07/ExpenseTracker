import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String DATA_FILE = "expenses.txt";

    private static Map<String, User> users = new HashMap<>();
    private static User currentUser;

    public static void main(String[] args) {
        loadUsers();
        while (true) {
            System.out.println("\n=== Expense Tracker ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    saveUsers();
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Try logging in.");
            return;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.put(username, new User(username, password));
        System.out.println("Registration successful!");
    }

    private static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            System.out.println("Login successful!");
            userDashboard();
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void userDashboard() {
        while (true) {
            System.out.println("\n=== User Dashboard ===");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Category-wise Summary");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    currentUser.viewExpenses();
                    break;
                case 3:
                    currentUser.displayCategorySummary();
                    break;
                case 4:
                    System.out.println("Logged out successfully!");
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addExpense() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        currentUser.addExpense(new Expense(date, category, amount));
        System.out.println("Expense added successfully!");
    }

    private static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            users = (Map<String, User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }
}
