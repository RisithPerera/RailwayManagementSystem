package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Officer extends SuperModel implements Comparable<Officer> {

    private int id;
    private String name;
    private String password;

    public Officer() {}

    public Officer(int id) {
        this.id = id;
    }

    public Officer(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return  getId()     + Symbol.SPLIT +
                getName()     + Symbol.SPLIT +
                getPassword();
    }

    @Override
    public int compareTo(Officer dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Officer) {
            return ((Officer)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}