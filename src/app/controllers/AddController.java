package app.controllers;

import app.models.*;
import app.utils.DB;
import app.utils.LocalStorage;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddController implements Initializable {

    @FXML
    private Label categoryError;

    @FXML
    private TextField accountNameTextField;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label accountError;

    @FXML
    private ComboBox<Category> categoryComboBox;

    @FXML
    private ComboBox<Account> eAccountComboBox;

    @FXML
    private ComboBox<Account> iAccountComboBox;

    @FXML
    private TableView<Expense> expenseTable;

    @FXML
    private TableColumn<Expense, String> expenseCategoryColumn;

    @FXML
    private TableColumn<Expense, String> expenseAmountColumn;

    @FXML
    private TableColumn<Expense, String> expenseAccountColumn;

    @FXML
    private TableView<Income> incomeTable;

    @FXML
    private TableColumn<Income, String> incomeAccountNameColumn;

    @FXML
    private TableColumn<Income, String> incomeAccountAmountColumn;

    @FXML
    private TextField expenseTextField;
    @FXML
    private TextField incomeTextField;
    @FXML
    private ObservableList<Expense> expenses = FXCollections.observableArrayList();
    @FXML
    private ObservableList<Income> incomes = FXCollections.observableArrayList();

    @FXML
    private TabPane tabpane;

//    private Map<String, Double> expensesMap = new HashMap<>();
//    private Map<String, Double> incomeBalances = new HashMap<>();
//    private Map<String, Double> budgetMap = new HashMap<>();

User user;
    ObservableList<Category> categoryItems = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = LocalStorage.getUser();
//        if(tabpane.getSelectionModel().getSelectedItem().getText().equals("Expenses")){
            expenseCategoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory().getCategory_name()));
            expenseAccountColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccount().getAccount_name()));
            expenseAmountColumn.setCellValueFactory(new PropertyValueFactory<>("expenseAmount"));
            loadExpenses();
            loadCategories();
//        }else {

            incomeAccountNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccount().getAccount_name()));
            incomeAccountAmountColumn.setCellValueFactory(new PropertyValueFactory<>("incomeAmount"));
            loadIncomes();
