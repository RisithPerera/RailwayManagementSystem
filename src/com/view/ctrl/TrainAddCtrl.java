/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.ctrl;

import com.base.client.impl.*;
import com.manifest.Data;
import com.manifest.Message;
import com.manifest.View;
import com.model.child.*;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TrainAddCtrl implements Initializable {

    @FXML
    private Button engineConfirmBtn;

    @FXML
    private TextField selectedEngineText;

    @FXML
    private ComboBox<Engine> searchEngineCombo;

    @FXML
    private TableView<Compartment> compartmentTable;

    @FXML
    private TableColumn<Compartment, String> idCol;

    @FXML
    private TableColumn<Compartment, String> classCol;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private HBox trainBox;

    private ObservableList<Compartment> compartmentList;
    private ObservableList<Compartment> selectedCompartmentList;
    private Engine selectedEngine = null;
    private boolean isAddedEngine = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        classCol.setCellValueFactory(new PropertyValueFactory<>("classText"));

        try {
            compartmentList = CompartmentClientImpl.getInstance().getEngineCompartments(null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        compartmentTable.getItems().setAll(compartmentList);

        searchEngineCombo.getItems().setAll(EngineClientImpl.getInstance().getAll());

        setTableMenu();
    }

    @FXML
    void addCompartmentBtnEvent(ActionEvent event) {

    }

    @FXML
    void addNewEngineBtnEvent(ActionEvent event) {
        showContent("/com/view/fxml/engineAdd.fxml");
    }

    @FXML
    void cancelBtnEvent(ActionEvent event) {
        MainCtrl.getInstance().showContent(String.format(View.PATH, View.TRAIN_VIEW));
    }

    @FXML
    void deleteCompartmentEvent(ActionEvent event) {
        trainBox.getChildren().clear();
        drawEngine();
        isAddedEngine = false;
    }

    @FXML
    void searchEngineComboEvent(ActionEvent event) {
        selectedEngineText.setText(searchEngineCombo.getSelectionModel().getSelectedItem().getName());
    }

    @FXML
    void confirmEngineBtnEvent(ActionEvent event) {
        if(isAddedEngine){
            engineConfirmBtn.setText("Confirm");
            trainBox.getChildren().clear();
            isAddedEngine = false;
        }else {
            try{
                engineConfirmBtn.setText("Delete");
                selectedEngine = searchEngineCombo.getSelectionModel().getSelectedItem();
                drawEngine();
                isAddedEngine = true;
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void saveBtnEvent(ActionEvent event) {
        System.out.println(selectedEngine);
        MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.ADD, "Train Structure"));
    }

    @FXML
    void viewTrainEvent(ActionEvent event) {
        MainCtrl.getInstance().showContent(String.format(View.PATH, View.TRAIN_VIEW));
    }


    private void drawEngine() {
        ImageView engineImage = new ImageView(getClass().getResource("/com/view/image/Engine.png").toExternalForm());
        trainBox.getChildren().add(engineImage);
        trainBox.setStyle("-fx-padding: 0 0 0 10;");
    }

    private void drawCompartment() {
        ImageView engineImage = new ImageView(getClass().getResource("/com/view/image/Compartment.png").toExternalForm());
        trainBox.getChildren().add(engineImage);
        trainBox.setStyle("-fx-padding: 0 0 0 10;");
    }

    private void setTableMenu() {
        compartmentTable.setRowFactory((TableView<Compartment> tableView) -> {
            TableRow<Compartment> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem addMenu = new MenuItem("Add Compartment");

            addMenu.setOnAction((ActionEvent event) -> {
                drawCompartment();
                Compartment compartment = compartmentTable.getSelectionModel().getSelectedItem();
                System.out.println(compartment);
                //selectedCompartmentList.add(compartment);
            });

            contextMenu.getItems().addAll(addMenu);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row ;
        });
    }

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
}



   