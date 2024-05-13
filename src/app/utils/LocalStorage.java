package app.utils;

import app.models.Account;
import app.models.Category;
import app.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class LocalStorage {
    private static User user = new User(12, "some");
    private static ObservableList<Category> categories = FXCollections.observableArrayList();
    private static ObservableList<Account> accounts = FXCollections.observableArrayList();

    public static ObservableList<Account> getAccounts() {
        return accounts;
    }

    public static void setAccounts(ObservableList<Account> accounts) {
        LocalStorage.accounts = accounts;
    }

    public static ObservableList<Category> getCategories() {
        return categories;
    }

    private LocalStorage(){}

    public static void setCategories(ObservableList<Category> categories) {
        LocalStorage.categories = categories;
    }

    public static void setUser(User _user) {
        user = _user;
    }

    public static User getUser() {
        return user;
    }


}
