package com.base.client;

import com.model.child.Commuter;
import com.model.child.Compartment;
import com.model.child.Engine;
import com.model.child.Seat;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface SeatClient {
    public boolean add(ObservableList<Seat> seats) throws SQLException, ClassNotFoundException;
    public Seat search(int compartmentId, int seatCol, int seatRow) throws SQLException, ClassNotFoundException;
    public ObservableList<Seat> getCompartmentSeats(Compartment compartment) throws SQLException, ClassNotFoundException;
}
