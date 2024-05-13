package app.controllers;

import app.models.Category;
import app.models.User;
import app.utils.DB;
import app.utils.LocalStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Integer> categoryIdColumn;

    @FXML
    private TableColumn<Category, String> categoryNameColumn;

    @FXML
    private TextField categoryNameTextField;

    @FXML
    private Label categoryError;
    @FXML
    private ComboBox budgetComboBox;

    private ObservableList<Category> categories;

    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = LocalStorage.getUser();
        categoryIdColumn.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        categories = LocalStorage.getCategories();
        categoryTable.setItems(categories);
    }


    @FXML
    private void addCategory(ActionEvent event) {
        String categoryName = categoryNameTextField.getText().trim();
        if (categoryName.isEmpty()) {
            categoryError.setText("Category name cannot be empty.");
            return;
        }

        try {
            String query = String.format("INSERT INTO Categories (category_name, userID) VALUES ('%s', %d)", categoryName, user.id);
            ResultSet rs = DB.executeQuery(query, true);

            if (rs.next()) {
                int categoryId = rs.getInt(1);
                Category newCategory = new Category(categoryId, categoryName, user.id);
                categories.add(newCategory);
                categoryNameTextField.clear();
                categoryError.setText("");


            } else {
                categoryError.setText("Failed to add category.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            categoryError.setText("Failed to add category.");
        }
    }


    @FXML
    private void deleteCategory(ActionEvent event) {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategory == null) {
            categoryError.setText("Please select a category to delete.");
            return;
        }

        try {
            String query = String.format("DELETE FROM Categories WHERE category_id = %d", selectedCategory.getCategory_id());
            if (DB.executeQuery(query, true) != null) {
                categories.remove(selectedCategory);
                categoryError.setText("");
            } else {
                categoryError.setText("Failed to delete category.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            categoryError.setText("Failed to delete category.");
        }
    }
}
