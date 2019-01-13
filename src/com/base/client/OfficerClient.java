package com.base.client;

import java.sql.SQLException;

public interface OfficerClient {
    public boolean checkExists(String username, String password) throws SQLException, ClassNotFoundException;
}
