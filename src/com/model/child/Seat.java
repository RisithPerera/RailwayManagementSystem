package com.model.child;

import com.manifest.Symbol;
import com.model.superb.SuperModel;

import java.util.Objects;

public class Seat extends SuperModel {

    private Compartment compartment;
    private int seatCol;
    private int seatRow;
    private int comfortType;
    private boolean isAvailbale;

    public Seat() {}

    public Seat(Compartment compartment, int seatCol, int seatRow, int comfortType, boolean isAvailbale) {
        this.seatCol = seatCol;
        this.seatRow = seatRow;
        this.compartment = compartment;
        this.comfortType = comfortType;
        this.isAvailbale = isAvailbale;
    }

    public int getSeatCol() {
        return seatCol;
    }

    public void setSeatCol(int seatCol) {
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

    public int getComfortType() {
        return comfortType;
    }

    public void setComfortType(int comfortType) {
        this.comfortType = comfortType;
    }

    public boolean isAvailbale() {
        return isAvailbale;
    }

    public void setAvailbale(boolean availbale) {
        isAvailbale = availbale;
    }

    //---------------------------- Custom Methods   -----------------------------//

    public String getId(){
        return this.compartment.getId()+":"+(char)(this.getSeatCol()+64)+"-"+this.getSeatRow();
    }
    //---------------------------- Override Methods -----------------------------//

    @Override
    public String toString() {
        return  getSeatCol()     + Symbol.SPLIT +
                getSeatRow()     + Symbol.SPLIT +
                getCompartment() + Symbol.SPLIT +
                getComfortType() + Symbol.SPLIT +
                isAvailbale();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Seat) {
            return ((Seat)obj).getId().equals(this.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(String.format("%05d", this.getId()));
        return hash;
    }
}
