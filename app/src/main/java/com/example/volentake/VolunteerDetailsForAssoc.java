package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.model_voluntake_class.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VolunteerDetailsForAssoc extends AppCompatActivity {

    private DatabaseReference mRootRef;
    AlertDialog.Builder builder;
    private TextView FirstName;
    private TextView LastName;
    private TextView Age;
    private TextView Email;
    private TextView PhoneNumber;
    private TextView City;
    private Button back;
    String  request_id ;
    String assoc_id = "";
    String post_name = "";
    String vol_user_name = "";
    String vol_user_email = "";
    String vol_user_id = "";
    String post_id = "";
    int status_def;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_details_for_assoc);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        builder = new AlertDialog.Builder(this);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            request_id = bun.getString("request_id");
            assoc_id = bun.getString("assoc_id");
            post_name = bun.getString("post_name");
            vol_user_name = bun.getString("vol_user_name");
            vol_user_email = bun.getString("vol_user_email");
            vol_user_id = bun.getString("vol_user_id");
            status_def =  bun.getInt("status_def");
            post_id =  bun.getString("post_id");
        }
        back = (Button)findViewById(R.id.backtoassocmassagespc);
        FirstName = (TextView) findViewById(R.id.VolunteerDetailsForAssocFirstName);
        LastName = (TextView) findViewById(R.id.VolunteerDetailsForAssocLastName);
        Age = (TextView) findViewById(R.id.VolunteerDetailsForAssocAge);
        Email = (TextView) findViewById(R.id.VolunteerDetailsForAssocEmail);
        PhoneNumber = (TextView) findViewById(R.id.VolunteerDetailsForAssocPhoneNumber);
        City = (TextView) findViewById(R.id.VolunteerDetailsForAssocCity);
        mRootRef.child("vol_users").child(vol_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Vol_user cur_vol_user = task.getResult().getValue(Vol_user.class);
                    FirstName.setText(cur_vol_user.getFirst_name());
                    LastName.setText(cur_vol_user.getLast_name());
                    Age.setText(cur_vol_user.getBirth_date());
                    Email.setText(cur_vol_user.getEmail());
                    PhoneNumber.setText(cur_vol_user.getPhone_num());
                    City.setText(cur_vol_user.getAddress().getCity());
                }
                progressDialog.dismiss();
            }
        });

        back.setOnClickListener(view -> {
            if(status_def==0) {
                Intent intent = new Intent(VolunteerDetailsForAssoc.this, DetailsRequest.class);
                intent.putExtra("request_id", request_id);
                intent.putExtra("assoc_id", assoc_id);
                intent.putExtra("post_name", post_name);
                intent.putExtra("vol_user_name", vol_user_name);
                intent.putExtra("vol_user_email", vol_user_email);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(VolunteerDetailsForAssoc.this, details_request_afterans.class);
                intent.putExtra("request_id", request_id);
                intent.putExtra("assoc_id", assoc_id);
                intent.putExtra("post_name", post_name);
                intent.putExtra("vol_user_name", vol_user_name);
                intent.putExtra("vol_user_email", vol_user_email);
                intent.putExtra("post_id", post_id);
                startActivity(intent);
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
                Intent intent1 = new Intent(VolunteerDetailsForAssoc.this,AssociationPage.class);
                intent1.putExtra("id",assoc_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(VolunteerDetailsForAssoc.this,AssociationLogIn.class);
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