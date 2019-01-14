package com.base.client;

import com.model.child.Officer;

import java.sql.SQLException;

public interface OfficerClient {
    public Officer getOfficer(String username, String password) throws SQLException, ClassNotFoundException;
}
