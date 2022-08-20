package edu.vassar.cmpu203.nextgenpos.view;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import edu.vassar.cmpu203.nextgenpos.databinding.MainBinding;

public class MainView implements IMainView{

    private final MainBinding binding;
    private final FragmentActivity activity;

    public MainView(FragmentActivity activity){
        this.binding = MainBinding.inflate(activity.getLayoutInflater());
        this.activity = activity;
    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(Fragment fragment) {

        this.activity.getSupportFragmentManager()
                .beginTransaction()
                    .replace(this.binding.fragmentContainerView.getId(), fragment)
                    .commitNow();
    }

    /**
     * An alternative version of the displayFragment() method that instead of taking in a fragment
     * object, takes in a class, and lets whatever fragment factory is active right now decide how
     * to instantiate it.
     * @param fragmentClass the fragment class to instantiate
     * @param args the arguments to be passed to the fragment
     */
    @Override
    public void displayFragment(Class<? extends Fragment> fragmentClass, Bundle args) {
        this.activity.getSupportFragmentManager().beginTransaction()
                .replace(this.binding.fragmentContainerView.getId(), fragmentClass, args)
                .commitNow();
    }

    @Override
    public Fragment getCurrentFragment() {
        // note: getFragment() is only available if implementation('androidx.fragment:fragment:1.4.0')
        // is part of the build.gradle dependencies (module level)
        return this.binding.fragmentContainerView.getFragment();
    }
}
