package com.example.volentake;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Address;
import com.example.myapplication.Assoc_user;
import com.example.myapplication.Association_user;
import com.example.myapplication.Request;
import com.example.myapplication.Request_vol;
import com.example.myapplication.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AssociationSignUp extends AppCompatActivity {

    private EditText inputName;
    private EditText inputCity;
    private EditText inputStreet;
    private EditText inputHouseNumber;
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
        inputCity = (EditText) findViewById(R.id.cityinsert2);
        inputStreet = (EditText) findViewById(R.id.inputStreet1);
        inputHouseNumber = (EditText) findViewById(R.id.houseNumberInput1);
        inputPhone = (EditText) findViewById(R.id.inputPhone);
        inputAbout = (EditText) findViewById(R.id.inputAbout);
        inputMail = (EditText) findViewById(R.id.inputEmail);
        inputPassword1 = (EditText) findViewById(R.id.inputPass1);
        inputPassword2 =(EditText)  findViewById(R.id.inputPass2);
        register = (Button)findViewById(R.id.btnRegister);
        logInAssociation = (Button)findViewById(R.id.backLogIn);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = inputName.getText().toString();
                String txtCity = inputCity.getText().toString().toLowerCase(Locale.ROOT);

                String txtStreet = inputStreet.getText().toString();
                String txtHouseNumber = inputHouseNumber.getText().toString();

                String txtPhone = inputPhone.getText().toString();
                String txtEmail = inputMail.getText().toString();
                String txtAbout = inputAbout.getText().toString();
                String txtPassword1 = inputPassword1.getText().toString();
                String txtPassword2 = inputPassword2.getText().toString();

                if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtPhone ) || TextUtils.isEmpty(txtCity)
                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtAbout) || TextUtils.isEmpty(txtPassword1) || TextUtils.isEmpty(txtPassword2)){
                    Toast.makeText(AssociationSignUp.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (!(txtPassword1.equals(txtPassword2))){
                    Toast.makeText(AssociationSignUp.this, "Incompatible Passwords!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword1.length() < 6){
                    Toast.makeText(AssociationSignUp.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtName , txtCity, txtStreet,txtHouseNumber , txtPhone , txtAbout , txtEmail , txtPassword1);
                }
            }
        });

        logInAssociation.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationSignUp.this, AssociationLogIn.class);
            startActivity(intent);
        });
    }
    private void registerUser(final String Name, final String city,final String street,final String housenum, final String phone,final String about,final String email, String password) {


        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Address address = new Address(city,street,Integer.parseInt(housenum));
                Date date = new Date();
                String user_id = mAuth.getCurrentUser().getUid();
                Association_user cur_user = new Assoc_user(phone,address,Name,email,about);

                mRootRef.child("assoc_users").child(user_id).setValue(cur_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AssociationSignUp.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AssociationSignUp.this, AssociationPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("id",mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AssociationSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}