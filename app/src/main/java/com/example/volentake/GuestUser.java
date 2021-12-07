package com.example.volentake;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GuestUser extends AppCompatActivity {
    private Button back;
    private Button vol_events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_user);
       back = (Button) findViewById(R.id.btnbackmain);
       vol_events = (Button) findViewById(R.id.btnpostjustforview);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(GuestUser.this, MainActivity.class);
            startActivity(intent);
        });

        vol_events.setOnClickListener(view -> {
            Intent intent = new Intent(GuestUser.this, FeedPostGuest.class);
            startActivity(intent);
        });
    }
}