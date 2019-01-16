package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Compartment extends SuperModel implements Comparable<Compartment> {

    private int id;
    private Engine engine;
    private int classType;
    private boolean isAvailable;

    public Compartment() {}

    public Compartment(int id) {
        this.id = id;
    }

    public Compartment(int id, Engine engine, int classType,boolean isAvailable) {
        this.id = id;
        this.engine = engine;
        this.classType = classType;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    //---------------------------- Calculatons -------------------------------------//

    public String getIdText() {
        return Integer.toString(this.id);
    }

    public String getClassText() {
        switch (this.classType){
            case 1:
                return "First Class";
            case 2:
                return "Second Class";
            case 3:
                return "Third Class";
            default:
                return "Normal";
        }
    }

    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return  getId()        + Symbol.SPLIT +
                getEngine()    + Symbol.SPLIT +
                getClassType() + Symbol.SPLIT +
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
