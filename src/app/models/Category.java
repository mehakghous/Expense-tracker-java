package app.models;

public class Category {
    private Integer category_id;
    private Integer userID;
    private String category_name;

    public Category() {}

    public Category(Integer category_id, String category_name, Integer userID) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.userID = userID;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return this.category_name;
    }
}
