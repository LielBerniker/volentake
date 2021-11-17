package com.example.volentake;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button volunteerLoginBtn;
    private Button associationLoginBtn;
    private Button guestLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volunteerLoginBtn = (Button) findViewById(R.id.volunteerloginbtn);
        associationLoginBtn = (Button) findViewById(R.id.associationloginbtn);
        guestLoginBtn = (Button) findViewById(R.id.guestloginbtn);

        volunteerLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, VolunteerLogIn.class);
            startActivity(intent);
        });

        associationLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AssociationLogIn.class);
            startActivity(intent);
        });

        guestLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GuestUser.class);
            startActivity(intent);
        });
    }
}