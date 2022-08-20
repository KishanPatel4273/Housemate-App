package edu.vassar.cmpu203.nextgenpos.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class SalesLineItem implements Serializable {
    private String name;
    private int qty;

    public SalesLineItem() {}

    public SalesLineItem(String name, int qty){
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    @Override
    @NonNull
    public String toString(){
        // <qty> units of <name>
        //return  "" + this.qty +  " units of " + this.name;

        return String.format("%d units of %s",  this.qty, this.name);

    }

}
