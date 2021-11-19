
package com.example.volentake;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_user_sign_up);

        inputFirstName = findViewById(R.id.inputFirstName);
        inputLastName = findViewById(R.id.inputLastName);
        inputAddress = findViewById(R.id.inputAddress);
        inputMail = findViewById(R.id.inputMail);
        inputPassword1 = findViewById(R.id.inputPassword1);
        inputPassword2 = findViewById(R.id.inputPassword2);
        register = findViewById(R.id.register);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFirstName = inputFirstName.getText().toString();
                String txtLastName = inputLastName.getText().toString();
                String txtEmail = inputMail.getText().toString();
                String txtPassword1 = inputPassword1.getText().toString();
                String txtPassword2 = inputPassword2.getText().toString();

                if (TextUtils.isEmpty(txtFirstName) || TextUtils.isEmpty(txtLastName)
                        || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword1) || TextUtils.isEmpty(txtPassword2)){
                    Toast.makeText(VolunteerUserSignUp.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (!(txtPassword1.equals(txtPassword2))){
                    Toast.makeText(VolunteerUserSignUp.this, "Incompatible Passwords!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword1.length() < 6){
                    Toast.makeText(VolunteerUserSignUp.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtFirstName , txtLastName , txtEmail , txtPassword1);
                }
            }
        });
    }
    private void registerUser(final String firstName, final String lastName, final String email, String password) {

        pd.setMessage("Please Wait!");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String , Object> map = new HashMap<>();
                map.put("First Name" , firstName);
                map.put("Last Name" , lastName);
                map.put("E-mail", email);
                map.put("id" , mAuth.getCurrentUser().getUid());

                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(VolunteerUserSignUp.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(VolunteerUserSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}