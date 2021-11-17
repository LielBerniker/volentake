package com.example.volentake;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;




public class VolunteerLogIn extends AppCompatActivity {

    // buttons
    private Button volunteerLoginBtn;
    private Button volunteerCreateUser;
    private Button volunteerLoginBack;
    // insert details for user signIn
    private TextView volunteerEmailEditText;
    private TextView volunteerPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_log_in);

        volunteerLoginBtn = (Button) findViewById(R.id.volunteerloginbtn);
        volunteerCreateUser = (Button) findViewById(R.id.volunteercreateuser);
        volunteerLoginBack = (Button) findViewById(R.id.volunteerloginback);

        volunteerEmailEditText = (TextView) findViewById(R.id.volunteeruseremail);
        volunteerPasswordEditText = (TextView) findViewById(R.id.volunteeruserpassword);

        volunteerLoginBtn.setOnClickListener(view -> {

            // checking validate of user details with firebase database - email + password
            // ***
            String email = volunteerEmailEditText.getText().toString();
            String password = volunteerPasswordEditText.getText().toString();
            Intent intent = new Intent(VolunteerLogIn.this, VolunteerPage.class);
            startActivity(intent);
        });

        volunteerCreateUser.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerLogIn.this, VolunteerUserSignUp.class);
            startActivity(intent);
        });

        volunteerLoginBack.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerLogIn.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
