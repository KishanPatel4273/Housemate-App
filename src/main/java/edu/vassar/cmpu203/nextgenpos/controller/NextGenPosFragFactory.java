package edu.vassar.cmpu203.nextgenpos.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import edu.vassar.cmpu203.nextgenpos.view.AddItemsFragment;
import edu.vassar.cmpu203.nextgenpos.view.AuthFragment;
import edu.vassar.cmpu203.nextgenpos.view.CashPaymentFragment;

public class NextGenPosFragFactory extends FragmentFactory {

    private ControllerActivity controller;

    NextGenPosFragFactory(ControllerActivity controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {

        Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader, className);

        Fragment fragment;
        if (fragmentClass == AuthFragment.class)
            fragment = new AuthFragment(controller);
        else if (fragmentClass == AddItemsFragment.class)
            fragment = new AddItemsFragment(controller);
        else if (fragmentClass == CashPaymentFragment.class)
            fragment = new CashPaymentFragment(controller);
        else fragment = super.instantiate(classLoader, className);

        return fragment;
    }
}
