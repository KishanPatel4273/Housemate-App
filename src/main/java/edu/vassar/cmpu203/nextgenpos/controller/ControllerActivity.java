package edu.vassar.cmpu203.nextgenpos.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import edu.vassar.cmpu203.nextgenpos.model.CashPayment;
import edu.vassar.cmpu203.nextgenpos.model.Ledger;
import edu.vassar.cmpu203.nextgenpos.model.Sale;
import edu.vassar.cmpu203.nextgenpos.model.User;
import edu.vassar.cmpu203.nextgenpos.persistence.FirestoreFacade;
import edu.vassar.cmpu203.nextgenpos.persistence.IPersistenceFacade;
import edu.vassar.cmpu203.nextgenpos.view.AddItemsFragment;
import edu.vassar.cmpu203.nextgenpos.view.AuthFragment;
import edu.vassar.cmpu203.nextgenpos.view.CashPaymentFragment;
import edu.vassar.cmpu203.nextgenpos.view.IAddItemsView;
import edu.vassar.cmpu203.nextgenpos.view.IAuthView;
import edu.vassar.cmpu203.nextgenpos.view.ICashPaymentView;
import edu.vassar.cmpu203.nextgenpos.view.ILedgerView;
import edu.vassar.cmpu203.nextgenpos.view.IMainView;
import edu.vassar.cmpu203.nextgenpos.view.LedgerFragment;
import edu.vassar.cmpu203.nextgenpos.view.MainView;

public class ControllerActivity extends AppCompatActivity
        implements IAddItemsView.Listener, ICashPaymentView.Listener, ILedgerView.Listener,
                   IAuthView.Listener {

    private static final String CUR_SALE = "curSale";
    private static final String CUR_USER = "curUser";

    private Ledger ledger;      // sales ledger
    private User curUser;       // current user
    private Sale curSale;       // current sale

    private IMainView mainView; // view logic class

    private final IPersistenceFacade persistenceFacade = new FirestoreFacade();

    @Override
    protected void onCreate(Bundle savedInstanceState){

        // use a custom factory so we can pass in the controller upon fragment reconstruction
        // must be called prior to call to super.onCreate()
        FragmentFactory fragmentFactory = new NextGenPosFragFactory(this);
        this.getSupportFragmentManager().
                setFragmentFactory(fragmentFactory);

        super.onCreate(savedInstanceState);

        // create screen skeleton
        this.mainView = new MainView(this);
        this.setContentView(this.mainView.getRootView());

        // load up the ledger
        this.ledger = new Ledger(); // initialize ledger
        this.persistenceFacade.retrieveLedger(new IPersistenceFacade.DataListener<Ledger>() {
            @Override
            public void onDataReceived(@NonNull Ledger ledger) {
                ControllerActivity.this.ledger = ledger; // set the activity's ledger to the one retrieved from the database

                Fragment curFrag = ControllerActivity.this.mainView.getCurrentFragment();
                if (curFrag instanceof ILedgerView)
                    ((ILedgerView) curFrag).onLedgerUpdated(ledger);
            }

            @Override
            public void onNoDataFound() { } // if no ledger found, do nothing - just start from
        });

        // are we starting from scratch, or recreating the activity?
        if (savedInstanceState != null) { // there is saved state, so we're recreating
            this.curUser = (User) savedInstanceState.getSerializable(CUR_USER);
            this.curSale = (Sale) savedInstanceState.getSerializable(CUR_SALE);
        } else { // no saved state, we're starting from scratch
            this.curSale = new Sale(); // start out with an empty sale
            // first screen is the authentication screen
            this.mainView.displayFragment(new AuthFragment(this));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CUR_USER, this.curUser);
        outState.putSerializable(CUR_SALE, this.curSale);
    }

    /* IAuthView.Listener realization start */
    @Override
    public void onRegister(String username, String password, IAuthView authView) {
        User user = new User(username, password); // our tentative user
        this.persistenceFacade.createUserIfNotExists(user, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() { authView.onRegisterSuccess(); }

            @Override
            public void onNoResult() { authView.onUserAlreadyExists(); }
        });
    }

    @Override
    public void onSigninAttempt(String username, String password, IAuthView authView) {

        this.persistenceFacade.retrieveUser(username, new IPersistenceFacade.DataListener<User>() {
            @Override
            public void onDataReceived(@NonNull User user) {
                if (user.validatePassword(password)){ // password matches
                    ControllerActivity.this.curUser = user; // we have a new user
                    // navigate to ledger screen
                    ControllerActivity.this.mainView.displayFragment(new LedgerFragment(ControllerActivity.this));

                } else authView.onInvalidCredentials(); // let the view know things didn't work out
            }

            @Override
            public void onNoDataFound() { // means username does not exist
                authView.onInvalidCredentials(); // let the view know things didn't work out
            }
        });
    }
    /* IAuthView.Listener realization end */

    /* ILedgerView.Listener realization start */
    @Override
    public void onPaymentDone() {
        this.mainView.displayFragment(new LedgerFragment(this));
    }

    @Override
    public void onNewSale() {
        this.curSale = new Sale(); // working on a new one now
        this.mainView.displayFragment(new AddItemsFragment(this));
    }

    @Override
    public Ledger getLedger() { return this.ledger; }
    /* ILedgerView.Listener realization end */


    /* IAddItemsView.Listener realization start */
    @Override
    public void onAddedItem(String name, int qty, IAddItemsView addItemsView) {
        this.curSale.addLineItem(name, qty);
        addItemsView.updateDisplay(this.curSale);
    }

    @Override
    public void onItemsDone() {
        double saleTotal = curSale.getTotal();
        Bundle args = CashPaymentFragment.makeArgsBundle(saleTotal);
        Fragment f = new CashPaymentFragment(this);
        f.setArguments(args);
        this.mainView.displayFragment(f);
    }
    /* IAddItemsView.Listener realization end */

    /* ICashPaymentView.Listener realization start */
    @Override
    public void onCashTendered(double tenderedAmount, ICashPaymentView cashPaymentView) {
        this.curSale.setPayment(new CashPayment(tenderedAmount));
        this.ledger.addSale(this.curSale); // add to ledger

        this.persistenceFacade.saveSale(this.curSale); // save to database

        double change = tenderedAmount - curSale.getTotal();
        cashPaymentView.updateOnSalePaid(change);
    }
    /* ICashPaymentView.Listener realization end */
}
