package edu.vassar.cmpu203.nextgenpos.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.vassar.cmpu203.nextgenpos.databinding.FragmentLedgerBinding;
import edu.vassar.cmpu203.nextgenpos.model.Ledger;


public class LedgerFragment extends Fragment implements ILedgerView {

    private FragmentLedgerBinding binding;
    private Listener listener;

    public LedgerFragment(@NonNull Listener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = FragmentLedgerBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {

        this.binding.ledgerTextView.setText(this.listener.getLedger().toString());

        this.binding.newSaleButton.setOnClickListener((clickedView) -> {
            LedgerFragment.this.listener.onNewSale();
        });
    }

    @Override
    public void onLedgerUpdated(Ledger ledger) {
        this.binding.ledgerTextView.setText(ledger.toString());
    }
}