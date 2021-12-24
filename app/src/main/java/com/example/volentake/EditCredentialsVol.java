package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Assoc_user;
import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCredentialsVol extends AppCompatActivity {

    private TextView volEmail, volCurrentPass, volNewPass, volConfirmNewPass;
    private Button backBtn, updateBtn;
    private String vol_user_id;
    private String vol_cur_email;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    AlertDialog.Builder builder;
    Vol_user vol_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credentials_vol);
        builder = new AlertDialog.Builder(this);
//        firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            vol_user_id = bun.getString("id");
            vol_cur_email = bun.getString("vol_email");
        }

        volEmail = (TextView) findViewById(R.id.email_activity_edit_credentials_vol);
        volCurrentPass = (TextView) findViewById(R.id.current_password_activity_edit_credentials_vol);
        volNewPass = (TextView) findViewById(R.id.new_password_activity_edit_credentials_vol);
        volConfirmNewPass = (TextView) findViewById(R.id.confirm_password_activity_edit_credentials_vol);

        backBtn = (Button) findViewById(R.id.back_activity_edit_credentials_vol);
        updateBtn = (Button) findViewById(R.id.update_btn_activity_edit_credentials_vol);

        volEmail.setText(vol_cur_email);
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditCredentialsVol.this, EditVolunteer.class);
            intent.putExtra("id", vol_user_id);
            startActivity(intent);
        });

        updateBtn.setOnClickListener(view -> {
            String cur_pass = volCurrentPass.getText().toString();
            String email_new = volEmail.getText().toString();
            String pass_new1 = volNewPass.getText().toString();
            String pass_new2 = volConfirmNewPass.getText().toString();
            if (TextUtils.isEmpty(cur_pass) || TextUtils.isEmpty(email_new ) || TextUtils.isEmpty(pass_new1)
                    || TextUtils.isEmpty(pass_new2)){
                Toast.makeText(EditCredentialsVol.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
            } else if (!(pass_new1.equals(pass_new2))){
                Toast.makeText(EditCredentialsVol.this, "Incompatible Passwords!", Toast.LENGTH_SHORT).show();
            } else if (pass_new1.length() < 6){
                Toast.makeText(EditCredentialsVol.this, "Password too short!", Toast.LENGTH_SHORT).show();
            } else {
                update_email_pass(cur_pass , email_new, pass_new1);
            }
        });


    }
    public void update_email_pass(String cur_pass,String email_new,String pass_new1) {
        mAuth.signInWithEmailAndPassword(vol_cur_email, cur_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
                                            mDatabase.child("vol_users").child(vol_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                    if (!task.isSuccessful()) {
                                                        Log.e("firebase", "Error getting data", task.getException());
                                                    } else {
                                                        vol_user = task.getResult().getValue(Vol_user.class);
                                                        vol_user.setEmail(email_new);
                                                        mDatabase.child("vol_users").child(vol_user_id).setValue(vol_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(EditCredentialsVol.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                                                    Intent intent = new Intent(EditCredentialsVol.this, VolunteerPage.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    intent.putExtra("id", vol_user_id);
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
                Toast.makeText(EditCredentialsVol.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itembacktouser:
                Intent intent1 = new Intent(EditCredentialsVol.this,VolunteerPage.class);
                intent1.putExtra("id",vol_user_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(EditCredentialsVol.this,VolunteerLogIn.class);
                                startActivity(intent1);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alterdialod = builder.create();
                alterdialod.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}