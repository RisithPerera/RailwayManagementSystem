/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.list;


import com.model.child.*;
import com.model.child.Commuter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author risit
 */
public class ListConnection {

    private static ListConnection listConnection;

    private final ObservableList<Commuter> commuterList;
    private final ObservableList<Compartment> compartmentList;
    private final ObservableList<Engine> engineList;
    private final ObservableList<Journey> journeyList;
    private final ObservableList<Officer> officerList;
    private final ObservableList<Reservation> reservationList;
    private final ObservableList<Seat> seatList;
    private final ObservableList<Station> stationList;
    private final ObservableList<Ticket> ticketList;

    private ListConnection() {
        this.commuterList = FXCollections.observableArrayList();
        this.compartmentList = FXCollections.observableArrayList();
        this.engineList = FXCollections.observableArrayList();
        this.journeyList = FXCollections.observableArrayList();
        this.officerList = FXCollections.observableArrayList();
        this.reservationList = FXCollections.observableArrayList();
        this.seatList = FXCollections.observableArrayList();
        this.stationList = FXCollections.observableArrayList();
        this.ticketList = FXCollections.observableArrayList();

    }

    public static ListConnection getInstance() {
        if (listConnection == null) {
            listConnection = new ListConnection();
        }
        return listConnection;
    }

    public ObservableList<Commuter> getCommuterList() {
        return commuterList;
    }

    public ObservableList<Compartment> getCompartmentList() {
        return compartmentList;
    }

    public ObservableList<Engine> getEngineList() {
        return engineList;
    }

    public ObservableList<Journey> getJourneyList() {
        return journeyList;
    }

    public ObservableList<Officer> getOfficerList() {
        return officerList;
    }

    public ObservableList<Reservation> getReservationList() {
        return reservationList;
    }

    public ObservableList<Seat> getSeatList() {
        return seatList;
    }

    public ObservableList<Station> getStationList() {
        return stationList;
    }

    public ObservableList<Ticket> getTicketList() {
        return ticketList;
    }
}
