package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class postShape extends AppCompatActivity {

    private Button btnSeeDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_shape);

        btnSeeDetails = (Button)findViewById(R.id.btnseedetails);
    }
}