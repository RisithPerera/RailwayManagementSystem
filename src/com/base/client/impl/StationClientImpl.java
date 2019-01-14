package com.base.client.impl;

import com.base.client.SeatClient;
import com.base.client.StationClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.*;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StationClientImpl implements StationClient {

    private static StationClientImpl stationClient;
    private static ObservableList<Station> stationList;

    private StationClientImpl(){
        stationList = ListConnection.getInstance().getStationList();
    }

    public static StationClientImpl getInstance() {
        if (stationClient == null) {
            stationClient = new StationClientImpl();
        }
        return stationClient;
    }

    @Override
    public Station search(int id){
        if (id < 0) return null;

        Station station = new Station(id);
        int index = stationList.indexOf(station);
        if (index != -1) {
            return stationList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<Station> getAll() {
        return stationList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        stationList.clear();
        String query = "SELECT * FROM Station";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Station station = new Station();
            station.setId(result.getInt("stationId"));
            station.setName(result.getString("stationName"));
            stationList.add(station);

        }
        System.out.println("Station List Loaded : " + stationList.size());
    }
}
