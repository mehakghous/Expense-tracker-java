package app.controllers;

import app.models.User;
import app.utils.DB;
import app.utils.LocalStorage;
import app.utils.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;


public class LoginController {

    @FXML
    private Label loginmessagelabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Hyperlink signupLink;
    @FXML
    private Button loginBtn;

    public void loginButtonOnAction(ActionEvent e){
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()){
            loginmessagelabel.setText("Fill the fields!");
        }else{
           try{
               loginmessagelabel.setText("");
               String query = String.format("SELECT * FROM users WHERE username='%s' AND password='%s' ", usernameField.getText(),passwordField.getText());
               ResultSet rs = DB.executeQuery(query,false);
               if(rs.next()){
                   LocalStorage.setUser(
                           new User(
                                   rs.getInt(1),
                                   usernameField.getText()
                           )
                   );
                   Router.navigate(loginBtn,getClass().getResource("/app/fxml/dashboard.fxml"));
               }
               loginmessagelabel.setText("Please Provide Valid Credentials");
           }catch(Exception ex){
               System.out.println(ex);
           }

        }
    }
    @FXML
    public void gosignup(ActionEvent event){
        try{
            Router.navigate(signupLink,getClass().getResource("/app/fxml/signup.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}