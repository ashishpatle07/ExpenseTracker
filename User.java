import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private final String username;
    private final String password;
    private final List<Expense> expenses;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.expenses = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        System.out.println("\n=== Expense List ===");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    public void displayCategorySummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }
        Map<String, Double> categorySummary = new HashMap<>();
        for (Expense expense : expenses) {
            categorySummary.put(expense.getCategory(),
                    categorySummary.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }
        System.out.println("\n=== Category-wise Summary ===");
        for (Map.Entry<String, Double> entry : categorySummary.entrySet()) {
            System.out.printf("%s: %.2f%n", entry.getKey(), entry.getValue());
        }
    }
}
