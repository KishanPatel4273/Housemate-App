package edu.vassar.cmpu203.nextgenpos.view;

import edu.vassar.cmpu203.nextgenpos.model.Sale;

public interface IAddItemsView {

    interface Listener{
        void onAddedItem(String name, int qty, IAddItemsView addItemsView);
        void onItemsDone();
    }
    void updateDisplay(Sale sale);

}
