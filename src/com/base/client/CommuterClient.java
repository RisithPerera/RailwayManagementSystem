/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.client;

import com.model.child.Commuter;
import com.model.child.Compartment;
import javafx.collections.ObservableList;

import java.sql.SQLException;


/**
 *
 * @author RISITH-PC
 */
public interface CommuterClient {
    public boolean add(Commuter commuter) throws SQLException, ClassNotFoundException;
    public Commuter search(int t);
    public boolean delete(int t) throws SQLException, ClassNotFoundException;
    public ObservableList<Commuter> getAll();
    public void loadAll() throws SQLException, ClassNotFoundException;
    public int getNextId() throws SQLException, ClassNotFoundException;
}
