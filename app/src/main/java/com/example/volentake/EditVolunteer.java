package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Address;
import com.example.myapplication.Vol_user;
import com.example.myapplication.Volunteer_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditVolunteer extends AppCompatActivity {
    private Button add_changes;
    private EditText FirstNameInsert;
    private EditText LastNameInsert;
    private EditText PhoneNumberInsert;
    private EditText EmailInsert;
    private EditText addresscityinsert;
    private EditText addressstreetinsert;
    private EditText addressnuminsert;
    private DatabaseReference mDatabase;
    String user_id = "";
    Vol_user cure_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_volunteer);
        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            user_id = bun.getString("id");
        }
        add_changes = (Button)findViewById(R.id.btnPageRequst);
        FirstNameInsert = (EditText)findViewById(R.id.firstnameinsert3);
        LastNameInsert = (EditText)findViewById(R.id.inputLastName2);
        PhoneNumberInsert = (EditText)findViewById(R.id.phunenumberinsert3);
        addresscityinsert = (EditText)findViewById(R.id.inputcity);
        addressstreetinsert = (EditText)findViewById(R.id.streetinsert);
        addressnuminsert = (EditText)findViewById(R.id.addressnumberinsert);
        EmailInsert = (EditText)findViewById(R.id.emailinsert3);
        mDatabase.child("vol_users").child(user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    cure_user =  task.getResult().getValue(Vol_user.class);
                    FirstNameInsert.setText(cure_user.getFirst_name());
                    LastNameInsert.setText(cure_user.getLast_name());
                    PhoneNumberInsert.setText(cure_user.getPhone_num());
                    EmailInsert.setText(cure_user.getEmail());
                    addresscityinsert.setText(cure_user.getAddress().getCity());
                    addressstreetinsert.setText(cure_user.getAddress().getStreet());
                    addressnuminsert.setText(Integer.toString(cure_user.getAddress().getNumber()));

                }
            }
        });
        add_changes.setOnClickListener(view -> {
            String txtFirstName = FirstNameInsert.getText().toString();
            String txtLastName = LastNameInsert.getText().toString();
            String txtaddrresscity = addresscityinsert.getText().toString();
            String txtaddrressstreet = addressstreetinsert.getText().toString();
            int txtaddrressnum = Integer.parseInt(addressnuminsert.getText().toString());
            String txtphonenum = PhoneNumberInsert.getText().toString();
            update_data(txtFirstName,txtLastName,txtaddrresscity,txtaddrressstreet,txtaddrressnum,txtphonenum);
        });

    }
    private void update_data(String fname,String lname,String city,String street,int streetnum, String phonenum)
    {
        Address address = new Address(city,street,streetnum);
        Volunteer_user cur_user2 = new Vol_user(fname,lname,address,phonenum,cure_user.getBirth_date(),cure_user.getEmail());
        mDatabase.child("vol_users").child(user_id).setValue(cur_user2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(EditVolunteer.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditVolunteer.this, VolunteerPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id",user_id);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}