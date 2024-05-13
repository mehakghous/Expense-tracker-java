//    void editBudget() {
//        budgetCategoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        budgetCategoryColumn.setOnEditCommit(event -> {
//            Budget budget = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            budget.setCategoryName(event.getNewValue());
//        });
//
//        budgetAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        budgetAmountColumn.setOnEditCommit(event -> {
//            Budget budget = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            budget.setBudget(event.getNewValue());
//        });
//    }
//

package app.controllers;
import app.models.Budget;
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
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BudgetController implements Initializable {

    @FXML
    private AnchorPane budgetAnchorPane;

    @FXML
    private TableView<Budget> budgetTable;

    @FXML
    private TableColumn<Budget, String> budgetCategoryColumn;

    @FXML
    private TableColumn<Budget, String> budgetAmountColumn;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private TextField budgetTextField;
    User user;

    private ObservableList<Budget> budgets = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = LocalStorage.getUser();
        budgetCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        budgetAmountColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        loadCategories();
        loadBudgets();
        editBudget();
    }
    private void loadCategories() {
            LocalStorage.getCategories().forEach((category -> {
                categoryComboBox.getItems().add(category);
            }));
    }
    private void loadBudgets() {
        try {
            ResultSet resultSet = DB.executeQuery("SELECT * FROM budgets", false);
            while (resultSet.next()) {
                int budget_id = resultSet.getInt(1);
                String category_name = resultSet.getString("category_name");
                String budget = resultSet.getString("budget_amount");
                Budget loadedBudget = new Budget(budget_id, category_name, budget);
                budgets.add(loadedBudget);
            }
            budgetTable.setItems(budgets);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addBudgetButton(ActionEvent event) {
        try {
            String category_name = categoryComboBox.getSelectionModel().getSelectedItem().getCategory_name();
            String amountText = budgetTextField.getText();
            if (amountText.isEmpty() || category_name == null) {
                showErrorDialog("Error", "Please enter both category and budget amount.");
                return;
            }
            int amount = Integer.parseInt(amountText);
            if (amount <= 0) {
                showErrorDialog("Error", "Please enter a valid positive amount for the budget.");
                return;
            }
            String query = String.format("INSERT INTO budgets ( userID, budget_amount,category_name) VALUES (%d,%d,'%s')", user.id,amount, category_name);
            ResultSet resultSet = DB.executeQuery(query, true);
            boolean categoryExists = false;
            if(resultSet.next()){
               int budget_id =  resultSet.getInt(1);
                Budget newBudget = new Budget(budget_id,category_name, amountText);
                for (Budget budget : budgets) {
                    if (budget.getCategory_name().equals(category_name)) {
                        categoryExists = true;
                        break;
                    }
                }

                if (categoryExists) {
                    showErrorDialog("Error", "Category already exists, edit instead!");
                    return;
                }
                budgets.add(newBudget);
                budgetTable.setItems(budgets);
                budgetTextField.clear();
                categoryComboBox.getSelectionModel().clearSelection();
            }
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e);
            showErrorDialog("Invalid Input", "Please enter a valid number for the budget amount.");
        }
    }

    @FXML
    void deleteBudgetButton(ActionEvent event) {
        try {
            Budget selectedBudget = budgetTable.getSelectionModel().getSelectedItem();
            if (selectedBudget == null) {
                showErrorDialog("Error", "Please select a budget to delete.");
                return;
            }
            String query = String.format("DELETE FROM Budgets WHERE budget_id = %d", selectedBudget.getBudget_id());
            if (DB.executeQuery(query, true) != null) {
                budgets.remove(selectedBudget);
            }
        } catch (SQLException e) {
            showErrorDialog("Error", "Failed to delete the selected budget.");
        }
    }

    void editBudget() {
        // Editing of budgets not implemented in this version
    }

    @FXML
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
