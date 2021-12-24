package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Assoc_user;
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

public class EditCredentialsAssoc extends AppCompatActivity {

    private TextView volEmail, volCurrentPass, volNewPass, volConfirmNewPass;
    private Button backBtn, updateBtn;
    private String assoc_user_id;
    private String assoc_cur_email;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Assoc_user assoc_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credentials_assoc);
//        firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            assoc_user_id = bun.getString("id");
            assoc_cur_email = bun.getString("assoc_email");
        }

        volEmail = (TextView) findViewById(R.id.email_activity_edit_credentials_assoc);
        volCurrentPass = (TextView) findViewById(R.id.current_password_activity_edit_credentials_assoc);
        volNewPass = (TextView) findViewById(R.id.new_password_activity_edit_credentials_assoc);
        volConfirmNewPass = (TextView) findViewById(R.id.confirm_password_activity_edit_credentials_assoc);
        backBtn = (Button) findViewById(R.id.back_btn_activity_edit_credentials_assoc);
        updateBtn = (Button) findViewById(R.id.update_btn_activity_edit_credentials_assoc);
        volEmail.setText(assoc_cur_email);
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditCredentialsAssoc.this, EditAssociation.class);
            intent.putExtra("id", assoc_user_id);
            startActivity(intent);
        });

        updateBtn.setOnClickListener(view -> {
            String cur_pass = volCurrentPass.getText().toString();
            String email_new = volEmail.getText().toString();
            String pass_new1 = volNewPass.getText().toString();
            String pass_new2 = volConfirmNewPass.getText().toString();
            if (TextUtils.isEmpty(cur_pass) || TextUtils.isEmpty(email_new ) || TextUtils.isEmpty(pass_new1)
                    || TextUtils.isEmpty(pass_new2)){
                Toast.makeText(EditCredentialsAssoc.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
            } else if (!(pass_new1.equals(pass_new2))){
                Toast.makeText(EditCredentialsAssoc.this, "Incompatible Passwords!", Toast.LENGTH_SHORT).show();
            } else if (pass_new1.length() < 6){
                Toast.makeText(EditCredentialsAssoc.this, "Password too short!", Toast.LENGTH_SHORT).show();
            } else {
               update_email_pass(cur_pass , email_new, pass_new1);
            }
        });
    }
    public void update_email_pass(String cur_pass,String email_new,String pass_new1) {
        mAuth.signInWithEmailAndPassword(assoc_cur_email, cur_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth.getCurrentUser().updateEmail(email_new).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mAuth.getCurrentUser().updatePassword(pass_new1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mDatabase.child("assoc_users").child(assoc_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                    if (!task.isSuccessful()) {
                                                        Log.e("firebase", "Error getting data", task.getException());
                                                    } else {
                                                        assoc_user = task.getResult().getValue(Assoc_user.class);
                                                        assoc_user.setEmail(email_new);
                                                        mDatabase.child("assoc_users").child(assoc_user_id).setValue(assoc_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(EditCredentialsAssoc.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(EditCredentialsAssoc.this, AssociationPage.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    intent.putExtra("id", assoc_user_id);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }
                                                        });
                                                    }

                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditCredentialsAssoc.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}