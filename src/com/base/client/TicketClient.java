package com.base.client;

import com.model.child.Reservation;
import com.model.child.Ticket;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface TicketClient {
    public ObservableList<Ticket> getReservationTickets(Reservation reservation) throws SQLException, ClassNotFoundException;
}
