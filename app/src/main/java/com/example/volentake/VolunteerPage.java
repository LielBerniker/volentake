package com.example.volentake;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity Volunteer Home Page
 */
public class VolunteerPage extends AppCompatActivity {
    private Button edit;
    private Button addPost;
    private Button logOut;
    private Button edit_pic;
    private TextView FirstNameInsert;
    private TextView LastNameInsert;
    private TextView PhoneNumberInsert;
    private TextView EmailInsert;
    //    firebase
    private DatabaseReference mDatabase;
    String user_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_page);
//        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
             user_id = bun.getString("id");
        }
        edit_pic = (Button)findViewById(R.id.btnaddpic);
        edit = (Button)findViewById(R.id.btnEditVol);
        logOut = (Button)findViewById(R.id.btnLogOut);
        FirstNameInsert = (TextView)findViewById(R.id.firstnameinsert);
        LastNameInsert = (TextView)findViewById(R.id.lastnameinsert);
        PhoneNumberInsert = (TextView)findViewById(R.id.phonenumberinsert);
        EmailInsert = (TextView)findViewById(R.id.emailinsert);
        mDatabase.child("vol_users").child(user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                   Vol_user cure_user =  task.getResult().getValue(Vol_user.class);
                    FirstNameInsert.setText(cure_user.getFirst_name());
                    LastNameInsert.setText(cure_user.getLast_name());
                    PhoneNumberInsert.setText(cure_user.getPhone_num());
                    EmailInsert.setText(cure_user.getEmail());

                }
            }
        });


        edit.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, EditVolunteer.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });
        edit_pic.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, picture_edit_vol.class);
            intent.putExtra("id",user_id);
            startActivity(intent);
        });


        logOut.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, VolunteerLogIn.class);
            startActivity(intent);
        });
    }
}