package com.example.boket.ui.addAd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.boket.R;

import org.w3c.dom.Text;

public class AddAdActivity extends AppCompatActivity {
    // TODO: 2020-09-09
    //Lagra info i db
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ad);

        final EditText conditionEditText = findViewById(R.id.conditionInput);
        final EditText priceEditText = findViewById(R.id.priceInput);
        final EditText cityEditText = findViewById(R.id.cityInput);
        final Button publishButton = findViewById(R.id.publishButton);
        final TextView authorText = findViewById(R.id.authorText);
        final TextView titleText = findViewById(R.id.titleText);

    }

}