package com.example.volentake;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AssociationLogIn extends AppCompatActivity {

    // buttons
    private Button associationLoginBtn;
    private Button associationCreateUser;
    private Button associationLoginBack;

    // insert details for user signIn
    private TextView associationEmailEditText;
    private TextView associationPasswordEditText;
    //    firebase connect
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_log_in);

        associationLoginBtn = (Button) findViewById(R.id.associationloginbtn);
        associationCreateUser = (Button) findViewById(R.id.associationcreateuser);
        associationLoginBack = (Button) findViewById(R.id.associationloginback);

        associationEmailEditText = (TextView) findViewById(R.id.associationuseremail);
        associationPasswordEditText = (TextView) findViewById(R.id.associationpassword);
//        firebase
        mAuth = FirebaseAuth.getInstance();

        associationLoginBtn.setOnClickListener(view -> {


            String email = associationEmailEditText.getText().toString();
            String password = associationPasswordEditText.getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(AssociationLogIn.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
            }else if (password.length() < 6){
                Toast.makeText(AssociationLogIn.this, "Password too short!", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email , password);
            }

        });

        associationCreateUser.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationLogIn.this, AssociationSignUp.class);
            startActivity(intent);
        });

        associationLoginBack.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationLogIn.this, MainActivity.class);
            startActivity(intent);
        });
    }
    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AssociationLogIn.this, "successfully log in " , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AssociationLogIn.this, AssociationPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id",mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AssociationLogIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}