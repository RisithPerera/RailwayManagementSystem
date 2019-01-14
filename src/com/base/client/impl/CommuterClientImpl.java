/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.base.client.impl;

import com.base.client.CommuterClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Commuter;

import java.sql.*;

import com.model.child.Compartment;
import javafx.collections.ObservableList;

public class CommuterClientImpl implements CommuterClient {

    private static CommuterClientImpl customerClient;
    private static ObservableList<Commuter> commuterList;

    private CommuterClientImpl() {
        commuterList = ListConnection.getInstance().getCommuterList();
    }

    public static CommuterClientImpl getInstance() {
        if (customerClient == null) {
            customerClient = new CommuterClientImpl();
        }
        return customerClient;
    }

    @Override
    public boolean add(Commuter commuter) throws SQLException, ClassNotFoundException {
        if (commuter == null) return false;
        String query = "INSERT INTO Commuter VALUE (?,?,?,?,?,?,?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, commuter.getId());
            state.setObject(2, commuter.getFName());
            state.setObject(3, commuter.getLName());
            state.setObject(4, commuter.getStreet());
            state.setObject(5, commuter.getCity());
            state.setObject(6, commuter.getDistrict());
            state.setObject(7, commuter.getPassword());
            state.setObject(8, commuter.getContact());
            state.setObject(9, commuter.getReputation());

            if(state.executeUpdate()>0){
                commuterList.add(commuter);
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
    public boolean delete(int id) throws SQLException, ClassNotFoundException {
        if (id < 0) return false;
        String query = "DELETE FROM Commuter WHERE commuterId = ?";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        state.setObject(1, id);
        if (state.executeUpdate() > 0) {
            Commuter commuter = new Commuter(id);
            commuterList.remove(commuter);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Commuter search(int id) {
        if (id < 0) return null;
        Commuter commuter = new Commuter(id);
        int index = commuterList.indexOf(commuter);
        if (index != -1) {
            return commuterList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<Commuter> getAll() {
        return commuterList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        commuterList.clear();
        String query = "SELECT * FROM Commuter";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Commuter commuter = new Commuter();
            commuter.setId(result.getInt("commuterId"));
            commuter.setFName(result.getString("fName"));
            commuter.setLName(result.getString("lName"));
            commuter.setStreet(result.getString("street"));
            commuter.setCity(result.getString("city"));
            commuter.setDistrict(result.getString("district"));
            commuter.setPassword(result.getString("password"));
            commuter.setContact(result.getString("contact"));
            commuter.setReputation(result.getDouble("reputation"));
            commuterList.add(commuter);

        }
        System.out.println("Commuter List Loaded : " + commuterList.size());
    }


    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "SELECT commuterId+1 AS nextID FROM Commuter ORDER BY 1 DESC LIMIT 1";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextID");
        }
        return 0;
    }
}
