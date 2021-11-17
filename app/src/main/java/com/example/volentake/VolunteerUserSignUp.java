package com.example.volentake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class VolunteerUserSignUp extends AppCompatActivity {

    private Button volunteerCreateUser;
    private Button toVolunteerLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_user_sign_up);

        volunteerCreateUser = (Button) findViewById(R.id.btnCreateVolunteer);
        toVolunteerLogIn = (Button) findViewById(R.id.toVolunteerLogIn);

    }
}