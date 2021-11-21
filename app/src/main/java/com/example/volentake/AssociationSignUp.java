
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


public class AssociationSignUp extends AppCompatActivity {

    private EditText inputName;
    private EditText inputAddress;
    private EditText inputPhone;
    private EditText inputAbout;
    private EditText inputMail;
    private EditText inputPassword1;
    private EditText inputPassword2;
    private Button register;
    private Button logInAssociation;
    //    firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_sign_up);

        inputName = (EditText) findViewById(R.id.inputName);
        inputAddress = (EditText) findViewById(R.id.inputAdd);
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        inputAbout = (EditText) findViewById(R.id.inputAbout);
        inputMail = (EditText) findViewById(R.id.inputEmail);
        inputPassword1 = (EditText) findViewById(R.id.inputPassword1);
        inputPassword2 =(EditText)  findViewById(R.id.inputPassword2);
        register = (Button)findViewById(R.id.btnRegister);
        logInAssociation = (Button)findViewById(R.id.backLogIn);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = inputName.getText().toString();
                String txtAddress = inputAddress.getText().toString();
                String txtPhone = inputPhone.getText().toString();
                String txtEmail = inputMail.getText().toString();
                String txtAbout = inputAbout.getText().toString();
                String txtPassword1 = inputPassword1.getText().toString();
                String txtPassword2 = inputPassword2.getText().toString();

                if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtPhone ) || TextUtils.isEmpty(txtAddress)
                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtAbout) || TextUtils.isEmpty(txtPassword1) || TextUtils.isEmpty(txtPassword2)){
                    Toast.makeText(AssociationSignUp.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (!(txtPassword1.equals(txtPassword2))){
                    Toast.makeText(AssociationSignUp.this, "Incompatible Passwords!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword1.length() < 6){
                    Toast.makeText(AssociationSignUp.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtName , txtAddress , txtPhone , txtAbout , txtEmail , txtPassword1);
                }
            }
        });

        logInAssociation.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationSignUp.this, AssociationLogIn.class);
            startActivity(intent);
        });
    }
    private void registerUser(final String Name, final String address, final String phone,final String about,final String email, String password) {
//
//
//        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Address address = new Address(add,"zohar",4);
//                Date date = new Date();
//                Volunteer_user cur_user = new Vol_user(firstName,lastName,address,"05424234",date,email);
//
//                mRootRef.child("vol_users").child(mAuth.getCurrentUser().getUid()).setValue(cur_user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(VolunteerUserSignUp.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(VolunteerUserSignUp.this, VolunteerLogIn.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }
//                });
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(VolunteerUserSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }


}