package com.example.volentake;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Activity Volunteer Home Page
 */
public class VolunteerPage extends AppCompatActivity {
    private Button edit;
    private Button addPost;
    private Button logOut;
    //    firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_page);

        edit = (Button)findViewById(R.id.btnEditVol);
        logOut = (Button)findViewById(R.id.btnLogOut);

        edit.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, EditVolunteer.class);
            startActivity(intent);
        });


        logOut.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, VolunteerLogIn.class);
            startActivity(intent);
        });
    }
}