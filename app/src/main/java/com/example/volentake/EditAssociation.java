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
    private EditText NameInsert;
    private EditText PhoneNumberInsert;
    private EditText EmailInsert;
    private EditText addresscityinsert;
    private EditText addressstreetinsert;
    private EditText addressnuminsert;
    private EditText assocInfo;
    private DatabaseReference mDatabase;
    String assoc_id = "";
    Assoc_user assoc_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_association);
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
                    EmailInsert.setText(assoc_user.getEmail());
                    addresscityinsert.setText(assoc_user.getAddress().getCity());
                    addressstreetinsert.setText(assoc_user.getAddress().getStreet());
                    addressnuminsert.setText(Integer.toString(assoc_user.getAddress().getNumber()));
                    assocInfo.setText(assoc_user.getAbout());

                }
            }
        });
        add_changes.setOnClickListener(view -> {
            String txtName = NameInsert.getText().toString();
            String txtaddrresscity = addresscityinsert.getText().toString();
            String txtaddrressstreet = addressstreetinsert.getText().toString();
            int txtaddrressnum = Integer.parseInt(addressnuminsert.getText().toString());
            String txtphonenum = PhoneNumberInsert.getText().toString();
            String txtabout = assocInfo.getText().toString();
            update_data(txtName,txtaddrresscity,txtaddrressstreet,txtaddrressnum,txtphonenum,txtabout);
        });
    }
    private void update_data(String name,String city,String street,int streetnum, String phonenum,String about)
    {
        Address address = new Address(city,street,streetnum);
        Association_user assoc_user2 = new Assoc_user(phonenum,address,name,assoc_user.getEmail(),about);
        mDatabase.child("assoc_users").child(assoc_id).setValue(assoc_user2).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}