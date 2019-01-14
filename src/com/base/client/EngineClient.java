package com.base.client;

import com.model.child.Commuter;
import com.model.child.Engine;
import com.model.child.Station;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface EngineClient {
    public boolean add(Engine engine) throws SQLException, ClassNotFoundException;
    public Engine search(int t);
    public ObservableList<Engine> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
