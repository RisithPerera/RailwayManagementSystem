package com.base.client.impl;

import com.base.client.EngineClient;
import com.base.connection.BaseConnection;
import com.base.list.ListConnection;
import com.model.child.Engine;
import javafx.collections.ObservableList;

import java.sql.*;

public class EngineClientImpl implements EngineClient {

    private static EngineClientImpl engineClient;
    private static ObservableList<Engine> engineList;

    private EngineClientImpl() {
        engineList = ListConnection.getInstance().getEngineList();
    }

    public static EngineClientImpl getInstance() {
        if (engineClient == null) {
            engineClient = new EngineClientImpl();
        }
        return engineClient;
    }

    @Override
    public boolean add(Engine engine) throws SQLException, ClassNotFoundException {
        if (engine == null) return false;
        String query = "INSERT INTO Engine VALUE (?,?,?)";
        Connection conn = BaseConnection.createConnection().getConnection();
        conn.setAutoCommit(false);
        try {
            PreparedStatement state = conn.prepareStatement(query);
            state.setObject(1, engine.getId());
            state.setObject(2, engine.getName());
            state.setObject(3, engine.isAvailable());

            if(state.executeUpdate()>0){
                engineList.add(engine);
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
    public Engine search(int id) {
        if (id < 0) return null;
        Engine engine = new Engine(id);
        int index = engineList.indexOf(engine);
        if (index != -1) {
            return engineList.get(index);
        }
        return null;
    }

    @Override
    public ObservableList<Engine> getAll() {
        return engineList;
    }

    @Override
    public void loadAll() throws SQLException, ClassNotFoundException {
        engineList.clear();
        String query = "SELECT * FROM Engine";
        Connection conn = BaseConnection.createConnection().getConnection();
        Statement state = conn.createStatement();
        ResultSet result = state.executeQuery(query);

        while (result.next()) {
            Engine engine = new Engine();
            engine.setId(result.getInt("engineId"));
            engine.setName(result.getString("engineName"));
            engine.setAvailable(result.getBoolean("isAvailable"));
            engineList.add(engine);

        }
        System.out.println("Engine List Loaded : " + engineList.size());
    }

    @Override
    public int getNextId() throws SQLException, ClassNotFoundException {
        String query = "SELECT engineId+1 AS nextID FROM Engine ORDER BY 1 DESC LIMIT 1;";
        Connection conn = BaseConnection.createConnection().getConnection();
        PreparedStatement state = conn.prepareStatement(query);
        ResultSet result = state.executeQuery();
        if (result.next()) {
            return result.getInt("nextID");
        }
        return 0;
    }
}
