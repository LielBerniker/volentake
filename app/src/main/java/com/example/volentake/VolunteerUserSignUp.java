package com.example.volentake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VolunteerUserSignUp extends AppCompatActivity {

    // insert details for user signUp
    private TextView inputFirstName;
    private TextView inputLastName;
    private TextView inputAddress;
    private TextView editTextPhone;
    private TextView editTextDate;
    private TextView editTextEmailAddress;
    private TextView volunteerInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_user_sign_up);

        inputFirstName = (TextView) findViewById(R.id.inputFirstName);
        inputLastName = (TextView) findViewById(R.id.inputLastName);
        inputAddress = (TextView) findViewById(R.id.inputAddress);
        editTextPhone = (TextView) findViewById(R.id.editTextPhone);
        editTextDate = (TextView) findViewById(R.id.editTextDate);
        editTextEmailAddress = (TextView) findViewById(R.id.editTextEmailAddress);
        volunteerInputPassword = (TextView) findViewById(R.id.volunteerInputPassword);

    }


}