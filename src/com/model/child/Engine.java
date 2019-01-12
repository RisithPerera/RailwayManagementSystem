package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Engine extends SuperModel implements Comparable<Engine> {

    private int    id;
    private String name;
    private boolean isAvailable;

    public Engine() {}

    public Engine(int id) {
        this.id = id;
    }

    public Engine(int id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return  getId()     + Symbol.SPLIT +
                getName()     + Symbol.SPLIT +
                isAvailable();
    }

    @Override
    public int compareTo(Engine dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Engine) {
            return ((Engine)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
