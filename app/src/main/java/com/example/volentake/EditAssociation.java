package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Address;
import com.example.myapplication.Assoc_user;
import com.example.myapplication.Association_user;
import com.example.myapplication.Vol_user;
import com.example.myapplication.Volunteer_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditAssociation extends AppCompatActivity {
    private Button add_changes;
    private Button backBtn;
    private Button editCredentialsBtn;
    private EditText NameInsert;
    private EditText PhoneNumberInsert;
    private EditText addresscityinsert;
    private EditText addressstreetinsert;
    private EditText addressnuminsert;
    private EditText assocInfo;
    private DatabaseReference mDatabase;
    AlertDialog.Builder builder;
    String assoc_id = "";
    Assoc_user assoc_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_association);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        builder = new AlertDialog.Builder(this);
        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            assoc_id = bun.getString("id");
        }
        add_changes = (Button)findViewById(R.id.btnaddchanges2);
        backBtn = (Button)findViewById(R.id.back_btn_activity_edit_association);
        editCredentialsBtn = (Button)findViewById(R.id.edit_credentials_btn_activity_edit_association);
        NameInsert = (EditText)findViewById(R.id.inputName);

        PhoneNumberInsert = (EditText)findViewById(R.id.inputPhone);
        addresscityinsert = (EditText)findViewById(R.id.cityinsert2);
        addressstreetinsert = (EditText)findViewById(R.id.streetinsert2);
        addressnuminsert = (EditText)findViewById(R.id.streetnuminsert2);
        assocInfo = (EditText)findViewById(R.id.associnfoinsert);
        mDatabase.child("assoc_users").child(assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    assoc_user =  task.getResult().getValue(Assoc_user.class);
                    NameInsert.setText(assoc_user.getName());
                    PhoneNumberInsert.setText(assoc_user.getPhone_num());
                    addresscityinsert.setText(assoc_user.getAddress().getCity());
                    addressstreetinsert.setText(assoc_user.getAddress().getStreet());
                    addressnuminsert.setText(Integer.toString(assoc_user.getAddress().getNumber()));
                    assocInfo.setText(assoc_user.getAbout());

                }
                progressDialog.dismiss();
            }
        });
        add_changes.setOnClickListener(view -> {
            String txtName = NameInsert.getText().toString();
            String txtaddrresscity = addresscityinsert.getText().toString();
            String txtaddrressstreet = addressstreetinsert.getText().toString();
            String  txtaddrressnum = addressnuminsert.getText().toString();
            String txtphonenum = PhoneNumberInsert.getText().toString();
            String txtabout = assocInfo.getText().toString();
            if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtaddrresscity) || TextUtils.isEmpty(txtaddrressstreet) || TextUtils.isEmpty(txtaddrressnum) || TextUtils.isEmpty(txtphonenum)
                    || TextUtils.isEmpty(txtabout) ) {
                Toast.makeText(EditAssociation.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
            }
            else {
                update_data(txtName, txtaddrresscity, txtaddrressstreet, txtaddrressnum, txtphonenum, txtabout);
            }
        });

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditAssociation.this, AssociationPage.class);
            intent.putExtra("id", assoc_id);
            startActivity(intent);
        });

        editCredentialsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditAssociation.this, EditCredentialsAssoc.class);
            intent.putExtra("id", assoc_id);
            startActivity(intent);
        });
    }
    private void update_data(String name,String city,String street,String  streetnum, String phonenum,String about)
    {
        Address address = new Address(city,street,Integer.parseInt(streetnum));
        assoc_user.setAddress(address);
        assoc_user.setName(name);
        assoc_user.setPhone_num(phonenum);
        assoc_user.setAbout(about);
        mDatabase.child("assoc_users").child(assoc_id).setValue(assoc_user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(EditAssociation.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditAssociation.this, AssociationPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id",assoc_id);
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
                Intent intent1 = new Intent(EditAssociation.this,AssociationPage.class);
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
                                Intent intent1 = new Intent(EditAssociation.this, AssociationLogIn.class);
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