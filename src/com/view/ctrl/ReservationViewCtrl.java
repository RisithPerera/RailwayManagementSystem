package com.view.ctrl;

import com.base.client.impl.CompartmentClientImpl;
import com.base.client.impl.ReservationClientImpl;
import com.base.client.impl.SeatClientImpl;
import com.base.client.impl.TicketClientImpl;
import com.manifest.Data;
import com.model.child.Compartment;
import com.model.child.Reservation;
import com.model.child.Seat;
import com.model.child.Ticket;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ReservationViewCtrl implements Initializable {
    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, String> dateTimeCol, idCol, nameCol;

    @FXML
    private Label nameLabel, addressLabel, ticketLabel, trainLabel, departureLabel, depPlatformLabel, depTimeLabel, arrivalLabel, arrPlatformLabel,arrTimeLabel;

    @FXML
    private VBox compartmentVBox;

    @FXML
    private FlowPane ticketFlowPane;

    @FXML
    private ComboBox<String> yearCombo, monthCombo, dayCombo;

    @FXML
    private TextField searchTrainText,searchCommuterText;

    private ObservableList<Reservation> reservationList;
    private GridPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        yearCombo.getItems().setAll(Data.YEARS);
        monthCombo.getItems().setAll(Data.MONTHS);
        dayCombo.getItems().setAll(Data.DAYS);

        yearCombo.getSelectionModel().select(null);
        monthCombo.setDisable(true);
        monthCombo.getSelectionModel().select(null);
        dayCombo.setDisable(true);
        dayCombo.getSelectionModel().select(null);

        //createCompartment();

        reservationList = ReservationClientImpl.getInstance().getAll();
        searchTrainText.textProperty().addListener(
                (ChangeListener) (observable, oldVal, newVal) -> updateTableDataByTrain((String) oldVal, (String) newVal)
        );

        searchCommuterText.textProperty().addListener(
                (ChangeListener) (observable, oldVal, newVal) -> updateTableDataByCommuter((String) oldVal, (String) newVal)
        );

        setTableSelection();
    }

    @FXML
    void viewAllBtnEvent(ActionEvent event) {
        reservationTable.getItems().setAll(reservationList);
        reservationTable.getSelectionModel().clearSelection();
        yearCombo.getSelectionModel().select(null);
        monthCombo.getSelectionModel().select(null);
        monthCombo.setDisable(true);
        dayCombo.getSelectionModel().select(null);
        dayCombo.setDisable(true);
    }

    @FXML
    void yearComboEvent(ActionEvent event) {
        monthCombo.getSelectionModel().select(null);
        monthCombo.setDisable(false);
        dayCombo.getSelectionModel().select(null);
        dayCombo.setDisable(true);
        String dateCompare = yearCombo.getSelectionModel().getSelectedItem();
        updateTableDataByDate(dateCompare);
    }

    @FXML
    void monthComboEvent(ActionEvent event) {
        dayCombo.getSelectionModel().select(null);
        dayCombo.setDisable(false);

        String dateCompare = yearCombo.getSelectionModel().getSelectedItem()+"-"+
                String.format("%02d", monthCombo.getSelectionModel().getSelectedIndex()+1);
        if(monthCombo.getSelectionModel().getSelectedIndex()+1 > 0){
            updateTableDataByDate(dateCompare);
        }
    }

    @FXML
    void dayComboEvent(ActionEvent event) {
        String dateCompare = yearCombo.getSelectionModel().getSelectedItem()+"-"+
                String.format("%02d", monthCombo.getSelectionModel().getSelectedIndex()+1)+"-"+
                dayCombo.getSelectionModel().getSelectedItem();

        updateTableDataByDate(dateCompare);
    }

    @FXML
    void newBtnEvent(ActionEvent event) {

    }

    @FXML
    void pastBtnEvent(ActionEvent event) {

    }


    private void updateTableDataByCommuter(String oldText, String newText) {
        if ( oldText != null && (newText.length() < oldText.length()) ) {
            reservationTable.getItems().setAll(reservationList);
        }

        String[] parts = newText.toUpperCase().split(" ");

        ObservableList<Reservation> subentries = FXCollections.observableArrayList();
        for(Reservation entry: reservationTable.getItems()){
            boolean match = true;
            for ( String part: parts ) {
                if ( ! entry.getCommuter().getFullName().toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }

            if ( match ) {
                subentries.add(entry);
            }
        }
        reservationTable.getItems().setAll(subentries);
    }

    private void updateTableDataByTrain(String oldText, String newText) {
        if ( oldText != null && (newText.length() < oldText.length()) ) {
            reservationTable.getItems().setAll(reservationList);
        }

        String[] parts = newText.toUpperCase().split(" ");

        ObservableList<Reservation> subentries = FXCollections.observableArrayList();
        for(Reservation entry: reservationTable.getItems()){
            boolean match = true;
            for ( String part: parts ) {
                if ( ! entry.getJourney().getEngine().getName().toUpperCase().contains(part) ) {
                    match = false;
                    break;
                }
            }

            if ( match ) {
                subentries.add(entry);
            }
        }
        reservationTable.getItems().setAll(subentries);
    }

    private void setTableSelection() {
        reservationTable.getItems().setAll(reservationList);
        reservationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && newSelection != oldSelection) {
                nameLabel.setText(": "+newSelection.getCommuter().getFullName());
                addressLabel.setText(": "+newSelection.getCommuter().getAddress());
                ticketLabel.setText(": 2 Full 1 Half");
                trainLabel.setText(": "+newSelection.getJourney().getEngine().getName());
                departureLabel.setText(": "+newSelection.getJourney().getDepStation().getName());
                depPlatformLabel.setText(": "+Integer.toString(newSelection.getJourney().getDepPlatform()));
                depTimeLabel.setText(": "+newSelection.getJourney().getDepDate()+":"+newSelection.getJourney().getDepTime());
                arrivalLabel.setText(": "+newSelection.getJourney().getArrStation().getName());
                arrPlatformLabel.setText(": "+Integer.toString(newSelection.getJourney().getArrPlatform()));
                arrTimeLabel.setText(": "+newSelection.getJourney().getDepDate()+":"+newSelection.getJourney().getDepTime());

                try {
                    ObservableList<Ticket> reservationTickets = TicketClientImpl.getInstance().getReservationTickets(newSelection);
                    createTickets(reservationTickets);
                    createCompartments(reservationTickets);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void updateTableDataByDate(String dateCompare){
        try{
            reservationTable.getItems().setAll(reservationList);
            Iterator<Reservation> iterator = reservationTable.getItems().iterator();
            while(iterator.hasNext()){
                if(! iterator.next().getDate().contains(dateCompare)){
                    iterator.remove();
                }
            }
        }catch(NullPointerException exception){}
    }

    private void createCompartments(ObservableList<Ticket> reservationTickets){
        compartmentVBox.getChildren().clear();
        if(reservationTickets == null) return;

        try {
            Ticket ticket = reservationTickets.get(0);
            Compartment compartment = ticket.getSeat().getCompartment();
            ObservableList<Seat> compartmentSeats = SeatClientImpl.getInstance().getCompartmentSeats(compartment);

            grid = new GridPane();
            for (int i = 0; i < Data.COMPARTMENT_SEAT_ROW_COUNT; i++)grid.getColumnConstraints().add(new ColumnConstraints(45));
            for (int i = 0; i < Data.COMPARTMENT_SEAT_COL_COUNT; i++) grid.getRowConstraints().add(new RowConstraints(28));
            int index = 0;
            grid.getStyleClass().add("compartment");
            for (int i = 0; i < Data.COMPARTMENT_SEAT_ROW_COUNT; i++) {
                for (int j = 0; j < Data.COMPARTMENT_SEAT_COL_COUNT; j++) {
                    Label seatLabel = new Label(Character.toString((char) (j+65))+":"+(i+1));
                    Seat seat = compartmentSeats.get(index);
                    seatLabel.getStyleClass().add("compartmentSeat");

                    if(seat.isAvailbale()) seatLabel.setStyle("-fx-background-color: #97959c");

                    for(Ticket t : reservationTickets){
                        if((i+1) == t.getSeat().getSeatRow() &&  (j+1) == t.getSeat().getSeatCol()){
                            seatLabel.setStyle("-fx-background-color: #ff0000");
                        }
                    }
                    seatLabel.setMaxHeight(Double.MAX_VALUE);
                    seatLabel.setMaxWidth(Double.MAX_VALUE);
                    seatLabel.setAlignment(Pos.CENTER);
                    grid.add(seatLabel, i, j);
                    index++;
                }
            }
            VBox.setVgrow(grid,Priority.ALWAYS);
            grid.setVgap(6);
            grid.setHgap(5);
            grid.setPrefSize(Double.MAX_VALUE,150);
            compartmentVBox.getChildren().add(grid);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTickets(ObservableList<Ticket> reservationTickets) {
        ticketFlowPane.getChildren().clear();
        if(reservationTickets == null) return;

        for (Ticket ticket : reservationTickets) {
            String journey = ticket.getReservation().getJourney().getDepStation().getName() + "-" + ticket.getReservation().getJourney().getArrStation().getName();
            Label journeyLabel = new Label(journey);
            journeyLabel.setPrefHeight(25);
            journeyLabel.setPrefWidth(Double.MAX_VALUE);
            journeyLabel.setStyle("-fx-background-color: #3B377E; -fx-text-fill: #ffffff;");
            journeyLabel.setAlignment(Pos.CENTER);

            Label seatLabel = new Label("Seat Number : " + ticket.getSeat().getId());
            seatLabel.setPrefHeight(25);
            seatLabel.setPrefWidth(Double.MAX_VALUE);

            String type = ticket.getType() == 0 ? "Full" : "Half";
            Label typeLabel = new Label("Ticket Type : " + type);
            typeLabel.setPrefHeight(25);
            typeLabel.setPrefWidth(Double.MAX_VALUE);

            String price = Double.toString(ticket.getPrice());
            Label priceLabel = new Label("Price : " + price);
            priceLabel.setPrefHeight(25);
            priceLabel.setPrefWidth(Double.MAX_VALUE);
            priceLabel.setAlignment(Pos.CENTER_RIGHT);

            VBox ticketBox = new VBox();
            ticketBox.setPrefHeight(100);
            ticketBox.setPrefWidth(150);
            ticketBox.setStyle("-fx-background-color: #409EC4;  -fx-padding: 5 5 5 5;");
            ticketBox.getChildren().addAll(journeyLabel, seatLabel, typeLabel, priceLabel);

            ticketFlowPane.getChildren().add(ticketBox);
        }
    }

}
