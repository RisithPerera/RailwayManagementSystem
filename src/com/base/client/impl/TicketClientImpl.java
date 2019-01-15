package com.base.client.impl;

import com.base.client.TicketClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Journey;
import com.model.child.Reservation;
import com.model.child.Ticket;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TicketClientImpl implements TicketClient {

    private static TicketClientImpl ticketClient;
    private ObservableList<Ticket> ticketList;

    private TicketClientImpl(){
        ticketList = ListConnection.getInstance().getTicketList();
    }

    public static TicketClientImpl getInstance() {
        if (ticketClient == null) {
            ticketClient = new TicketClientImpl();
        }
        return ticketClient;
    }

    @Override
    public ObservableList<Ticket> getReservationTickets(Reservation reservation) throws SQLException, ClassNotFoundException {
        ticketList.clear();
        String query = "SELECT * FROM Ticket WHERE reservationId = "+reservation.getId();
        if(reservation == null) return null;

        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Ticket ticket = new Ticket();
            ticket.setId(result.getInt("ticketId"));
            ticket.setReservation(reservation);
            int compartmentId = result.getInt("compartmentId");
            int seatCol = result.getInt("seatCol");
            int seatRow = result.getInt("seatRow");
            ticket.setSeat(SeatClientImpl.getInstance().search(compartmentId,seatCol,seatRow));
            ticket.setPrice(result.getDouble("price"));
            ticket.setType(result.getInt("type"));
            ticketList.add(ticket);

        }
        System.out.println("Compartment List Loaded : " + ticketList.size());
        return ticketList;
    }
}
