package com.base.client.impl;

import com.base.client.CompartmentClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Compartment;
import com.model.child.Engine;
import com.model.child.Seat;
import javafx.collections.ObservableList;

import java.sql.*;

public class CompartmentClientImpl implements CompartmentClient {
    private static CompartmentClientImpl compartmentClient;

    private static ObservableList<Engine> engineList;
    private ObservableList<Compartment> compartmentList;

    private CompartmentClientImpl() {
        engineList = ListConnection.getInstance().getEngineList();
        compartmentList = ListConnection.getInstance().getCompartmentList();
    }

    public static CompartmentClientImpl getInstance() {
        if (compartmentClient == null) {
            compartmentClient = new CompartmentClientImpl();
        }
        return compartmentClient;
    }

    @Override
    public boolean add(Compartment compartment, ObservableList<Seat> seatList) throws SQLException, ClassNotFoundException {
        if (compartment == null) return false;

        String query = "INSERT INTO Compartment VALUE (?,?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);

        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, compartment.getId());
            state.setObject(2, compartment.getEngine().getId());
            state.setObject(3, compartment.getClassType());
            state.setObject(6, compartment.isAvailable());

            if (state.executeUpdate() > 0) {
                if (SeatClientImpl.getInstance().add(seatList)) {
                    conn.commit();
                    compartmentList.add(compartment);
                    return true;
                }
                conn.rollback();
                return false;
            }
            conn.rollback();
            return false;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public Compartment search(int compartmentId) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Compartment WHERE compartmentId = "+compartmentId;
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        if (result.next()) {
            Compartment compartment = new Compartment();

            compartment.setId(result.getInt("compartmentId"));
            compartment.setEngine(EngineClientImpl.getInstance().search(result.getInt("engineId")));
            compartment.setClassType(result.getInt("class"));
            compartment.setAvailable(result.getBoolean("isAvailable"));

            return compartment;
        }
        return null;
    }


    @Override
    public ObservableList<Compartment> getEngineCompartments(Engine engine) throws SQLException, ClassNotFoundException {
        compartmentList.clear();
        String query;
        if(engine == null){
            query = "SELECT * FROM Compartment WHERE engineId IS NULL";
        }else{
            query = "SELECT * FROM Compartment WHERE engineId = "+engine.getId();
        }

        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Compartment compartment = new Compartment();

            compartment.setId(result.getInt("compartmentId"));
            compartment.setEngine(engine);
            compartment.setClassType(result.getInt("class"));
            compartment.setAvailable(result.getBoolean("isAvailable"));
            compartmentList.add(compartment);

        }
        System.out.println("Compartment List Loaded : " + compartmentList.size());
        return compartmentList;
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "SELECT compartmentId+1 AS nextID FROM Compartment ORDER BY 1 DESC LIMIT 1;";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextId");
        }
        return 0;
    }

}
