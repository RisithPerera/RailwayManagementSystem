package com.base.client.impl;

import com.base.client.SeatClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Compartment;
import com.model.child.Seat;
import javafx.collections.ObservableList;

import java.sql.*;

public class SeatClientImpl implements SeatClient {

    private static SeatClientImpl seatClient;

    private static ObservableList<Seat> seatList;

    private SeatClientImpl(){
        seatList = ListConnection.getInstance().getSeatList();
    }

    public static SeatClientImpl getInstance() {
        if (seatClient == null) {
            seatClient = new SeatClientImpl();
        }
        return seatClient;
    }

    @Override
    public boolean add(ObservableList<Seat> seatList) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Seat VALUE (?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        int count = 0;
        for (Seat seat : seatList) {
            PreparedStatement state = conn.prepareStatement(query);

            state.setObject(1, seat.getCompartment().getId());
            state.setObject(2, seat.getSeatCol());
            state.setObject(3, seat.getSeatRow());
            state.setObject(4, seat.getComfortType());
            state.setObject(5, seat.isAvailbale());

            count += state.executeUpdate();
        }
        return count == seatList.size();
    }

    @Override
    public Seat search(int compartmentId, int seatCol, int seatRow) throws SQLException, ClassNotFoundException {

        String query = "SELECT * FROM Seat WHERE compartmentId = "+compartmentId+" AND seatCol = '"+seatCol+"' AND seatRow = "+seatRow;
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);
        if(result.next()) {
            Seat seat = new Seat();
            seat.setCompartment(CompartmentClientImpl.getInstance().search(compartmentId));
            seat.setSeatCol(seatCol);
            seat.setSeatRow(seatRow);
            seat.setComfortType(result.getInt("comfortType"));
            seat.setAvailbale(result.getBoolean("isAvailable"));
            return seat;
        }
        return null;
    }

    @Override
    public ObservableList<Seat> getCompartmentSeats(Compartment compartment) throws SQLException, ClassNotFoundException {
        if(compartment == null) return null;

        seatList.clear();
        String query = "SELECT * FROM Seat WHERE compartmentId = "+compartment.getId();
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Seat seat = new Seat();
            seat.setCompartment(compartment);
            seat.setSeatCol(result.getInt("seatCol"));
            seat.setSeatRow(result.getInt("seatRow"));
            seat.setComfortType(result.getInt("comfortType"));
            seat.setAvailbale(result.getBoolean("isAvailable"));

            seatList.add(seat);
        }
        System.out.println("Seat List Loaded : " + seatList.size());
        return seatList;
    }
}
