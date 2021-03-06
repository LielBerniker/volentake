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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class VolunteerLogIn extends AppCompatActivity {

    // buttons
    private Button volunteerLoginBtn;
    private Button volunteerCreateUser;
    private Button volunteerLoginBack;
    // insert details for user signIn
    private TextView volunteerEmailEditText;
    private TextView volunteerPasswordEditText;
    String user_id;
//    firebase connect
private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_log_in);

        volunteerLoginBtn = (Button) findViewById(R.id.volunteerloginbtn);
        volunteerCreateUser = (Button) findViewById(R.id.volunteercreateuser);
        volunteerLoginBack = (Button) findViewById(R.id.volunteerloginback);

        volunteerEmailEditText = (TextView) findViewById(R.id.volunteeruseremail);
        volunteerPasswordEditText = (TextView) findViewById(R.id.volunteeruserpassword);

//        firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("vol_users");
        volunteerLoginBtn.setOnClickListener(view -> {

            String email = volunteerEmailEditText.getText().toString();
            String password = volunteerPasswordEditText.getText().toString();
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(VolunteerLogIn.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
            }else if (password.length() < 6){
                Toast.makeText(VolunteerLogIn.this, "Password too short!", Toast.LENGTH_SHORT).show();
            }
            else {
                loginUser(email , password);
            }

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
    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    user_id = mAuth.getCurrentUser().getUid();
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(user_id)) {
                                Toast.makeText(VolunteerLogIn.this, "successfully log in " , Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(VolunteerLogIn.this, VolunteerPage.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("id",mAuth.getCurrentUser().getUid());
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(VolunteerLogIn.this,"not a volunteer user!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VolunteerLogIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
