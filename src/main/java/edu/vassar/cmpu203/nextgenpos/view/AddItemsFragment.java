    package edu.vassar.cmpu203.nextgenpos.view;

    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.text.Editable;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

    import com.google.android.material.snackbar.Snackbar;

    import edu.vassar.cmpu203.nextgenpos.databinding.FragmentAddItemsBinding;
    import edu.vassar.cmpu203.nextgenpos.model.Sale;


    public class AddItemsFragment extends Fragment implements IAddItemsView{

        FragmentAddItemsBinding binding;
        Listener listener;

        public AddItemsFragment(Listener listener) {
            this.listener = listener;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            this.binding = FragmentAddItemsBinding.inflate(inflater);
            return this.binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

            this.binding.addItemButton.setOnClickListener((clickedView) -> {
                    // get the item name
                    Editable nameEditable = binding.nameEditText.getText();
                    String name = nameEditable.toString();

                    Editable qtyEditable = binding.qtyEditText.getText();
                    String qtyString = qtyEditable.toString();
                    qtyEditable.clear();

                    int qty = -1;

                    try {
                        qty = Integer.parseInt(qtyString);
                    } catch (NumberFormatException e){
                        Log.d("NextGenPos", "the user doesn't know what they're doing");
                    }

                    if (qty < 1){
                        Snackbar.make(view, "Invalid quantity. Please provide positive integer.", Snackbar.LENGTH_LONG).show();
                    } else {
                        nameEditable.clear();
                        listener.onAddedItem(name, qty, AddItemsFragment.this);
                    }

            });

            this.binding.doneButton.setOnClickListener( (clickedView) -> {
                    this.listener.onItemsDone();
                    }
            );
        }

        @Override
        public void updateDisplay(Sale sale) {
            String s = sale.toString();
            this.binding.lineItemsTextView.setText(s);
        }

    }