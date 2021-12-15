package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerDetailsForAssoc extends AppCompatActivity {

    private DatabaseReference mRootRef;

    private TextView FirstName;
    private TextView LastName;
    private TextView Age;
    private TextView Email;
    private TextView PhoneNumber;
    private TextView City;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_details_for_assoc);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        // Bundle bun = null;

        FirstName = (TextView) findViewById(R.id.VolunteerDetailsForAssocFirstName);
        LastName = (TextView) findViewById(R.id.VolunteerDetailsForAssocLastName);
        Age = (TextView) findViewById(R.id.VolunteerDetailsForAssocAge);
        Email = (TextView) findViewById(R.id.VolunteerDetailsForAssocEmail);
        PhoneNumber = (TextView) findViewById(R.id.VolunteerDetailsForAssocPhoneNumber);
        City = (TextView) findViewById(R.id.VolunteerDetailsForAssocCity);




    }
}