package com.example.boket;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.boket.ui.camera.BarcodeScannerActivity;
import com.example.boket.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //TODO : Remove this. Only for testing purpose
        //mAuth.signOut();

        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(this, BarcodeScannerActivity.class);
            startActivity(intent);
            finish();
        }else{
            //Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show();
        }




        setContentView(R.layout.activity_main);

        Intent barcodeScanner=new Intent(this, BarcodeScannerActivity.class);
        startActivity(barcodeScanner);

        /*
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


         */

    }

}