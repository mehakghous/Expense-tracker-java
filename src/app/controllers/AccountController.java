package app.controllers;

import app.models.Account;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AccountController implements Initializable {

    @FXML
    private AnchorPane accountAnchorPane;

    @FXML
    private TableColumn<Account, String> accountNameColoumn;

    @FXML
    private TableColumn<Account, String> amountColoumn;

    @FXML
    private TableView<Account> accountTable;

    @FXML
    private TextField accountNameTextField;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label accountError;

    @FXML
    private ComboBox<String> eAccountComboBox;
    private ObservableList<Account> accounts = FXCollections.observableArrayList();
    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = LocalStorage.getUser();
        accountNameColoumn.setCellValueFactory(new PropertyValueFactory<>("account_name"));
        amountColoumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        accounts = LocalStorage.getAccounts();
        accountTable.setItems(accounts);
    }
//    private void loadAccounts() {
//        try {
//            ResultSet resultSet = DB.executeQuery("SELECT * FROM Accounts", false);
//            while (resultSet.next()) {
//                Integer account_id = resultSet.getInt(1);
//                String account_name = resultSet.getString("account_name");
//                Integer amount = Integer.parseInt(resultSet.getString("amount_saved"));
//
//                Account loadedAccount = new Account(account_id,account_name,amount,user.id);
//                accounts.add(loadedAccount);
//            }
//            accountTable.setItems(accounts);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    private void addAccount(ActionEvent event) {
        String account_name = accountNameTextField.getText();
        Integer amount = Integer.parseInt(amountTextField.getText());
        if (accountNameTextField.getText().isEmpty() || amountTextField.getText().isEmpty()) {
            accountError.setText("Fields should not be empty.");
        } else if ((accountNameTextField.getText()).matches(".*\\d.*") || !(amountTextField.getText().matches("^\\d*\\.?\\d+$"))) {
            accountError.setText("Enter valid input!");
        }
        try{
            Boolean AccountExists = false;
            String query = String.format("INSERT INTO Accounts (account_name, amount_saved, userID) VALUES ('%s',%d, %d)", account_name,amount, user.id);
            ResultSet rs = DB.executeQuery(query,true);
            if(rs.next()){
                int account_id = rs.getInt(1);
            Account newAccount = new Account(account_id, account_name, amount, user.id);

                for (Account account : accounts) {
                    if (account.getAccount_name().equals(account_name)) {
                        AccountExists = true;
                        break;
                    }
                }

                if (AccountExists) {
                    accountError.setText("Category already exists, edit instead!");
                    return;
                }
            accounts.add(newAccount);
            accountNameTextField.clear();
            amountTextField.clear();
            accountError.setText("");
            }
        }catch(Exception e){
            System.out.println(e);
        }
//            eAccountComboBox.getItems().add(accountNameTextField.getText());
//            accountNameTextField.clear();
//            amountTextField.clear();

    }

    @FXML
    private void deleteAccount(ActionEvent event) {
        Account selectedAccount = accountTable.getSelectionModel().getSelectedItem();
//        TableView.TableViewSelectionModel<Account> selectionModel = accountTable.getSelectionModel();
        if (selectedAccount == null) {
            accountError.setText("You need select a row before deleting.");
        }
        try {
            String query = String.format("DELETE FROM Accounts WHERE account_id = %d", selectedAccount.getAccount_id());
            if (DB.executeQuery(query, true) != null) {
                accounts.remove(selectedAccount);
                accountError.setText("");
            } else {
                accountError.setText("Failed to delete account.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            accountError.setText("Failed to delete account.");
        }
    }
}

