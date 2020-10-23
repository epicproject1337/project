package com.example.boket.ui.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.user.LocalUser;

/**
 * @author Pajam Khoshnam
 *
 * The Activity for logging in a user
 *
 * @since 2020-09-07
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();
    private ProgressBar loadingProgressBar;
    private int mediumAnimationDuration;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final Button createAccountButton = findViewById(R.id.createAccount);
        loadingProgressBar = findViewById(R.id.loading);
        mediumAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        loginButton.setEnabled(true);
        createAccountButton.setEnabled(true);

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signIn(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setAlpha(0f);
                loadingProgressBar.setVisibility(View.VISIBLE);
                loadingProgressBar.animate().alpha(1f).setDuration(mediumAnimationDuration).setListener(null);
                signIn(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUiWithSignup();
            }
        });
    }

    private void signIn(String email, String password) {
        LocalUser.login(email, password, new LocalUser.LoginCallback() {
            @Override
            public void onLoginComplete(LocalUser user) {
                Log.d(TAG, "signInWithEmail:success");
                updateUiWithUser(user.getEmail());
            }

            @Override
            public void onLoginFailed(String message) {
                showLoginFailed("Login failed! " +  message);
                loadingProgressBar.animate().alpha(0f).setDuration(mediumAnimationDuration).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadingProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void updateUiWithSignup() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }


    private void updateUiWithUser(String displayName) {
        String welcome = "Welcome! " + displayName;
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}