package edu.vassar.cmpu203.nextgenpos.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.vassar.cmpu203.nextgenpos.model.Ledger;
import edu.vassar.cmpu203.nextgenpos.model.Sale;
import edu.vassar.cmpu203.nextgenpos.model.User;

public class FirestoreFacade implements IPersistenceFacade {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String SALES_LEDGER = "ledger";
    private static final String USERS = "users";

    @Override
    public void saveSale(@NonNull Sale sale) {
        db.collection(SALES_LEDGER).add(sale);
    }

    public void retrieveLedger(@NonNull DataListener<Ledger> listener){
        this.db.collection(SALES_LEDGER).get()
                .addOnSuccessListener(qsnap -> {
                    Ledger ledger = new Ledger();
                    for (DocumentSnapshot dsnap : qsnap){
                        Sale sale = dsnap.toObject(Sale.class);
                        ledger.addSale(sale);
                    }
                    listener.onDataReceived(ledger);
                })
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error retrieving ledger from database",e));
    }

    @Override
    public void createUserIfNotExists(@NonNull User user, @NonNull BinaryResultListener listener) {

        this.retrieveUser(user.getUsername(), new DataListener<User>() {
                    @Override
                    public void onDataReceived(@NonNull User user) { // there's data there, so no go
                        listener.onNoResult();
                    }

                    @Override
                    public void onNoDataFound() { // there's no data there, so we can add the user
                        FirestoreFacade.this.setUser(user, listener);
                    }
                }
        );
    }

    private void setUser(@NonNull User user, @NonNull BinaryResultListener listener){
        this.db.collection(USERS)
                .document(user.getUsername())
                .set(user)
                .addOnSuccessListener( avoid -> listener.onYesResult())
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error retrieving ledger from database",e));
    }

    @Override
    public void retrieveUser(@NonNull String username, @NonNull DataListener<User> listener) {

        this.db.collection(USERS).document(username).get()
                .addOnSuccessListener(dsnap -> {
                    if (dsnap.exists()) { // got some data back
                        User user = dsnap.toObject(User.class);
                        assert (user != null);
                        listener.onDataReceived(user);
                    } else listener.onNoDataFound();  // no username match
                })
                .addOnFailureListener(e ->
                        Log.w("NextGenPos", "Error retrieving user from database",e));
    }
}
