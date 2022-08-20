package edu.vassar.cmpu203.nextgenpos.view;

public interface ICashPaymentView {

    interface Listener {
        void onCashTendered(double tenderedAmount, ICashPaymentView cashPaymentView);
        void onPaymentDone();
    }
    void updateOnSalePaid(double change);
}