//        }
        loadAccounts();

    }
    private void loadCategories() {
try{
    LocalStorage.getCategories().forEach((category -> {
        categoryComboBox.getItems().add(category);
    }));
}catch(Exception e){
    System.out.println(e);
}
    }
    private void loadAccounts() {
        LocalStorage.getAccounts().forEach((account -> {
           eAccountComboBox.getItems().add(account);
           iAccountComboBox.getItems().add(account);
        }));
    }
    private void loadExpenses() {
        user = LocalStorage.getUser();
        try {
            ResultSet rs = DB.executeQuery(
                    "SELECT e.*, a.*, c.*\n" +
                            "FROM Expenses e\n" +
                            "INNER JOIN Accounts a ON e.account_id = a.account_id\n" +
                            "INNER JOIN Categories c ON e.category_id = c.category_id;",
                    false
            );
            while (rs.next()) {
              int expenseId = rs.getInt("e.expense_id");
    String expenseAmount = rs.getString("e.expense_amount");
    String date = rs.getString("e.expense_date");
    int userID = rs.getInt("e.userID");
    // Create new Account and Category objects
    Account account = new Account(
            rs.getInt("a.account_id"),
            rs.getString("a.account_name"),
            rs.getInt("a.amount_saved"),
            user.id
    );
    Category category = new Category(
            rs.getInt("c.category_id"),
            rs.getString("c.category_name"),
            user.id
    );

    // Create new Expense object and add it to the list
    Expense expense = new Expense(expenseId, category, expenseAmount, account, date, userID);
    expenses.add(expense);
            }
            expenseTable.setItems(expenses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadIncomes() {
        user = LocalStorage.getUser();
        try {
            System.out.println("Challing 1");
            ResultSet rs = DB.executeQuery(
                    "SELECT i.*, a.*\n" +
                            "FROM Incomes i\n" +
                            "INNER JOIN Accounts a ON i.account_id = a.account_id;",
                    false
            );
            System.out.println("Challing");
            while (rs.next()) {
                Integer income_id = rs.getInt("i.income_id");
                System.out.println(income_id);
                String incomeAmount = rs.getString("i.income_amount");
                // Create new Account and Category objects
                Account account = new Account(
                        rs.getInt("a.account_id"),
                        rs.getString("a.account_name"),
                        rs.getInt("a.amount_saved"),
                        user.id
                );
                Income income = new Income(income_id, account, incomeAmount);
                incomes.add(income);
            }
            incomeTable.setItems(incomes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void onAddExpenseButtonClick(ActionEvent actionEvent) throws Exception{
        user = LocalStorage.getUser();
        Category selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
        int selectedCategoryId = selectedCategory.getCategory_id() ;
        Account selectedAccount = eAccountComboBox.getSelectionModel().getSelectedItem();
        int selectedAccountId = selectedAccount.getAccount_id();
        String expenseAmount =  expenseTextField.getText();
        if (selectedCategory.getCategory_name() == null || selectedAccount.getAccount_name() == null) {
            showErrorDialog("Error", "Please select an expense category, an account and expense amount first.");
        }else if(expenseAmount == null || !expenseAmount.matches("\\d+(\\.\\d+)?")){
            showErrorDialog("Error", "Enter valid Input!");
        }
        else{
            Integer eAmount = Integer.parseInt(expenseAmount);
            LocalDate c = LocalDate.now();
            String currentDate = c.toString();


           try{
               String query = String.format("INSERT INTO Expenses (userID, category_id, account_id, expense_amount, expense_date) VALUES (%d, %d,%d,%d,'%s')", user.id,selectedCategoryId,selectedAccountId,eAmount,currentDate);
               ResultSet rs = DB.executeQuery(query, true);
               if(rs.next()){
                   Integer expenseId= rs.getInt(1);
                   Expense expenseData = new Expense(expenseId,selectedCategory, expenseAmount,selectedAccount , currentDate, user.id);
                    expenses.add(expenseData);
               }
           }catch(Exception e){
               System.out.println(e);
           }

            updateAccountAmount(eAmount, selectedAccount);
        }

    }
    @FXML
    private void  updateAccountAmount(Integer eAmount,Account selectedAccount){
      try{
          String query = String.format("UPDATE Accounts SET amount_saved = amount_saved - %d WHERE account_id = %d",eAmount, selectedAccount.getAccount_id());
          ResultSet rs = DB.executeQuery(query,true);
          if (rs.next()){
              Integer Balance = rs.getInt("amount_saved");
              iAccountComboBox.getSelectionModel().getSelectedItem().setAmount(Balance);
          }
      }catch(Exception e){
          System.out.println(e);
      }

    }
    @FXML
    private void onDeleteExpenseButtonClick(ActionEvent event) {
        // Implement logic to add expense
    }

    @FXML
    private void onAddIncomeButtonClick(ActionEvent event) {
        user = LocalStorage.getUser();
        Account selectedAccount = iAccountComboBox.getSelectionModel().getSelectedItem();
        int selectedAccountId = selectedAccount.getAccount_id();
        if (selectedAccount.getAccount_name() == null) {
            showErrorDialog("Error", "Please select an account first.");
        }
        String in = incomeTextField.getText();
        Integer incomeAmount = Integer.parseInt(in);
            try{
                Integer newAmount = selectedAccount.getAmount() + incomeAmount;
                selectedAccount.setAmount(newAmount);
                String query = String.format("UPDATE Accounts SET amount_saved = %d WHERE account_id = %d", newAmount, selectedAccountId);;
                ResultSet rs = DB.executeQuery(query, true);
                System.out.println("Chaling....");
//                if(rs.next()){
                   try{
                       String _query = String.format("INSERT INTO Incomes (userID, account_id, income_amount) VALUES (%d,%d,%d)",user.id,selectedAccountId,incomeAmount);
                       ResultSet _rs = DB.executeQuery(_query, true);
                       if(_rs.next()){
                           Integer incomeId = _rs.getInt(1);
                           Income income = new Income(incomeId, selectedAccount, in);
                           incomes.add(income);
                           incomeTable.setItems(incomes);
                       }
                   }catch(Exception e){
                       System.out.println(e);
                   }
//                }
            }catch(Exception e){
                System.out.println(e);
            }

        }


//
    @FXML
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


//    private void updateBalance(String incomeOption, double incomeAmount) {
//        double currentBalance = incomeBalances.getOrDefault(incomeOption, 0.0);
//        double newBalance = currentBalance + incomeAmount;
//        incomeBalances.put(incomeOption, newBalance);
//    }

//
//    private void deductExpenseFromAccount(String selectedAccount, double expenseAmount) {
//        double currentBalance = incomeBalances.getOrDefault(selectedAccount, 0.0);
//        double newBalance = currentBalance - expenseAmount;
//        incomeBalances.put(selectedAccount, newBalance);
//    }
//
//    private void updateBudget(String expenseCategory, double budget) {
//        budgetMap.put(expenseCategory, budgetMap.getOrDefault(expenseCategory, 0.0) + budget);
//    }
}
