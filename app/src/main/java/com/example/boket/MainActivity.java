package com.example.boket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.boket.ui.addAd.SearchAddAd;
import com.example.boket.ui.login.LoginActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
/**
 * @author Pajam Khoshnam, Albin Landgren, Oscar Bennet
 *
 * The MainActivity when the app starts
 *
 * @since 2020-09-07
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //TODO : Remove this. Only for testing purpose
        //mAuth.signOut();

        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            //Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show();
        }

        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_profile)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * Start the SearchAddAd activity when add Ad button is pressed
     * @param view the associated view.
     */
    public void startAddAdActivity(View view) {
        Intent intent = new Intent(this, SearchAddAd.class);
        startActivity(intent);
    }

    public void showBookFromProfile(View view) {

    }

    /**
     * Method to signout a user.
     */
    //TODO: Move logic to user model.
    public void signOut() {
        mAuth.signOut();
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show();
        }
    }
/*
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            this.finish();
        }
    }*/


}