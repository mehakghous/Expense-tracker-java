//package app.controllers;
//
//import app.models.*;
//import app.utils.LocalStorage;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.control.*;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class LastController implements Initializable {
//
//    @FXML
//    private AnchorPane dashboardAnchorPane;
//    @FXML
//    private AnchorPane budgetAnchorPane;
//    @FXML
//    private AnchorPane categoryAnchorPane;
//    @FXML
//    private AnchorPane accountAnchorPane;
//    @FXML
//    private AnchorPane addAnchorPane;
//
//    @FXML
//    private Button homeButton;
//    @FXML
//    private Button budgetButton;
//    @FXML
//    private Button categoryButton;
//    @FXML
//    private Button accountButton;
//    @FXML
//    private Button addButton;
//
//    @FXML
//    private TableColumn<Category, String> categoryNameColoumn;
//
//    @FXML
//    private TableView<Category> categoryTable;
//
//    @FXML
//    private TextField categoryNameTextField;
//
//    @FXML
//    private Label categoryError;
//
//    @FXML
//    private TableColumn<Account, String> accountNameColoumn;
//
//    @FXML
//    private TableColumn<Account, String> amountColoumn;
//
//    @FXML
//    private TableView<Account> accountTable;
//
//    @FXML
//    private TextField accountNameTextField;
//
//    @FXML
//    private TextField amountTextField;
//    @FXML
//    private Label accountError;
//    @FXML
//    private ComboBox budgetComboBox;
//
//    @FXML
//    private TableColumn<Expense, String> expenseCategoryColoumn;
//    @FXML
//    private TableColumn<Expense, String> expenseAmountColoumn;
//    @FXML
//    private TableColumn<Expense, String> expenseAccountColoumn;
//
//    @FXML
//    private TableColumn<Income, String> incomeAccountNameColoumn;
//    @FXML
//    private TableColumn<Income, String> incomeAccountAmountColoumn;
//
//    @FXML
//    private TableView<Expense> expenseTable;
//    @FXML
//    private TableView<Income> incomeTable;
//    @FXML
//    private ComboBox iAccountComboBox;
//
//    @FXML
//    private TableView<Budget> budgetTable;
//    @FXML
//    private TableColumn<Budget, String> budgetCategoryColumn;
//    @FXML
//    private TableColumn<Budget, String> budgetAmountColumn;
//    @FXML
//    private TextField budgetTextField;
//
//    @FXML
//    private DatePicker endDatePicker;
//    @FXML
//    private DatePicker startDatePicker;
//
//    private Map<String, Double> expensesMap = new HashMap<>();
//    private Map<String, Double> incomeBalances = new HashMap<>();
//    private Map<String, Double> budgetMap = new HashMap<>();
//
//    LocalDate startDate;
//    LocalDate endDate;
//
//    String expenseAmountforTable;
//
//    @FXML
//    public void switchForm(ActionEvent event) {
//
//        if (event.getSource() == homeButton) {
//            dashboardAnchorPane.setVisible(true);
//            budgetAnchorPane.setVisible(false);
//            categoryAnchorPane.setVisible(false);
//            accountAnchorPane.setVisible(false);
//            addAnchorPane.setVisible(false);
//
//        } else if (event.getSource() == accountButton) {
//            dashboardAnchorPane.setVisible(false);
//            accountAnchorPane.setVisible(true);
//            categoryAnchorPane.setVisible(false);
//            budgetAnchorPane.setVisible(false);
//            addAnchorPane.setVisible(false);
//
//        } else if (event.getSource() == categoryButton) {
//            dashboardAnchorPane.setVisible(false);
//            accountAnchorPane.setVisible(false);
//            categoryAnchorPane.setVisible(true);
//            budgetAnchorPane.setVisible(false);
//            addAnchorPane.setVisible(false);
//
//        } else if (event.getSource() == budgetButton) {
//            dashboardAnchorPane.setVisible(false);
//            accountAnchorPane.setVisible(false);
//            categoryAnchorPane.setVisible(false);
//            budgetAnchorPane.setVisible(true);
//            addAnchorPane.setVisible(false);
//
//        } else if (event.getSource() == addButton) {
//            dashboardAnchorPane.setVisible(false);
//            accountAnchorPane.setVisible(false);
//            categoryAnchorPane.setVisible(false);
//            budgetAnchorPane.setVisible(false);
//            addAnchorPane.setVisible(true);
//
//
//        }
//
//    }
//
//
//    ObservableList<Category> initialCategoryData() {
//        Category p1 = new Category("Utilities");
//        Category p2 = new Category("Food");
//        Category p3 = new Category("Transport");
//        return FXCollections.observableArrayList(p1, p2, p3);
//    }
//    @FXML
//    private void addCategory(ActionEvent event) {
//
//        if (!categoryNameTextField.getText().isEmpty() && !(categoryNameTextField.getText()).matches(".*\\d.*")) {
//            categoryError.setText("");
//            User user = LocalStorage.getUser();
//            String query = String.format("INSERT INTO Categories (category_name, userID) VALUES (%s, %d)",categoryNameTextField.getText(), user.id);
//            Category newData = new Category(categoryNameTextField.getText());
//            categoryTable.getItems().add(newData);
//            categoryComboBox.getItems().add(categoryNameTextField.getText());
//            budgetComboBox.getItems().add(categoryNameTextField.getText());
//            categoryNameTextField.clear();
//        } else if(categoryNameTextField.getText().isEmpty()) {
//                categoryError.setText("Field should not be empty.");
//            } else if((categoryNameTextField.getText()).matches(".*\\d.*")) {
//                    categoryError.setText("Category Name cannot contain numbers.");
//            }
//
//
//    }
//
//    @FXML
//    private void deleteCategory(ActionEvent event) {
//        TableView.TableViewSelectionModel<Category> selectionModel = categoryTable.getSelectionModel();
//        if (selectionModel.isEmpty()) {
//            categoryError.setText("You need to select a row before deleting.");
//        }
//
//        ObservableList<Integer> list = selectionModel.getSelectedIndices();
//        Integer[] selectedIndices = new Integer[list.size()];
//        selectedIndices = list.toArray(selectedIndices);
//        Arrays.sort(selectedIndices);
//
//        for (int i = selectedIndices.length - 1; i >= 0; i--) {
//            selectionModel.clearSelection(selectedIndices[i].intValue());
//            categoryTable.getItems().remove(selectedIndices[i].intValue());
//            Category selectedCategory = categoryTable.getItems().get(selectedIndices[i]);
//            String categoryName = selectedCategory.getCategoryName();
//            categoryComboBox.getItems().remove(categoryName);
//            budgetComboBox.getItems().remove(categoryName);
//        }
//    }
//
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        categoryNameColoumn.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
//
//        categoryTable.setItems(initialCategoryData());
//
//        editCategory();
//
//        accountNameColoumn.setCellValueFactory(new PropertyValueFactory<Account, String>("accountName"));
//        amountColoumn.setCellValueFactory(new PropertyValueFactory<Account, String>("amount"));
//
//        accountTable.setItems(initialAccountData());
//        editAccount();
//
//        expenseCategoryColoumn.setCellValueFactory(new PropertyValueFactory<Expense, String>("categoryName"));
//        expenseAmountColoumn.setCellValueFactory(new PropertyValueFactory<Expense, String>("expenseAmount"));
//        expenseAccountColoumn.setCellValueFactory(new PropertyValueFactory<Expense, String>("accountName"));
//        editExpense();
//
//        incomeAccountAmountColoumn.setCellValueFactory(new PropertyValueFactory<Income, String>("incomeAmount"));
//        incomeAccountNameColoumn.setCellValueFactory(new PropertyValueFactory<Income, String>("accountName"));
//        editIncome();
//
//        budgetCategoryColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("categoryName"));
//        budgetAmountColumn.setCellValueFactory(new PropertyValueFactory<Budget, String>("budget"));
//        editBudget();
//
//
//
//        // Populate categoryComboBox items and synchronize with budgetComboBox
//        ObservableList<String> categoryItems = FXCollections.observableArrayList();
//        for (Category category : categoryTable.getItems()) {
//            categoryItems.add(category.getCategoryName());
//        }
//        budgetComboBox.setItems(categoryItems); // Synchronize budgetComboBox with categoryComboBox
//
//
//}
//
//    private void editCategory() {
//        categoryNameColoumn.setCellFactory(TextFieldTableCell.<Category>forTableColumn());
//        categoryNameColoumn.setOnEditCommit(event -> {
//            Category category = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            category.setCategoryName(event.getNewValue());
//        });
//
//
//    }
//
//
//    ObservableList<Account> initialAccountData() {
//        Account p1 = new Account("Savings", "0");
//        Account p2 = new Account("Card", "0");
//        Account p3 = new Account("Cash", "0");
//        return FXCollections.observableArrayList(p1, p2, p3);
//    }
//
//    @FXML
//    private void addAccount(ActionEvent event) {
//
//        if (!accountNameTextField.getText().isEmpty() && !amountTextField.getText().isEmpty() && !(accountNameTextField.getText()).matches(".*\\d.*") && amountTextField.getText().matches("^\\d*\\.?\\d+$")) {
//            accountError.setText("");
//            Account newData = new Account(accountNameTextField.getText(), amountTextField.getText());
//            accountTable.getItems().add(newData);
//            eAccountComboBox.getItems().add(accountNameTextField.getText());
//            iAccountComboBox.getItems().add(accountNameTextField.getText());
//            accountNameTextField.clear();
//            amountTextField.clear();
//        } else if (accountNameTextField.getText().isEmpty() || amountTextField.getText().isEmpty()) {
//                accountError.setText("Fields should not be empty.");
//        } else if ((accountNameTextField.getText()).matches(".*\\d.*") || !(amountTextField.getText().matches("^\\d*\\.?\\d+$"))) {
//            if ((accountNameTextField.getText()).matches(".*\\d.*")) {
//                accountError.setText("Account Name cannot contain numbers.");
//            } else {
//                accountError.setText("Amount cannot be other than numbers.");
//            }
//        }
//
//    }
//
//    @FXML
//    private void deleteAccount(ActionEvent event) {
//        TableView.TableViewSelectionModel<Account> selectionModel = accountTable.getSelectionModel();
//        if (selectionModel.isEmpty()) {
//            accountError.setText("You need select a row before deleting.");
//        }
//
//        ObservableList<Integer> list = selectionModel.getSelectedIndices();
//        Integer[] selectedIndices = new Integer[list.size()];
//        selectedIndices = list.toArray(selectedIndices);
//
//        Arrays.sort(selectedIndices);
//
//        for (int i = selectedIndices.length - 1; i >= 0; i--) {
//            Account selectedAccount = accountTable.getItems().get(selectedIndices[i]);
//            String accountName = selectedAccount.getAccountName();
//            selectionModel.clearSelection(selectedIndices[i].intValue());
//            accountTable.getItems().remove(selectedIndices[i].intValue());
//            eAccountComboBox.getItems().remove(accountName);
//            iAccountComboBox.getItems().remove(accountName);
//
//        }
//    }
//
//    private void editAccount() {
//        accountNameColoumn.setCellFactory(TextFieldTableCell.<Account>forTableColumn());
//        accountNameColoumn.setOnEditCommit(event -> {
//            Account account = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            account.setAccountName(event.getNewValue());
//        });
//
//        amountColoumn.setCellFactory(TextFieldTableCell.<Account>forTableColumn());
//        amountColoumn.setOnEditCommit(event -> {
//            Account account = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            account.setAmount(event.getNewValue());
//        });
//
//
//    }
//
//    @FXML
//    ComboBox categoryComboBox;
//    @FXML
//    ComboBox eAccountComboBox;
//
//    @FXML
//    private void showIncomeInputDialog(String selectedIncomeOption) {
//        TextField incomeField = new TextField();
//        incomeField.setPromptText("Enter income amount");
//
//        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
//        dialog.setTitle("Add income");
//        dialog.setHeaderText("Enter income Amount for " + selectedIncomeOption);
//        dialog.getDialogPane().setContent(incomeField);
//        dialog.getButtonTypes().clear();
//        dialog.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//        Optional<ButtonType> result = dialog.showAndWait();
//        result.ifPresent(buttonType -> {
//            if (buttonType == ButtonType.OK) {
//                String amountText = incomeField.getText();
//                try {
//                    double amount = Double.parseDouble(amountText);
//                    if (amount > 0) {
//                        incomeAmountforTable = amountText;
//                        System.out.println("Income added to " + selectedIncomeOption + ": " + amount);
//                        updateBalance(selectedIncomeOption, amount);
//                    } else {
//                        showErrorDialog("Invalid Input", "Please enter a positive number.");
//                    }
//                } catch (NumberFormatException e) {
//                    showErrorDialog("Invalid Input", "Please enter a valid number.");
//                }
//            }
//        });
//    }
//    @FXML
//    public void onAddIncomeButtonClick() {
//        String selectedIncomeOption = (String) iAccountComboBox.getSelectionModel().getSelectedItem();
//        String selectedIncomeAmount = incomeAmountforTable;
//        if (selectedIncomeOption == null) {
//            showErrorDialog("Error", "Please select an option first.");
//        } else {
//            Income incomeData = new Income(selectedIncomeOption, selectedIncomeAmount );
//            incomeTable.getItems().add(incomeData);
//        }
//    }
//
//
//
//    public void onDeleteIncomeButtonClick() {
//
//        TableView.TableViewSelectionModel<Income> selectionModel = incomeTable.getSelectionModel();
//        if (selectionModel.isEmpty()) {
//            showErrorDialog("Error", "Please select an expense to delete");
//        }
//
//        ObservableList<Integer> list = selectionModel.getSelectedIndices();
//        Integer[] selectedIndices = new Integer[list.size()];
//        selectedIndices = list.toArray(selectedIndices);
//
//        Arrays.sort(selectedIndices);
//
//        for (int i = selectedIndices.length - 1; i >= 0; i--) {
//            selectionModel.clearSelection(selectedIndices[i].intValue());
//            incomeTable.getItems().remove(selectedIndices[i].intValue());
//        }
//    }
//
//    public void AddIncome(ActionEvent actionEvent) {
//
//        String selectedAccount = (String)iAccountComboBox.getSelectionModel().getSelectedItem();
//
//        if (selectedAccount == null) {
//            showErrorDialog("Error", "Please select an account first.");
//            return;
//        }
//        showIncomeInputDialog(selectedAccount);
//    }
//
//    private void showExpenseInputDialog(String selectedOption, String selectedAccount) {
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Add Expense");
//        dialog.setHeaderText("Enter Expense Amount for " + selectedOption);
//        dialog.setContentText("Amount:");
//
//        Optional<String> result = dialog.showAndWait();
//        result.ifPresent(amountText -> {
//            try {
//                double amount = Double.parseDouble(amountText);
//                if (amount > 0) {
//                    System.out.println("Expense added to " + selectedOption + ": " + amount);
//                    expenseAmountforTable = amountText;
//                    updateExpenses(selectedOption, amount); // Update expenses
//                    deductExpenseFromAccount(selectedAccount, amount); // Deduct expense from account
//                } else {
//                    showErrorDialog("Invalid Input", "Please enter a positive number.");
//                }
//            } catch (NumberFormatException e) {
//                showErrorDialog("Invalid Input", "Please enter a valid number.");
//            }
//        });
//    }
//
//    @FXML
//    public void onAddExpenseButtonClick(ActionEvent actionEvent){
//        String selectedOption = (String) categoryComboBox.getSelectionModel().getSelectedItem();
//        String selectedAccount = (String) eAccountComboBox.getSelectionModel().getSelectedItem();
//        System.out.println(selectedAccount);
//        if (selectedOption == null || selectedAccount == null) {
//            showErrorDialog("Error", "Please select an expense category, an account and expense amount first.");
//        }
//        else{
//
//            showExpenseInputDialog(selectedOption, selectedAccount);
//            String selectedAmount = expenseAmountforTable;
//            Expense expenseData = new Expense(selectedOption, selectedAmount,selectedAccount);
//            expenseTable.getItems().add(expenseData);
//        }
//
//
//    }
//
//    @FXML
//    private void showErrorDialog(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//    private void editExpense()
//    {
//        expenseCategoryColoumn.setCellFactory(TextFieldTableCell.<Expense>forTableColumn());
//        expenseCategoryColoumn.setOnEditCommit(event -> {
//            Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            expense.setCategoryName(event.getNewValue());
//        });
//
//        expenseAmountColoumn.setCellFactory(TextFieldTableCell.<Expense>forTableColumn());
//        expenseAmountColoumn.setOnEditCommit(event -> {
//            Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            expense.setExpenseAmount(event.getNewValue());
//        });
//
//        expenseAccountColoumn.setCellFactory(TextFieldTableCell.<Expense>forTableColumn());
//        expenseAccountColoumn.setOnEditCommit(event -> {
//            Expense expense = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            expense.setAccountName(event.getNewValue());
//        });
//
//
//    }
//    private void editIncome()
//    {
//
//        incomeAccountNameColoumn.setCellFactory(TextFieldTableCell.<Income>forTableColumn());
//        incomeAccountNameColoumn.setOnEditCommit(event -> {
//            Income income = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            income.setAccountName(event.getNewValue());
//        });
//
//        incomeAccountAmountColoumn.setCellFactory(TextFieldTableCell.<Income>forTableColumn());
//        incomeAccountAmountColoumn.setOnEditCommit(event -> {
//            Income income = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            income.setIncomeAmount(event.getNewValue());
//        });
//
//
//    }
//
//    String incomeAmountforTable;
//
//    @FXML
//    private void showInfoDialog(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    private void updateBalance(String incomeOption, double incomeAmount) {
//        double currentBalance = incomeBalances.getOrDefault(incomeOption, 0.0);
//        double newBalance = currentBalance + incomeAmount;
//        incomeBalances.put(incomeOption, newBalance);
//    }
//
//    private void updateExpenses(String expenseCategory, double expenseAmount) {
//        expensesMap.put(expenseCategory, expensesMap.getOrDefault(expenseCategory, 0.0) + expenseAmount);
//    }
//
//    @FXML
//    private void onDeletexpenseButtonClick() {
//        TableView.TableViewSelectionModel<Expense> selectionModel = expenseTable.getSelectionModel();
//        if (selectionModel.isEmpty()) {
//           showErrorDialog("Error", "Please select an expense to delete");
//        }
//
//        ObservableList<Integer> list = selectionModel.getSelectedIndices();
//        Integer[] selectedIndices = new Integer[list.size()];
//        selectedIndices = list.toArray(selectedIndices);
//
//        Arrays.sort(selectedIndices);
//
//        for (int i = selectedIndices.length - 1; i >= 0; i--) {
//            selectionModel.clearSelection(selectedIndices[i].intValue());
//            expenseTable.getItems().remove(selectedIndices[i].intValue());
//        }
//    }
//
//
//    private void deductExpenseFromAccount(String selectedAccount, double expenseAmount) {
//        double currentBalance = incomeBalances.getOrDefault(selectedAccount, 0.0);
//        double newBalance = currentBalance - expenseAmount;
//        incomeBalances.put(selectedAccount, newBalance);
//    }
//
//
//    @FXML
//    public void getStartDate(ActionEvent event) {
//        startDate = startDatePicker.getValue();
//        if (startDate != null) {
//            String myStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
//        } else {
//            System.out.println("Null");
//            // Handle case when no start date is selected
//        }
//    }
//
//    @FXML
//    public void getEndDate(ActionEvent event) {
//        endDate = endDatePicker.getValue();
//        if (endDate != null) {
//            String myEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
//        } else {
//            System.out.println("Null");
//            // Handle case when no end date is selected
//        }
//    }
//
//
//    private void updateBudget(String expenseCategory, double budget) {
//        budgetMap.put(expenseCategory, budgetMap.getOrDefault(expenseCategory, 0.0) + budget);
//    }
//
//    @FXML
//    void addBudgetButton(ActionEvent event) {
//        // Add debug statement to check selected dates
//        System.out.println("Selected Start Date: " + startDate);
//        System.out.println("Selected End Date: " + endDate);
//        String amountText = budgetTextField.getText();
//        try {
//            double amount = Double.parseDouble(amountText);
//            String selectedCategory = (String) budgetComboBox.getSelectionModel().getSelectedItem();
//            Budget newBudget = new Budget(selectedCategory, amountText);
//            budgetTable.getItems().add(newBudget);
//            budgetTextField.clear();
//            System.out.println("Budget added successfully.");
//            if (amount <= 0) {
//                showErrorDialog("Error", "Please enter a valid positive amount for the budget.");
//                return;
//            }
//            if (selectedCategory == null) {
//                showErrorDialog("Error", "Please select a category for the budget.");
//                return;
//            }
//            if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
//                showErrorDialog("Error", "Please select valid start and end dates for the budget period.");
//                return;
//            }
//
//        } catch (NumberFormatException e) {
//            showErrorDialog("Invalid Input", "Please enter a valid number for the budget amount.");
//        }
//    }
//    void editBudget()
//    {
//        budgetCategoryColumn.setCellFactory(TextFieldTableCell.<Budget>forTableColumn());
//        budgetCategoryColumn.setOnEditCommit(event -> {
//            Budget budget = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            budget.setCategoryName(event.getNewValue());
//        });
//
//        budgetAmountColumn.setCellFactory(TextFieldTableCell.<Budget>forTableColumn());
//        budgetAmountColumn.setOnEditCommit(event -> {
//            Budget budget = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            budget.setBudget(event.getNewValue());
//        });
//    }
//    @FXML
//    void onDeleteBudgetButtonClick() {
//
//        TableView.TableViewSelectionModel<Budget> selectionModel = budgetTable.getSelectionModel();
//        if (selectionModel.isEmpty()) {
//            showErrorDialog("Error", "Please select an expense to delete");
//        }
//
//        ObservableList<Integer> list = selectionModel.getSelectedIndices();
//        Integer[] selectedIndices = new Integer[list.size()];
//        selectedIndices = list.toArray(selectedIndices);
//
//        Arrays.sort(selectedIndices);
//
//        for (int i = selectedIndices.length - 1; i >= 0; i--) {
//            selectionModel.clearSelection(selectedIndices[i].intValue());
//            budgetTable.getItems().remove(selectedIndices[i].intValue());
//        }
//    }
//}