package com.example.boket.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.user.LocalUser;

import org.w3c.dom.Text;

/**
 * @author Pajam Khoshnam
 * <p>
 * The Activity for signing up a user
 * @since 2020-09-07
 */
public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getName();
    //private Drawable originTextViewColor;
    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);

        final EditText nameEditText = findViewById(R.id.name);
        final EditText emailEditText = findViewById(R.id.email);
        final EditText emailConfirmEditText = findViewById(R.id.emailConfirm);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText passwordConfirmEditText = findViewById(R.id.passwordConfirm);
        final Button signupButton = findViewById(R.id.signup);
        final Button goTologin = findViewById(R.id.goToLogin);
        //originTextViewColor = nameEditText.getBackground();

        loadingProgressBar = findViewById(R.id.loading);

        setTextColorWriteListener(nameEditText);
        setTextColorWriteListener(emailEditText);
        setTextColorWriteListener(emailConfirmEditText);
        setTextColorWriteListener(passwordEditText);
        setTextColorWriteListener(passwordConfirmEditText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkInputs()) {
                    Log.e("SIGNUP", "SIGNUP NOT VALID");
                    return;
                }

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String emailConfirm = emailConfirmEditText.getText().toString();
                String password = emailConfirmEditText.getText().toString();
                String passwordConfirm = passwordConfirmEditText.getText().toString();

                loadingProgressBar.setVisibility(View.VISIBLE);
                signup(name, email, emailConfirm, password, passwordConfirm);

            }

            @SuppressLint("ResourceAsColor")
            private boolean checkInputs() {

                boolean inputCorrect = true;
                String name = nameEditText.getText().toString();
                if (name.length() <= 0 || name.matches(".*\\d.*")) {
                    inputCorrect = false;
                    nameEditText.getText().clear();
                    nameEditText.setBackgroundColor(Color.parseColor("#FFD6D6"));

                    //nameEditText.setBackgroundColor());
                    nameEditText.setHint("Skriv namn, inga nummer!");
                }
                String email = emailEditText.getText().toString();
                if (email.length() <= 0 || !email.matches("^.+@.+\\..+$")) {
                    inputCorrect = false;
                    emailEditText.getText().clear();
                    emailEditText.setBackgroundColor(Color.parseColor("#FFD6D6"));
                    emailEditText.setHint("Inte giltig email!");
                }
                String emailconf = emailConfirmEditText.getText().toString();
                if (!emailconf.equals(email) || emailconf.isEmpty()) {
                    inputCorrect = false;
                    emailConfirmEditText.getText().clear();
                    emailConfirmEditText.setBackgroundColor(Color.parseColor("#FFD6D6"));
                    emailConfirmEditText.setHint("Upprepa samma mail!");
                }
                String password = passwordEditText.getText().toString();
                if (password.isEmpty()) {
                    inputCorrect = false;
                    passwordEditText.setBackgroundColor(Color.parseColor("#FFD6D6"));
                    passwordEditText.setHint("Du måste ha ett lösenord!");
                }
                String passConf = passwordConfirmEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                if (!passConf.equals(pass) || passConf.isEmpty()) {
                    inputCorrect = false;
                    passwordConfirmEditText.getText().clear();
                    passwordConfirmEditText.setBackgroundColor(Color.parseColor("#FFD6D6"));
                    passwordConfirmEditText.setHint("Upprepa samma lösenord!");
                }
                return inputCorrect;
            }
        });


        goTologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotToLoginMethod();

            }
        });
    }

    private void setTextColorWriteListener(TextView tv) {
        tv.addTextChangedListener(new TextWatcher() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 tv.setBackgroundColor(Color.parseColor("#f1f3f5"));
                //tv.setBackgroundColor(R.color.colorDefaultInput);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void signup(final String name, String email, String emailConfirm, String password, String passwordConfirm) {

        LocalUser.signup(name, email, emailConfirm, password, passwordConfirm, "Göteborg", new LocalUser.SignupCallback() {
            @Override
            public void onSignupComplete(LocalUser user) {
                Log.d(TAG, "createUserWithEmail:success");
                LocalUser.getCurrentUser();
                updateUiWithUser(user.getName());
            }

            @Override
            public void onSignupFailed(String message) {
                loadingProgressBar.setVisibility(View.GONE);
                showLoginFailed("Signup failed! Try again.. " + message);
            }
        });
    }

    private void updateUiWithUser(String displayName) {
        String welcome = "Welcome! " + displayName;
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotToLoginMethod() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
