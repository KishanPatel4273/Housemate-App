package edu.vassar.cmpu203.nextgenpos.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.nextgenpos.databinding.FragmentCashPaymentBinding;


public class CashPaymentFragment extends Fragment implements ICashPaymentView{

    private final static String SALE_TOTAL = "saleTotal";
    private final static String IS_PAID = "isPaid";

    private FragmentCashPaymentBinding binding;
    private double saleTotal;
    private Listener listener;
    private boolean isPaid = false;

    public static Bundle makeArgsBundle(double saleTotal){
        Bundle args = new Bundle();
        args.putDouble(CashPaymentFragment.SALE_TOTAL, saleTotal);
        return args;
    }

    public CashPaymentFragment(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();

        if (args != null)
            this.saleTotal = args.getDouble(SALE_TOTAL);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentCashPaymentBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        this.binding.saleTotalText.setText("$"+this.saleTotal);
        this.binding.payButton.setOnClickListener( (clickedView) -> {
            String tenderedAmountStr = this.binding.tenderedAmountText.getText().toString();
            double tenderedAmount = Double.parseDouble(tenderedAmountStr);
            this.listener.onCashTendered(tenderedAmount,  CashPaymentFragment.this);
        });

        this.binding.paymentDoneButton.setOnClickListener((clickedView) -> CashPaymentFragment.this.listener.onPaymentDone());

    }

    @Override
    public void updateOnSalePaid(double change) {
        this.isPaid = true;
        this.binding.changeText.setText("$"+change);
        displayIsPaidConfiguration();
    }

    /**
     * This method is used to give the fragment a chance to save state information before
     * being destroyed to free up system resources.
     * @param outState the bundle to save state to
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState); // will save some widget features automatically
        outState.putBoolean(IS_PAID, this.isPaid);
    }

    /**
     * This method is used to give the fragment a chance to restore state information before
     * being destroyed to free up system resources.
     * @param savedInstanceState the bundle to read state from
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState); // will reinstate widget features automatically

        if (savedInstanceState != null) // it can be null if it's the first time we're creating the fragment
            this.isPaid = savedInstanceState.getBoolean(IS_PAID);

        if (this.isPaid) displayIsPaidConfiguration();
    }

    /**
     * Helper method that configures screen to display correctly after a payment has been made.
     */
    private void displayIsPaidConfiguration() {
        this.binding.payButton.setEnabled(false);
        this.binding.tenderedAmountText.setEnabled(false);
        this.binding.thankYouLabel.setVisibility(View.VISIBLE);
        this.binding.changeText.setVisibility(View.VISIBLE);
        this.binding.paymentDoneButton.setVisibility(View.VISIBLE);
    }
}