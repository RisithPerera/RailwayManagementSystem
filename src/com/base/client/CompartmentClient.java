package com.base.client;

import com.model.child.Compartment;
import com.model.child.Engine;
import com.model.child.Seat;
import com.model.child.Station;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CompartmentClient {
    public boolean add(Compartment compartment,  ObservableList<Seat> seatList) throws SQLException, ClassNotFoundException;
    public Compartment search(int compartmentId) throws SQLException, ClassNotFoundException;
    public ObservableList<Compartment> getEngineCompartments(Engine engine) throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
