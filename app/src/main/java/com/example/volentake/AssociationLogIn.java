package com.example.volentake;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AssociationLogIn extends AppCompatActivity {

    // buttons
    private Button associationLoginBtn;
    private Button associationCreateUser;
    private Button associationLoginBack;

    // insert details for user signIn
    private TextView associationEmailEditText;
    private TextView associationPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_log_in);


        associationLoginBtn = (Button) findViewById(R.id.associationloginbtn);
        associationCreateUser = (Button) findViewById(R.id.associationcreateuser);
        associationLoginBack = (Button) findViewById(R.id.associationloginback);

        associationEmailEditText = (TextView) findViewById(R.id.associationuseremail);
        associationPasswordEditText = (TextView) findViewById(R.id.volunteeruserpassword);

        associationLoginBtn.setOnClickListener(view -> {

            // checking validate of user details with firebase database - email + password
            // ***
            String email = associationEmailEditText.getText().toString();
            String password = associationPasswordEditText.getText().toString();

            Intent intent = new Intent(AssociationLogIn.this, AssociationPage.class);
            startActivity(intent);
        });

//        associationCreateUser.setOnClickListener(view -> {
//            Intent intent = new Intent(associationLogIn.this, associationUserSignUp.class);
//            startActivity(intent);
//        });

        associationLoginBack.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationLogIn.this, MainActivity.class);
            startActivity(intent);
        });
    }
}