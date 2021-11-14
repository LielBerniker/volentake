package com.example.volentake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class VolunteerLogIn extends AppCompatActivity {

    private Button volunteerLoginBtn;
    private Button volunteerCreateUser;
    private Button volunteerLoginBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_log_in);

        volunteerLoginBtn = (Button) findViewById(R.id.volunteerloginbtn);
        volunteerCreateUser = (Button) findViewById(R.id.volunteercreateuser);
        volunteerLoginBack = (Button) findViewById(R.id.volunteerloginback);

        volunteerLoginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerLogIn.this, VolunteerPage.class);
            startActivity(intent);
        });

//        volunteerCreateUser.setOnClickListener(view -> {
//            Intent intent = new Intent(VolunteerLogIn.this, VolunteerUserSignUp.class);
//            startActivity(intent);
//        });

        volunteerLoginBack.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerLogIn.this, MainActivity.class);
            startActivity(intent);
        });
    }
}