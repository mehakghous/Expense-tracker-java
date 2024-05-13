package app.models;

public class Income {

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    Account account;
    Integer income_id;
    String incomeAmount;

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(String incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public Income(Integer income_id, Account account, String incomeAmount) {
        this.account = account;
        this.income_id = income_id;
        this.incomeAmount = incomeAmount;
    }
}
