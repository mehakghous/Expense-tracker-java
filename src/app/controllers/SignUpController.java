package app.controllers;

import app.models.User;
import app.utils.DB;
import app.utils.LocalStorage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import app.utils.Router;


import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button signupButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField setPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML

    public void signup(ActionEvent event) {
        try {
            String query = String.format("INSERT INTO users (firstname,lastname,password,username) VALUES ('%s','%s','%s', '%s')",
                    firstnameField.getText(), lastnameField.getText(), setPasswordField.getText(), usernameField.getText());
            ResultSet resultSet = DB.executeQuery(query, true);
            if (resultSet.next()) {
                LocalStorage.setUser(
                        new User(
                                resultSet.getInt(1),
                                usernameField.getText()
                        )
                );
                Router.navigate(signupButton, getClass().getResource("/app/fxml/_dashboard.fxml"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
