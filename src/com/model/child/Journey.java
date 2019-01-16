package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Journey extends SuperModel implements Comparable<Journey> {

    private String date;
    private String time;
    private int    id;
    private Engine engine;
    private Station depStation;
    private int depPlatform;
    private String depDate;
    private String depTime;
    private Station arrStation;
    private int arrPlatform;
    private String arrDate;
    private String arrTime;


    public Journey() {}

    public Journey(int id) {
        this.id = id;
    }

    public Journey(String date, String time, int id, Engine engine, Station depStation, int depPlatform, String depDate, String depTime, Station arrStation, int arrPlatform, String arrDate, String arrTime) {
        this.date = date;
        this.time = time;
        this.id = id;
        this.engine = engine;
        this.depStation = depStation;
        this.depPlatform = depPlatform;
        this.depDate = depDate;
        this.depTime = depTime;
        this.arrStation = arrStation;
        this.arrPlatform = arrPlatform;
        this.arrDate = arrDate;
        this.arrTime = arrTime;
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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Station getDepStation() {
        return depStation;
    }

    public void setDepStation(Station depStation) {
        this.depStation = depStation;
    }

    public int getDepPlatform() {
        return depPlatform;
    }

    public void setDepPlatform(int depPlatform) {
        this.depPlatform = depPlatform;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public Station getArrStation() {
        return arrStation;
    }

    public void setArrStation(Station arrStation) {
        this.arrStation = arrStation;
    }

    public int getArrPlatform() {
        return arrPlatform;
    }

    public void setArrPlatform(int arrPlatform) {
        this.arrPlatform = arrPlatform;
    }

    public String getArrDate() {
        return arrDate;
    }

    public void setArrDate(String arrDate) {
        this.arrDate = arrDate;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    //---------------------------- Calculatons -------------------------------------//

    public String getDateTime() {
        return this.date +",\n"+ this.time;
    }

    public String getTrainName() {
        return this.engine.getName();
    }

    public String getDepDetails() {
        return this.depStation.getName() +"\nPlatform: "+ this.depPlatform+"\n"+ this.depDate+" : "+ this.depTime;
    }

    public String getArrDetails() {
        return this.arrStation.getName() +"\nPlatform: "+ this.arrPlatform+"\n"+ this.arrDate+" : "+ this.arrTime;
    }

    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return  getDate()        + Symbol.SPLIT +
                getTime()        + Symbol.SPLIT +
                getEngine().getName()      + Symbol.SPLIT +
                getDepStation().getName()  + Symbol.SPLIT +
                getDepPlatform() + Symbol.SPLIT +
                getDepDate()     + Symbol.SPLIT +
                getArrStation().getName()  + Symbol.SPLIT +
                getArrPlatform() + Symbol.SPLIT +
                getArrDate();
    }

    @Override
    public int compareTo(Journey dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Journey) {
            return ((Journey)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
