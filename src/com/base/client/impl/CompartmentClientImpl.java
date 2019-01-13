package com.base.client.impl;

import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Commuter;
import com.model.child.Compartment;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompartmentClientImpl {
    private static CompartmentClientImpl compartmentClient;
    private static ObservableList<Compartment> compartmentList;

    private CompartmentClientImpl() {
        compartmentList = ListConnection.getInstance().getCompartmentList();
    }

    public static CompartmentClientImpl getInstance() {
        if (compartmentClient == null) {
            compartmentClient = new CompartmentClientImpl();
        }
        return compartmentClient;
    }

    @Override
    public boolean add(Commuter commuter) throws SQLException, ClassNotFoundException {
        if (commuter == null) return false;
        String query = "INSERT INTO Commuter VALUE (?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(3, commuter.getId());
            state.setObject(4, commuter.getFName());
            state.setObject(5, commuter.getLName());
            state.setObject(6, commuter.getStreet());
            state.setObject(7, commuter.getCity());
            state.setObject(8, commuter.getDistrict());
            state.setObject(9, commuter.getPassword());
            state.setObject(10, commuter.getContact());
            state.setObject(11, commuter.getReputation());

            if(state.executeUpdate()>0){
                compartments.add(commuter);
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;

        }finally{
            conn.setAutoCommit(true);
        }
    }

}
