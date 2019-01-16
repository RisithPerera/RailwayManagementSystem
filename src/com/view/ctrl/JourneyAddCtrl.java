/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.ctrl;

import com.base.client.impl.CompartmentClientImpl;
import com.base.client.impl.EngineClientImpl;
import com.base.client.impl.JourneyClientImpl;
import com.base.client.impl.StationClientImpl;
import com.manifest.Data;
import com.manifest.Message;
import com.manifest.View;
import com.model.child.Compartment;
import com.model.child.Engine;
import com.model.child.Journey;
import com.model.child.Station;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class JourneyAddCtrl implements Initializable {

    @FXML
    private TextField trainNameText;

    @FXML
    private TextField depPlatformText, depMinuteText;

    @FXML
    private ComboBox<Station> depStationCombo,arrStationCombo;

    @FXML
    private ComboBox<String> depYearCombo,depMonthCombo,depDayCombo,depHourCombo;

    @FXML
    private TextField arrPlatformText, arrMinuteText;

    @FXML
    private ComboBox<String> arrYearCombo,arrMonthCombo,arrDayCombo,arrHourCombo;

    @FXML
    private Button cancelBtn;

    @FXML
    private VBox trainVBox;

    private ObservableList<Engine> engines;
    private Engine selectedEngine = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        depStationCombo.getItems().setAll(StationClientImpl.getInstance().getAll());
        depYearCombo.getItems().setAll(Data.YEARS);
        depMonthCombo.getItems().setAll(Data.MONTHS);
        depDayCombo.getItems().setAll(Data.DAYS);
        depHourCombo.getItems().setAll(Data.HOURS);

        arrStationCombo.getItems().setAll(StationClientImpl.getInstance().getAll());
        arrYearCombo.getItems().setAll(Data.YEARS);
        arrMonthCombo.getItems().setAll(Data.MONTHS);
        arrDayCombo.getItems().setAll(Data.DAYS);
        arrHourCombo.getItems().setAll(Data.HOURS);

        engines = EngineClientImpl.getInstance().getAll();
        createAvailableTrains(engines);
    }

    @FXML
    void cancelBtnEvent(ActionEvent event) {
        MainCtrl.getInstance().showContent(String.format(View.PATH, View.JOURNEY_ADD));
    }

    @FXML
    void saveBtnEvent(ActionEvent event) {
        try {
            Journey journey = new Journey();
            journey.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            journey.setTime(new SimpleDateFormat("hh:mm:ss").format(new Date()));
            journey.setId(JourneyClientImpl.getInstance().getNextId());
            journey.setEngine(selectedEngine);

            journey.setDepStation(depStationCombo.getSelectionModel().getSelectedItem());
            journey.setDepPlatform(Integer.parseInt(depPlatformText.getText()));
            String date = depYearCombo.getSelectionModel().getSelectedItem()+"-"+depMonthCombo.getSelectionModel().getSelectedItem()+"-"+depDayCombo.getSelectionModel().getSelectedItem();
            journey.setDepDate(date);
            String time = depHourCombo.getSelectionModel().getSelectedItem()+":"+depMinuteText.getText()+":00";
            journey.setDepTime(time);

            journey.setArrStation(arrStationCombo.getSelectionModel().getSelectedItem());
            journey.setArrPlatform(Integer.parseInt(arrPlatformText.getText()));
            date = arrYearCombo.getSelectionModel().getSelectedItem()+"-"+arrMonthCombo.getSelectionModel().getSelectedItem()+"-"+arrDayCombo.getSelectionModel().getSelectedItem();
            journey.setArrDate(date);
            time = arrHourCombo.getSelectionModel().getSelectedItem()+":"+arrMinuteText.getText()+":00";
            journey.setArrTime(time);

            if(JourneyClientImpl.getInstance().add(journey)){
                MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.ADD, Data.JOURNEY));
                MainCtrl.getInstance().showContent(String.format(View.PATH, View.JOURNEY_VIEW));
            }else {
                MessageBoxViewCtrl.display(Message.TITLE,String.format(Message.UNSUCESS, Data.JOURNEY));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void viewJourneyEvent(ActionEvent event) {
        MainCtrl.getInstance().showContent(String.format(View.PATH, View.JOURNEY_ADD));
    }

    private void createAvailableTrains(ObservableList<Engine> engines ) {
        ObservableList<Engine> emptyEngines = FXCollections.observableArrayList();
        for(Engine engine : engines){
            if(engine.isAvailable()){
                System.out.println("empty");
                emptyEngines.add(engine);
            }
        }

        try {
            trainVBox.getChildren().clear();
            for (Engine engine: emptyEngines) {
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
                trainBox.getStyleClass().add("trainBox");
                trainBox.getChildren().addAll(engineName,train);
                trainBox.setSpacing(10);
                trainBox.setId(Integer.toString(engine.getId()));
                trainBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        selectedEngine = EngineClientImpl.getInstance().search(Integer.parseInt(trainBox.getId()));
                        trainNameText.setText(selectedEngine.getName());
                    }
                });
                trainVBox.getChildren().add(trainBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}



   