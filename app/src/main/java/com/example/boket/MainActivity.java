package com.example.boket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.boket.model.user.LocalUser;
import com.example.boket.ui.addAd.SearchAddAdActivity;
import com.example.boket.ui.login.LoginActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(LocalUser.getCurrentUser() == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
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
        Intent intent = new Intent(this, SearchAddAdActivity.class);
        startActivity(intent);
    }

    /**
     * Method to signout a user.
     */
    public void signOut() {
        LocalUser.signout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}