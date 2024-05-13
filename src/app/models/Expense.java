package app.models;

public class Expense {

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    private Account account;
    private Category category;
    private String expenseAmount;
    private Integer expenseId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    private Integer userID;


    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public Expense(Integer expenseId,Category category, String expenseAmount, Account account,String date,Integer userID) {
        this.expenseId = expenseId;
        this.account = account;
        this.category = category;
        this.expenseAmount = expenseAmount;
        this.date = date;
        this.userID = userID;
    }
}
