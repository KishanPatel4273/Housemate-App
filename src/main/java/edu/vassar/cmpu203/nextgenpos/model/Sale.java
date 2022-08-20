package edu.vassar.cmpu203.nextgenpos.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Sale implements Serializable {

    private List<SalesLineItem> lineItems;
    private CashPayment payment;

    public Sale(){
        this.lineItems = new LinkedList<>();
    }

    public void addLineItem(String name, int qty){
        SalesLineItem lineItem = new SalesLineItem(name, qty);
        this.getLineItems().add(lineItem);
    }

    public List<SalesLineItem> getLineItems() {
        return lineItems;
    }
    public CashPayment getPayment() {
        return payment;
    }

    @Override
    @NonNull
    public String toString(){

        String str = "";
        for(SalesLineItem sli : this.lineItems){
            str += sli.toString() + "\n";
        }
        return str;
    }


    public void setPayment(CashPayment payment) {
        this.payment = payment;
    }

    /**
     * Computes total as equal to number of units (dollar store style).`
     * @return total amount for sale
     */
    @Exclude
    public double getTotal(){
        double total = 0;

        for (SalesLineItem sli : this.lineItems)
            total += sli.getQty();

        return total;
    }

}
