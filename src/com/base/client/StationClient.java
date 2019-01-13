package com.base.client;

import com.model.child.Station;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface StationClient {
    public ObservableList<Station> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
}
