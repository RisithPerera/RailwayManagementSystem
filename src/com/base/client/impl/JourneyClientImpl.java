package com.base.client.impl;

import com.base.client.JourneyClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Commuter;
import com.model.child.Engine;
import com.model.child.Journey;
import javafx.collections.ObservableList;

import java.sql.*;

public class JourneyClientImpl implements JourneyClient {

    private static JourneyClientImpl journeyClient;
    private static ObservableList<Journey> journeyList;

    private JourneyClientImpl() {
        journeyList = ListConnection.getInstance().getJourneyList();
    }

    public static JourneyClientImpl getInstance() {
        if (journeyClient == null) {
            journeyClient = new JourneyClientImpl();
        }
        return journeyClient;
    }

    @Override
    public boolean add(Journey journey) throws SQLException, ClassNotFoundException {
        if (journey == null) return false;

        String query = "INSERT INTO Journey VALUE (?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);

        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, journey.getDate());
            state.setObject(2, journey.getTime());
            state.setObject(3, journey.getId());
            state.setObject(4, journey.getEngine().getId());
            state.setObject(5, journey.getDepStation().getId());
            state.setObject(6, journey.getDepPlatform());
            state.setObject(7, journey.getDepDate());
            state.setObject(8, journey.getDepTime());
            state.setObject(9, journey.getArrStation().getId());
            state.setObject(10, journey.getArrPlatform());
            state.setObject(11, journey.getArrDate());
            state.setObject(12, journey.getArrTime());

            if(state.executeUpdate()>0){
                journeyList.add(journey);
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;

        }finally{
            conn.setAutoCommit(true);
        }
    }

    @Override
    public boolean update(Journey journey) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Journey search(int id){
        if (id < 0) return null;

        Journey journey = new Journey(id);
        int index = journeyList.indexOf(journey);
            if (index != -1) {
            return journeyList.get(index);
        }
        return null;
    }

    @Override
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        if (id < 0) return false;
        String query = "DELETE FROM Journey WHERE journeyId = ?";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        state.setObject(1, id);
        if (state.executeUpdate() > 0) {
            Journey journey = new Journey(id);
            journeyList.remove(journey);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ObservableList<Journey> getCompletedJourneys() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ObservableList<Journey> getAll() {
        return journeyList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        journeyList.clear();
        String query = "SELECT * FROM Journey";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Journey journey = new Journey();
            journey.setDate(result.getDate("recordDate").toString());
            journey.setTime(result.getTime("recordTime").toString());
            journey.setId(result.getInt("journeyId"));
            journey.setEngine(EngineClientImpl.getInstance().search(result.getInt("engineId")));
            journey.setDepStation(StationClientImpl.getInstance().search(result.getInt("depStation")));
            journey.setDepPlatform(result.getInt("depPlatform"));
            journey.setDepDate(result.getString("depDate"));
            journey.setDepTime(result.getString("depTime"));
            journey.setArrStation(StationClientImpl.getInstance().search(result.getInt("arrStation")));
            journey.setArrPlatform(result.getInt("arrPlatform"));
            journey.setArrDate(result.getString("arrDate"));
            journey.setArrTime(result.getString("arrTime"));

            journeyList.add(journey);

        }
        System.out.println("Journey List Loaded : " + journeyList.size());
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "SELECT journeyId+1 AS nextID FROM Journey ORDER BY 1 DESC LIMIT 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextID");
        }
        return 0;
    }
}
