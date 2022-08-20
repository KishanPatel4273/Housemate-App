package edu.vassar.cmpu203.nextgenpos.view;

import edu.vassar.cmpu203.nextgenpos.model.Ledger;

public interface ILedgerView {

    interface Listener{
        void onNewSale();
        Ledger getLedger();
    }
    void onLedgerUpdated(Ledger ledger);
}
