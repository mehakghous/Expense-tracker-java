package app.models;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class Account {
    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    private String account_name;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    private Integer amount;

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    private Integer account_id;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    private Integer userID;

    public static TableColumn<Account, String> getAccountNameColoumn(){
        TableColumn<Account, String> accountNameColoumn = new TableColumn<>("Account Name");
        accountNameColoumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        return accountNameColoumn;
    }

    public static TableColumn<Account, String> getAmountColoumn(){
        TableColumn<Account, String> amountColoumn = new TableColumn<>("Amount");
        amountColoumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        return amountColoumn;
    }



    public Account(Integer account_id, String account_name, Integer amount,Integer userID) {
     this.account_name = account_name;
     this.account_id = account_id;
     this.amount = amount;
     this.userID = userID;
    }

    @Override
    public String toString() {
        return this.account_name;
    }
}
