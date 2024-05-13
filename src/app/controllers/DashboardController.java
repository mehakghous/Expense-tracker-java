package app.controllers;
import app.utils.Router;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane mainContentArea;

    @FXML
    private void openHomeTab() {
        loadFXML("home_tab.fxml");
    }

    @FXML
    private void openBudgetTab() {
        loadFXML("budget_tab.fxml");
    }

    @FXML
    private void openCategoryTab() {
        loadFXML("category_tab.fxml");
    }

    @FXML
    private void openAccountTab() {
        loadFXML("account_tab.fxml");
    }

    @FXML
    private void openExpenseTab() {
        loadFXML("expense.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataFetchController.loadCategories();
        DataFetchController.loadAccounts();
    }

    private void loadFXML(String fxmlFileName)  {
        try {
            AnchorPane tabContent = Router.load(getClass().getResource("/app/fxml/"+ fxmlFileName));
            mainContentArea.getChildren().setAll(tabContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}