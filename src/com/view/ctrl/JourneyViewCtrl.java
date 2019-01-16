/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.ctrl;

import com.base.client.impl.CommuterClientImpl;
import com.base.client.impl.JourneyClientImpl;
import com.manifest.Data;
import com.manifest.Message;
import com.manifest.View;
import com.model.child.Commuter;
import com.model.child.Journey;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author RISITH-PC
 */
public class JourneyViewCtrl implements Initializable {




    @FXML
    private TableView<Journey> journeyTable;
    
    @FXML
    private TableColumn<Journey, String> dataTimeCol,  trainCol, departureCol, arrivalCol;

    @FXML
    private TextField searchJourneyText;
   
    private ObservableList<Journey> journeyList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dataTimeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        trainCol.setCellValueFactory(new PropertyValueFactory<>("trainName"));
        departureCol.setCellValueFactory(new PropertyValueFactory<>("depDetails"));
        arrivalCol.setCellValueFactory(new PropertyValueFactory<>("arrDetails"));


        journeyList = JourneyClientImpl.getInstance().getAll();
        journeyTable.getItems().setAll(journeyList);
            // TODO

        searchJourneyText.textProperty().addListener(
            new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldVal, Object newVal) {
                    searchJourney((String)oldVal, (String)newVal);
                }
            }
        );
        
        setTableMenu();
    }

    @FXML
    void addNewJourneyEvent(ActionEvent event) {

        MainCtrl.getInstance().showContent(String.format(View.PATH, View.JOURNEY_ADD));

    }


    private void searchJourney(String oldText, String newText) {
        if ( oldText != null && (newText.length() < oldText.length()) ) {
            journeyTable.getItems().setAll(journeyList);
        }
         
        String[] parts = newText.toUpperCase().split(" ");

        ObservableList<Journey> subentries = FXCollections.observableArrayList();
        for(Journey entry: journeyTable.getItems()){
            boolean match = true;
            for ( String part: parts ) {
                if ( ! entry.toString().toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }
            
            if ( match ) {
                subentries.add(entry);
            }
        }
        journeyTable.getItems().setAll(subentries);
    }   
    
    private void setTableMenu() {
        journeyTable.setRowFactory((TableView<Journey> tableView) -> {
            TableRow<Journey> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem deleteMenu = new MenuItem("Delete Journey");

            deleteMenu.setOnAction((ActionEvent event) -> {                         
                try {
                    Journey selectedJourney = journeyTable.getSelectionModel().getSelectedItem();
                    if (JourneyClientImpl.getInstance().delete(selectedJourney.getId())) {
                        MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.DELETE, Data.JOURNEY));
                    }else{
                        MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.UNSUCESS, Data.JOURNEY));
                    }
                    journeyTable.getItems().setAll(JourneyClientImpl.getInstance().getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            contextMenu.getItems().addAll(deleteMenu);

            row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
                );
                return row ;
            });
    }
}



   