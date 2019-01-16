package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Station extends SuperModel implements Comparable<Station> {

    private int    id;
    private String name;

    public Station() {}

    public Station(int id) {
        this.id = id;
    }

    public Station(int id, String name) {
        this.id = id;
        this.name = name;
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

    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(Station dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Station) {
            return ((Station)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
