package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Address;
import com.example.myapplication.Vol_user;
import com.example.myapplication.Volunteer_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditVolunteer extends AppCompatActivity {
    private Button add_changes;
    private Button backBtn;
    private Button editCredentialsBtn;
    private EditText FirstNameInsert;
    private EditText LastNameInsert;
    private EditText PhoneNumberInsert;
    private EditText EmailInsert;
    private EditText addresscityinsert;
    private EditText addressstreetinsert;
    private EditText addressnuminsert;
    private DatabaseReference mDatabase;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    AlertDialog.Builder builder;
    String user_id = "";
    Vol_user cure_user;
    String BirthDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_volunteer);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        builder = new AlertDialog.Builder(this);
        // firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            user_id = bun.getString("id");
        }
        add_changes = (Button) findViewById(R.id.update_btn_back_btn_activity_edit_volunteer);
        backBtn = (Button) findViewById(R.id.back_btn_activity_edit_volunteer);
        editCredentialsBtn = (Button) findViewById(R.id.edit_credentials_btn_activity_edit_volunteer);
        FirstNameInsert = (EditText) findViewById(R.id.firstnameinsert3);
        LastNameInsert = (EditText) findViewById(R.id.inputLastName2);
        PhoneNumberInsert = (EditText) findViewById(R.id.phunenumberinsert3);
        addresscityinsert = (EditText) findViewById(R.id.inputcity);
        addressstreetinsert = (EditText) findViewById(R.id.streetinsert);
        addressnuminsert = (EditText) findViewById(R.id.addressnumberinsert);
        mDisplayDate = (TextView) findViewById(R.id.inputdateselector2);
        mDatabase.child("vol_users").child(user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    cure_user = task.getResult().getValue(Vol_user.class);
                    FirstNameInsert.setText(cure_user.getFirst_name());
                    LastNameInsert.setText(cure_user.getLast_name());
                    PhoneNumberInsert.setText(cure_user.getPhone_num());
                    addresscityinsert.setText(cure_user.getAddress().getCity());
                    addressstreetinsert.setText(cure_user.getAddress().getStreet());
                    addressnuminsert.setText(Integer.toString(cure_user.getAddress().getNumber()));
                    mDisplayDate.setText(cure_user.getBirth_date());

                }
                progressDialog.dismiss();
            }
        });
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditVolunteer.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                BirthDay = day + "/" + month + "/" + year;
                mDisplayDate.setText(BirthDay);
            }
        };
        add_changes.setOnClickListener(view -> {
            String txtFirstName = FirstNameInsert.getText().toString();
            String txtLastName = LastNameInsert.getText().toString();
            String txtaddrresscity = addresscityinsert.getText().toString();
            String txtaddrressstreet = addressstreetinsert.getText().toString();
            int txtaddrressnum = Integer.parseInt(addressnuminsert.getText().toString());
            String txtphonenum = PhoneNumberInsert.getText().toString();
            String txtbirhday = mDisplayDate.getText().toString();
            update_data(txtFirstName, txtLastName, txtaddrresscity, txtaddrressstreet, txtaddrressnum, txtphonenum, txtbirhday);
        });
        editCredentialsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditVolunteer.this, EditCredentialsVol.class);
            intent.putExtra("id", user_id);
            startActivity(intent);
        });
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditVolunteer.this, VolunteerPage.class);
            intent.putExtra("id", user_id);
            startActivity(intent);

        });
    }

    private void update_data(String fname, String lname, String city, String street, int streetnum, String phonenum, String birthdaycur) {
        Address address = new Address(city, street, streetnum);
        Volunteer_user cur_user2 = new Vol_user(fname, lname, address, phonenum, birthdaycur, cure_user.getEmail());
        mDatabase.child("vol_users").child(user_id).setValue(cur_user2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditVolunteer.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditVolunteer.this, VolunteerPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", user_id);
                    startActivity(intent);
                    finish();
                }
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
                Intent intent1 = new Intent(EditVolunteer.this, VolunteerPage.class);
                intent1.putExtra("id", user_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(EditVolunteer.this, VolunteerLogIn.class);
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