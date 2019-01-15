package com.base.client.impl;

import com.base.client.ReservationClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Commuter;
import com.model.child.Journey;
import com.model.child.Reservation;
import com.model.child.Seat;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationClientImpl implements ReservationClient {

    private static ReservationClientImpl reservationClient;

    private ObservableList<Reservation> reservationList;

    private ReservationClientImpl(){
        reservationList = ListConnection.getInstance().getReservationList();
    }

    public static ReservationClientImpl getInstance() {
        if (reservationClient == null) {
            reservationClient = new ReservationClientImpl();
        }
        return reservationClient;
    }

    @Override
    public ObservableList<Reservation> getJourneyReservation(Journey journey) throws SQLException, ClassNotFoundException {
        if(journey == null) return null;

        reservationList.clear();
        String query = "SELECT * FROM Reservation WHERE journeyId = "+journey.getId();
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);
        while (result.next()) {
            Reservation reservation = new Reservation();
            reservation.setDate(result.getString("recordDate"));
            reservation.setTime(result.getString("recordTime"));
            reservation.setId(result.getInt("reservationId"));
            reservation.setCommuter(CommuterClientImpl.getInstance().search(result.getInt("commuterId")));
            reservation.setJourney(journey);
            reservationList.add(reservation);
        }
        System.out.println("Reservation List Loaded : " + reservationList.size());
        return reservationList;
    }

    @Override
    public ObservableList<Reservation> getCommuterReservation(Commuter commuter) throws SQLException, ClassNotFoundException {
        if(commuter == null) return null;

        reservationList.clear();
        String query = "SELECT * FROM Reservation WHERE commuterId = "+commuter.getId();
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);
        while (result.next()) {
            Reservation reservation = new Reservation();
            reservation.setDate(result.getString("recordDate"));
            reservation.setTime(result.getString("recordTime"));
            reservation.setId(result.getInt("reservationId"));
            reservation.setCommuter(commuter);
            reservation.setJourney(JourneyClientImpl.getInstance().search(result.getInt("journeyId")));
            reservationList.add(reservation);
        }
        System.out.println("Reservation List Loaded : " + reservationList.size());
        return reservationList;
    }

    @Override
    public ObservableList<Reservation> getAll() {
        return reservationList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        reservationList.clear();
        String query = "SELECT * FROM Reservation";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Reservation reservation = new Reservation();
            reservation.setDate(result.getString("recordDate"));
            reservation.setTime(result.getString("recordTime"));
            reservation.setId(result.getInt("reservationId"));
            reservation.setCommuter(CommuterClientImpl.getInstance().search(result.getInt("commuterId")));
            reservation.setJourney(JourneyClientImpl.getInstance().search(result.getInt("journeyId")));
            reservationList.add(reservation);
        }
        System.out.println("Reservation List Loaded : " + reservationList.size());
    }
}
