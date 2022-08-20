package edu.vassar.cmpu203.nextgenpos.model;

import java.io.Serializable;

public class CashPayment implements Serializable {

    private double tenderedAmount;

    public CashPayment() {}

    public CashPayment(double tenderedAmount){
        this.tenderedAmount = tenderedAmount;
    }

    public double getTenderedAmount() {
        return tenderedAmount;
    }
}
