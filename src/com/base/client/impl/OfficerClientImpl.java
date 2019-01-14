package com.base.client.impl;

import com.base.client.OfficerClient;
import com.base.connection.BaseConnection;

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
    public boolean checkExists(String username, String password) throws SQLException, ClassNotFoundException {
        String query = "SELECT count(*) FROM officer WHERE officername = ? AND password = ?";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            if(result.getInt("nextID")==1){
                return true;
            }
        }
        return false;
    }
}
