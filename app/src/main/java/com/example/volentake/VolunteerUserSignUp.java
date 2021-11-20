
package com.example.volentake;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Address;
import com.example.myapplication.Vol_user;
import com.example.myapplication.Volunteer_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;


public class VolunteerUserSignUp extends AppCompatActivity {

    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputAddress;
    private EditText inputBirthday;
    private EditText inputPassword1;
    private EditText inputPassword2;
    private EditText inputMail;
    private Button register;
//    firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_user_sign_up);

        inputFirstName = (EditText) findViewById(R.id.inputFirstName);
        inputLastName =(EditText)  findViewById(R.id.inputLastName);
        inputAddress = (EditText) findViewById(R.id.inputAddress);
        inputMail = (EditText) findViewById(R.id.inputMail);
        inputPassword1 = (EditText) findViewById(R.id.inputPassword1);
        inputPassword2 =(EditText)  findViewById(R.id.inputPassword2);
        register = (Button)findViewById(R.id.register);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFirstName = inputFirstName.getText().toString();
                String txtLastName = inputLastName.getText().toString();
                String txtEmail = inputMail.getText().toString();
                String txtPassword1 = inputPassword1.getText().toString();
                String txtPassword2 = inputPassword2.getText().toString();
                String txtaddrress = inputAddress.getText().toString();

                if (TextUtils.isEmpty(txtFirstName) || TextUtils.isEmpty(txtLastName ) || TextUtils.isEmpty(txtaddrress)
                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword1) || TextUtils.isEmpty(txtPassword2)){
                    Toast.makeText(VolunteerUserSignUp.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (!(txtPassword1.equals(txtPassword2))){
                    Toast.makeText(VolunteerUserSignUp.this, "Incompatible Passwords!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword1.length() < 6){
                    Toast.makeText(VolunteerUserSignUp.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtFirstName , txtLastName , txtEmail , txtPassword1,txtaddrress);
                }
            }
        });
    }
    private void registerUser(final String firstName, final String lastName, final String email, String password,final String add) {


        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Address address = new Address(add,"zohar",4);
                Date date = new Date();
                Volunteer_user cur_user = new Vol_user(firstName,lastName,address,"05424234",date,email);

                mRootRef.child("vol_users").child(mAuth.getCurrentUser().getUid()).setValue(cur_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(VolunteerUserSignUp.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VolunteerUserSignUp.this, VolunteerLogIn.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VolunteerUserSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}