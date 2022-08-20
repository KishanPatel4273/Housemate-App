package edu.vassar.cmpu203.nextgenpos.model;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.LinkedList;

public class Ledger {

    private final Collection<Sale> sales;

    public Ledger(){
        this.sales = new LinkedList<>();
    }

    public void addSale(Sale sale){
        this.sales.add(sale);
    }

    @NonNull
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Sale sale : sales) {
            sb.append(sale.toString());
            sb.append("Total: $");
            sb.append(sale.getTotal());
            sb.append("\n-----------\n");
        }
        return sb.toString();
    }
}
