/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.ctrl;

import com.base.client.impl.CommuterClientImpl;
import com.manifest.Data;
import com.manifest.Message;
import com.manifest.View;
import com.model.child.Commuter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author RISITH-PC
 */
public class CommuterViewCtrl implements Initializable {

    @FXML
    private TableView<Commuter> commuterTable;
    
    @FXML
    private TableColumn<Commuter, String> idCol,  nameCol, addressCol, contactsCol, pointsCol;

    @FXML
    private TextField searchCommuterText;
   
    private ObservableList<Commuter> commuterList;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        contactsCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        pointsCol.setCellValueFactory(new PropertyValueFactory<>("reputation"));

        commuterList = CommuterClientImpl.getInstance().getAll();
        commuterTable.getItems().setAll(commuterList);
            // TODO

        
        searchCommuterText.textProperty().addListener(
            new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldVal, Object newVal) {
                    searchCommuter((String)oldVal, (String)newVal);
                }
            }
        );
        
        setTableMenu();
    }    

    private void searchCommuter(String oldText, String newText) {
        if ( oldText != null && (newText.length() < oldText.length()) ) {
            commuterTable.getItems().setAll(commuterList);
        }
         
        String[] parts = newText.toUpperCase().split(" ");

        ObservableList<Commuter> subentries = FXCollections.observableArrayList();
        for(Commuter entry: commuterTable.getItems()){
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
        commuterTable.getItems().setAll(subentries);
    }   
    
    private void setTableMenu() {
        commuterTable.setRowFactory((TableView<Commuter> tableView) -> {
            TableRow<Commuter> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem viewMenu = new MenuItem("View Reservations");
            MenuItem deleteMenu = new MenuItem("Delete Commuter");

            /*
            viewMenu.setOnAction((ActionEvent event) -> {                         
                try{    
                    Commuter selectedCommuter = commuterTable.getSelectionModel().getSelectedItem();
                    //CustomerOrderViewCtrl ctrl = (CustomerOrderViewCtrl) MainCtrl.getInstance().showContent(String.format(View.PATH, View.RESERVATION_VIEW));
                    //ctrl.updateTableDataByCustomer(selectedCommuter);
                } catch (IOException ex) {
                    Logger.getLogger(CommuterViewCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            */

            deleteMenu.setOnAction((ActionEvent event) -> {                         
                try {
                    Commuter selectedCommuter = commuterTable.getSelectionModel().getSelectedItem();
                    if (CommuterClientImpl.getInstance().delete(selectedCommuter.getId())) {
                        MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.DELETE, Data.COMMUTER));
                    }else{
                        MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.UNSUCESS, Data.COMMUTER));
                    }
                    commuterTable.getItems().setAll(CommuterClientImpl.getInstance().getAll());
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            contextMenu.getItems().addAll(viewMenu,deleteMenu);

            row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                        .then((ContextMenu)null)
                        .otherwise(contextMenu)
                );
                return row ;
            });
    }
    
}



   