package com.example.boket.ui.login;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = SignupActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_signup);

        final TextView nameTextView = findViewById(R.id.name);
        final TextView emailTextView = findViewById(R.id.email);
        final TextView emailConfirmTextView = findViewById(R.id.emailConfirm);
        final TextView passwordTextView = findViewById(R.id.password);
        final TextView passwordConfirmTextView = findViewById(R.id.passwordConfirm);
        final Button signupButton = findViewById(R.id.signup);
        final Button goTologin = findViewById(R.id.goToLogin);

        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                signup(nameTextView.getText().toString(), emailTextView.getText().toString(),
                        emailConfirmTextView.getText().toString(), passwordTextView.getText().toString(),
                        passwordConfirmTextView.getText().toString());

            }
        });

    }

    private void signup(String name, String email, String emailConfirm, String password, String passwordConfirm){
        //TODO: Validate email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUiWithUser(user.getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            showLoginFailed("Signup failed! Try again..");
                        }


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

    //TODO: LoginActivity have same method. Merge somehow.
    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
