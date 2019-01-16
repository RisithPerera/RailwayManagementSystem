package com.view.ctrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EngineAddCtrl implements Initializable {

    @FXML
    private TextField engineNameText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void addnewEngineBtnEvent(ActionEvent event) {
        System.out.println("hello");
    }

    @FXML
    void cancelEngineBtnEvent(ActionEvent event) {

    }

}
