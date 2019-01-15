/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;


import com.model.child.Commuter;
import com.model.child.Journey;
import com.model.child.Reservation;
import javafx.collections.ObservableList;
import java.sql.SQLException;


/**
 *
 * @author RISITH-PC
 */

public interface ReservationClient{
    public ObservableList<Reservation> getJourneyReservation(Journey journey) throws SQLException, ClassNotFoundException;
    public ObservableList<Reservation> getCommuterReservation(Commuter commuter) throws SQLException, ClassNotFoundException;
    public ObservableList<Reservation> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
}
