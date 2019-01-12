package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Compartment extends SuperModel implements Comparable<Compartment> {

    private int id;
    private Engine engine;
    private int classType;
    private int numCol;
    private int numRow;
    private boolean isAvailable;

    public Compartment() {}

    public Compartment(int id) {
        this.id = id;
    }

    public Compartment(int id, Engine engine, int classType, int numCol, int numRow, boolean isAvailable) {
        this.id = id;
        this.engine = engine;
        this.classType = classType;
        this.numCol = numCol;
        this.numRow = numRow;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }

    public int getNumCol() {
        return numCol;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
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
                getEngine()     + Symbol.SPLIT +
                getClassType()       + Symbol.SPLIT +
                getNumCol()       + Symbol.SPLIT +
                getNumRow()       + Symbol.SPLIT +
                isAvailable();
    }

    @Override
    public int compareTo(Compartment dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Compartment) {
            return ((Compartment)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
