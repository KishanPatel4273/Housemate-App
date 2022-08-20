package edu.vassar.cmpu203.nextgenpos.persistence;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.nextgenpos.model.Ledger;
import edu.vassar.cmpu203.nextgenpos.model.Sale;
import edu.vassar.cmpu203.nextgenpos.model.User;

public interface IPersistenceFacade {

    interface DataListener<T>{
        void onDataReceived(@NonNull T data);
        void onNoDataFound();
    }

    interface BinaryResultListener {
        void onYesResult();
        void onNoResult();
    }

    // ledger related
    void saveSale(@NonNull Sale sale);
    void retrieveLedger(@NonNull DataListener<Ledger> listener);

    // authentication related
    void createUserIfNotExists(@NonNull User user, @NonNull BinaryResultListener listener);
    void retrieveUser(@NonNull String username, @NonNull DataListener<User> listener);
}
