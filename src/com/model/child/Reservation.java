package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Reservation extends SuperModel implements Comparable<Commuter> {

    private String date;
    private String time;
    private int    id;
    private Commuter commuter;
    private Journey journey;

    public Reservation() {}

    public Reservation(int id) {
        this.id = id;
    }

    public Reservation(String date, String time, int id, Commuter commuter, Journey journey) {
        this.date = date;
        this.time = time;
        this.id = id;
        this.commuter = commuter;
        this.journey = journey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Commuter getCommuter() {
        return commuter;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public void setCommuter(Commuter commuter) {
        this.commuter = commuter;
    }

    //---------------------------- Calculatons -------------------------------------//

    public String getFullName() {
        return this.commuter.getFullName();
    }

    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return  getDate()     + Symbol.SPLIT +
                getTime()     + Symbol.SPLIT +
                getId()       + Symbol.SPLIT +
                getCommuter();
    }

    @Override
    public int compareTo(Commuter dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Commuter) {
            return ((Commuter)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
