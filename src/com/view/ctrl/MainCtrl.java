package com.view.ctrl;


import com.main.Main;
import com.manifest.Data;
import com.manifest.View;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.model.child.Officer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;


public class MainCtrl {

    //--------------------------- FXML Attributes ----------------------------//
    @FXML
    private AnchorPane contentPane;

    @FXML
    private ListView menuListView;

    @FXML
    private Label currentTimeText;
    
    @FXML
    private Label userTypeText, userNameText, loginTimeText, loginDateText;
    
    //---------------------- Normal Attributes -------------------------------//
    private static MainCtrl mainCtrl;   
    private Stage primaryStage;

    public static MainCtrl getInstance() {
        return mainCtrl;
    }
    
    //---------------------- Initialize and Startup Actions ------------------//
    public void initialize() {        
        mainCtrl = this;
        primaryStage = Main.primaryStage;
        currentTimeText.textProperty().bind(Main.timeTask.messageProperty());

        //-------------------------- Add Menu List Event ---------------------------//
        menuListView.getItems().addAll(Data.MENU_LIST);
        menuListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        menuListView.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(new Image(String.format("/com/view/image/%s.png",name)));
                    imageView.setFitWidth(30);
                    imageView.setFitHeight(30);
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });
        showContent(String.format(View.PATH, View.JOURNEY_VIEW));
    }

    //---------------------- Nodes Events ------------------------------------//
    @FXML
    public void menuListViewEvent(MouseEvent event) {
        switch((String) menuListView.getSelectionModel().getSelectedItem()){
            case "Journey" :
                showContent(String.format(View.PATH, View.JOURNEY_VIEW));
                break;
            case "Train" :
                showContent(String.format(View.PATH,View.TRAIN_VIEW));
                break;
            case "Commuters" :
                showContent(String.format(View.PATH, View.COMMUTER_VIEW));
                break;
            case "Booking" :
                showContent(String.format(View.PATH, View.RESERVATION_VIEW));
                break;
            case "About":
                //showAbout(String.format(View.PATH, View.ABOUT));
        }
    }

    @FXML
    void logoutBtnEvent(ActionEvent event) {
        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(String.format(View.PATH, View.LOGIN)))));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //------------------------- Addtional Methods ----------------------------//
    public Object showContent(String path)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane pane = fxmlLoader.load(getClass().getResource(path).openStream());

            AnchorPane.setBottomAnchor(pane, 0.0);
            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setTopAnchor(pane, 0.0);
            contentPane.getChildren().setAll(pane);
            return fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showAbout(String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(View.IMAGE_ABOUT));
        stage.initOwner(primaryStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    
    public void updateLoginContent(Officer officer){
        userTypeText.setText("Login As : OFFICER");
        userNameText.setText("Current User : " + officer.getName());
        loginDateText.setText("Date Logged In : " + new SimpleDateFormat("yyyy-MM-dd").format(new Date()));   
        loginTimeText.setText("Time Logged In : " + new SimpleDateFormat("hh:mm:ss a").format(new Date()));       
    }
}
