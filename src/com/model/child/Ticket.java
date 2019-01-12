package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Ticket extends SuperModel implements Comparable<Ticket> {
    private int id;
    private Reservation reservation;
    private Journey journey;
    private char seatCol;
    private int seatRow;
    private Compartment compartment;
    private double price;
    private int type;

    public Ticket() {}

    public Ticket(int id) {
        this.id = id;
    }

    public Ticket(int id, Reservation reservation, Journey journey, char seatCol, int seatRow, Compartment compartment, double price, int type) {
        this.id = id;
        this.reservation = reservation;
        this.journey = journey;
        this.seatCol = seatCol;
        this.seatRow = seatRow;
        this.compartment = compartment;
        this.price = price;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public char getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(char seatCol) {
        this.seatCol = seatCol;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public Compartment getCompartment() {
        return compartment;
    }

    public void setCompartment(Compartment compartment) {
        this.compartment = compartment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return  getId()          + Symbol.SPLIT +
                getReservation() + Symbol.SPLIT +
                getJourney()     + Symbol.SPLIT +
                getSeatCol()     + Symbol.SPLIT +
                getSeatRow()     + Symbol.SPLIT +
                getCompartment() + Symbol.SPLIT +
                getPrice()       + Symbol.SPLIT +
                getType();
    }

    @Override
    public int compareTo(Ticket dto) {
        boolean logic = dto.getId() > this.getId();
        return  logic ? -1 : !logic ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ticket) {
            return ((Ticket)obj).getId() == this.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
