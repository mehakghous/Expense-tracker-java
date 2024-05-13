//package app.models;
//
//public class Budget{
//        String category_name;
//        String budget;
//
//        public String getCategoryName() {
//            return category_name;
//        }
//
//        public void setCategoryName(String categoryName) {
//            this.category_name = categoryName;
//        }
//
//        public String getBudget() {
//            return budget;
//        }
//
//        public void setBudget(String budget) {
//            this.budget = budget;
//        }
//
//        public Budget(String category_name, String budget) {
//            this.category_name = category_name;
//            this.budget = budget;
//        }
//    }
//

package app.models;

public class Budget {
    private String category_name;
    private String budget;

    public int getBudget_id() {
        return budget_id;
    }

    public void setBudget_id(Integer budget_id) {
        this.budget_id = budget_id;
    }

    private Integer budget_id;

    public Budget(Integer budget_id, String category_name, String budget) {
        this.category_name = category_name;
        this.budget = budget;
        this.budget_id = budget_id;
    }

    // Getters and setters
    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
