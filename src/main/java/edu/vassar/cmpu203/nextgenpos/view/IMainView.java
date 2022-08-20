package edu.vassar.cmpu203.nextgenpos.view;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

public interface IMainView {

    View getRootView();
    void displayFragment(Fragment fragment);
    void displayFragment(Class<? extends Fragment> fragmentClass, Bundle args);
    Fragment getCurrentFragment();

}
