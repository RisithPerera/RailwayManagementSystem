package com.view.ctrl;

import com.base.client.impl.OfficerClientImpl;
import com.main.Main;
import com.manifest.Message;
import com.manifest.View;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.model.child.Officer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginCtrl implements Initializable {
    
    @FXML
    private Label currentTimeText;

    @FXML
    private TextField usernameText;
    
    @FXML
    private Button loginBtn;
    
    @FXML
    private PasswordField passwordText;
    
    private Stage primaryStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        primaryStage = Main.primaryStage;
        currentTimeText.textProperty().bind(Main.timeTask.messageProperty());

        loginBtnEnable();
        validationNodes();
    }

    @FXML
    void loginBtnEvent(ActionEvent event) {        
        try{
            Officer officer = OfficerClientImpl.getInstance().getOfficer(usernameText.getText(),passwordText.getText());
            if( officer != null){
                primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(View.PATH, View.MAIN)))));
                MainCtrl.getInstance().updateLoginContent(officer);
            }else{
                MessageBoxViewCtrl.display(Message.TITLE,"Incorrect details!");
            }
            clearFields();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void closeBtnEvent(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

    //------------------------- Addtional Methods ----------------------------//
    
    private void clearFields(){
        usernameText.clear();
        passwordText.clear();
    }
    
    private void validationNodes() {
        usernameText.setOnKeyTyped((KeyEvent event) -> {
            String str = event.getCharacter();
            if(!str.matches("[A-Za-z]")) {
                event.consume();
            }
        });
  
        usernameText.setOnKeyReleased((KeyEvent event) -> {
             loginBtnEnable(); 
        });
        
        passwordText.setOnKeyReleased((KeyEvent event) -> {
             loginBtnEnable(); 
        });
    }
    
    private void loginBtnEnable() {
        boolean action1 = usernameText.getText().trim().equals("");
        boolean action2 = passwordText.getText().trim().equals("");
        
        if(action1 || action2){
            loginBtn.setDisable(true);
        }else{
            loginBtn.setDisable(false);
        }
    }
}
