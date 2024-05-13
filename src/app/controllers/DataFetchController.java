package app.controllers;
import app.models.Account;
import app.models.Category;
import app.models.User;
import app.utils.DB;
import app.utils.LocalStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataFetchController {
    static void loadCategories() {
        try {
            User user = LocalStorage.getUser();
            String query = String.format("SELECT * FROM Categories WHERE userID = %d", user.id);
            ResultSet rs = DB.executeQuery(query, false);
            ObservableList<Category> categories = FXCollections.observableArrayList();
            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                categories.add(new Category(categoryId, categoryName, user.id));
            }
            LocalStorage.setCategories(categories);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static void loadAccounts() {
        try {
            User user = LocalStorage.getUser();
            String query = String.format("SELECT * FROM Accounts WHERE userID = %d", user.id);
            ResultSet rs = DB.executeQuery(query, false);
            ObservableList<Account> accounts = FXCollections.observableArrayList();
            while (rs.next()) {
                int accountId = rs.getInt("account_id");
                String accountName = rs.getString("account_name");
                String amount_saved = rs.getString("amount_saved");
                Integer amount = Integer.parseInt(amount_saved);
                accounts.add(new Account(accountId,accountName, amount, user.id));
            }
            LocalStorage.setAccounts(accounts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
