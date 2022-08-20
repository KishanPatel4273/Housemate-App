package edu.vassar.cmpu203.nextgenpos.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.nextgenpos.R;
import edu.vassar.cmpu203.nextgenpos.databinding.FragmentAuthBinding;


public class AuthFragment extends Fragment implements IAuthView {

    private static final String IS_REGISTERED = "isRegistered";

    private final Listener listener;
    private FragmentAuthBinding binding;
    private boolean isRegistered = false;

    public AuthFragment(@NonNull Listener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentAuthBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null && savedInstanceState.getBoolean(IS_REGISTERED))
            activateRegisteredConfig();

        this.binding.registerButton.setOnClickListener( (clickedView) ->{
            String username = this.binding.usernameEditText.getText().toString();
            String password = this.binding.passwordEditText.getText().toString();
            AuthFragment.this.listener.onRegister(
                    username, password,AuthFragment.this);
        });

        this.binding.signinButton.setOnClickListener( (clickedView) ->{
            String username = this.binding.usernameEditText.getText().toString();
            String password = this.binding.passwordEditText.getText().toString();
            AuthFragment.this.listener.onSigninAttempt(
                    username, password, AuthFragment.this);
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_REGISTERED, this.isRegistered);
    }

    @Override
    public void onRegisterSuccess() {
        activateRegisteredConfig();
        this.displayMessage(R.string.registration_success_msg);
    }

    // prevent multiple registration attempts
    private void activateRegisteredConfig(){
        this.isRegistered = true;
        this.binding.registerButton.setEnabled(false);
    }

    @Override
    public void onInvalidCredentials() {
        displayMessage(R.string.invalid_credentials_msg);
    }

    @Override
    public void onUserAlreadyExists() {
        displayMessage(R.string.user_already_exists_msg);
    }

    private void displayMessage(int msgRid){
        Snackbar.make(this.binding.getRoot(),
                msgRid,
                Snackbar.LENGTH_LONG)
                .show();
    }
}