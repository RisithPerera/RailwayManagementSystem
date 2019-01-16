package com.view.ctrl;

import com.base.client.impl.CompartmentClientImpl;
import com.base.client.impl.EngineClientImpl;
import com.manifest.View;
import com.model.child.Commuter;
import com.model.child.Compartment;
import com.model.child.Engine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TrainViewCtrl implements Initializable {

    @FXML
    private TextField searchTrainText;

    @FXML
    private VBox trainVBox;

    private ObservableList<Engine> engines;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        engines = EngineClientImpl.getInstance().getAll();
        createTrains(engines);
        searchTrainText.textProperty().addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldVal, Object newVal) {
                        searchTrain((String)oldVal, (String)newVal);
                    }
                }
        );
    }

    @FXML
    void addNewTrainEvent(ActionEvent event) {
        MainCtrl.getInstance().showContent(String.format(View.PATH, View.TRAIN_ADD));
    }

    @FXML
    void allEngineEvent(ActionEvent event) {
        createTrains(engines);
    }

    @FXML
    void availableEngineEvent(ActionEvent event) {
        ObservableList<Engine> emptyEngines = FXCollections.observableArrayList();
        for(Engine engine : engines){
            if(engine.isAvailable()){
                System.out.println("empty");
                emptyEngines.add(engine);
            }
        }
        createTrains(emptyEngines);
    }

    @FXML
    void emptyEngineEvent(ActionEvent event) {
        try {
            ObservableList<Engine> emptyEngines = FXCollections.observableArrayList();
            for(Engine engine : engines){
                ObservableList<Compartment> engineCompartments = CompartmentClientImpl.getInstance().getEngineCompartments(engine);
                System.out.println(engineCompartments);
                if(engineCompartments.size() == 0){
                    System.out.println("empty");
                    emptyEngines.add(engine);
                }
            }
            createTrains(emptyEngines);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void searchTrain(String oldText, String newText) {
        if ( oldText != null && (newText.length() < oldText.length()) ) {
            createTrains(engines);
        }

        String[] parts = newText.toUpperCase().split(" ");

        ObservableList<Engine> subEngines = FXCollections.observableArrayList();
        for(Engine engine : engines){
            boolean match = true;
            for ( String part: parts ) {
                if ( ! engine.getName().toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }

            if ( match ) {
                subEngines.add(engine);
            }
        }
        createTrains(subEngines);
    }

    private void createTrains(ObservableList<Engine> engines ) {
        try {
            trainVBox.getChildren().clear();
            for (Engine engine: engines) {
                Label engineName = new Label(engine.getName());
                engineName.setPrefHeight(30);
                engineName.setPrefWidth(Double.MAX_VALUE);
                engineName.getStyleClass().add("engineName");
                engineName.setAlignment(Pos.CENTER_LEFT);

                ImageView engineImage = new ImageView(getClass().getResource("/com/view/image/Engine.png").toExternalForm());

                HBox train = new HBox();
                train.getChildren().add(engineImage);
                train.setStyle("-fx-padding: 0 0 0 10;");

                ObservableList<Compartment> engineCompartments = null;

                    engineCompartments = CompartmentClientImpl.getInstance().getEngineCompartments(engine);

                for (Compartment compartment : engineCompartments) {
                    ImageView compartmentImage = new ImageView(getClass().getResource("/com/view/image/Compartment.png").toExternalForm());
                    train.getChildren().add(compartmentImage);
                }

                VBox trainBox = new VBox();
                trainBox.setPrefHeight(100);
                trainBox.setPrefWidth(Double.MAX_VALUE);
                trainBox.setStyle("-fx-background-color: #409EC4;");
                trainBox.getChildren().addAll(engineName,train);
                trainBox.setSpacing(10);

                trainVBox.getChildren().add(trainBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
