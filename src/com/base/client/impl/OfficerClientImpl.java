package com.base.client.impl;

import com.base.client.OfficerClient;
import com.base.connection.BaseConnection;
import com.model.child.Officer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OfficerClientImpl implements OfficerClient {

    private static OfficerClientImpl officerClient;

    private OfficerClientImpl(){}

    public static OfficerClientImpl getInstance() {
        if (officerClient == null) {
            officerClient = new OfficerClientImpl();
        }
        return officerClient;
    }

    @Override
    public Officer getOfficer(String officerName, String password) throws SQLException, ClassNotFoundException {
        String query = "SELECT * FROM Officer WHERE officername LIKE '"+officerName+"' AND password LIKE '"+password+"'";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            Officer officer = new Officer();
            officer.setName(result.getString("officerName"));
            officer.setPassword(result.getString("password"));
            return officer;
        }
        return null;
    }
}
