package com.base.client;

import com.model.child.Commuter;
import com.model.child.Journey;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface JourneyClient {
    public boolean add(Journey journey) throws SQLException, ClassNotFoundException;
    public boolean update(Journey journey) throws SQLException, ClassNotFoundException;
    public Journey search(int t);
    public boolean delete(int t) throws SQLException, ClassNotFoundException;
    public ObservableList<Journey> getCompletedJourneys() throws SQLException, ClassNotFoundException;
    public ObservableList<Journey> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
