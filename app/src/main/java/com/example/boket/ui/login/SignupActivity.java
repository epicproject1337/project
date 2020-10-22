package com.example.boket.ui.login;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.model.user.LocalUser;
import com.example.boket.model.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

/**
 * @author Pajam Khoshnam
 * <p>
 * The Activity for signing up a user
 * @since 2020-09-07
 */
public class SignupActivity extends AppCompatActivity {

    private static final String TAG = SignupActivity.class.getName();
    private Drawable originTVcolor;

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
        originTVcolor = nameEditText.getBackground();

        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        setTVwriteListener(nameEditText);
        setTVwriteListener(emailEditText);
        setTVwriteListener(emailConfirmEditText);
        setTVwriteListener(passwordEditText);
        setTVwriteListener(passwordConfirmEditText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);

                if (!checkInputs()) {
                    Log.e("SIGNUP", "SIGNUP NOT VALID");
                    return;
                }

                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String emailConfirm = emailConfirmEditText.getText().toString();
                String password = emailConfirmEditText.getText().toString();
                String passwordConfirm = passwordConfirmEditText.getText().toString();

                signup(name, email, emailConfirm, password, passwordConfirm);

            }

            private boolean checkInputs() {

                boolean inputCorrect = true;
                String name = nameEditText.getText().toString();
                if (name.length() <= 0 || name.matches(".*\\d.*")) {
                    inputCorrect = false;
                    nameEditText.getText().clear();
                    nameEditText.setBackgroundColor(Color.parseColor("#FFD6D6"));
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

    private void setTVwriteListener(TextView tv) {
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv.setBackground(originTVcolor);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void signup(final String name, String email, String emailConfirm, String password, String passwordConfirm) {
        //TODO: Validate email and password

        System.out.println(email);
        System.out.println(emailConfirm);

        LocalUser.signup(name, email, emailConfirm, password, passwordConfirm, "Göteborg", new LocalUser.SignupCallback() {
            @Override
            public void onSignupComplete(LocalUser user) {
                Log.d(TAG, "createUserWithEmail:success");
                LocalUser.getCurrentUser();
                //Update the UI
                updateUiWithUser(user.getName());
            }

            @Override
            public void onSignupFailed(String message) {
                showLoginFailed("Signup failed! Try again.. " + message);
            }
        });
    }

    //TODO: LoginActivity have same method. Merge somehow.
    private void updateUiWithUser(String displayName) {
        String welcome = "Welcome! " + displayName;
        // TODO : initiate successful logged in experience
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

    //TODO: LoginActivity have same method. Merge somehow.
    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    //TODO : Add error handling
    private void updateUsersName(String name, FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }
}
