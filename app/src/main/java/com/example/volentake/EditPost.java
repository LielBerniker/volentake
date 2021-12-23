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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.Address;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Association_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPost extends AppCompatActivity {
    private Button update;
    private Button back_btn;
    private EditText NameInsert;
    private EditText PhoneNumberInsert;
    private EditText cityInsert;
    private EditText streetInsert;
    private EditText houseNumberInsert;
    private EditText postnumofvol;
    private EditText postdescreption;
    private Spinner spintype;
    private DatabaseReference mDatabase;
    AlertDialog.Builder builder;
    String assoc_id = "";
    String post_id = "";
    Assoc_post post_n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
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
        if (bun != null) {
            assoc_id = bun.getString("id");
            post_id = bun.getString("post_id");
        }
        update = (Button) findViewById(R.id.update_btn_activity_edit_post);
        back_btn = (Button) findViewById(R.id.back_btn_activity_edit_post);
        NameInsert = (EditText) findViewById(R.id.post_name_activity_edit_post);
        PhoneNumberInsert = (EditText) findViewById(R.id.phone_activity_edit_post);
        cityInsert = (EditText) findViewById(R.id.city_activity_edit_post);
        postnumofvol = (EditText) findViewById(R.id.num_of_vol_activity_edit_post);
        streetInsert = (EditText) findViewById(R.id.street_activity_edit_post);
        houseNumberInsert = (EditText) findViewById(R.id.house_num_activity_edit_post);
        postdescreption = (EditText) findViewById(R.id.description_activity_edit_post);

        spintype = (Spinner) findViewById(R.id.spinnerpostedittype);
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(EditPost.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(typeadapter);

        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    post_n = task.getResult().getValue(Assoc_post.class);
                    NameInsert.setText(post_n.getName());
                    PhoneNumberInsert.setText(post_n.getPhone_number());
                    cityInsert.setText(post_n.getLocation().getCity());
                    postnumofvol.setText(Integer.toString(post_n.getNum_of_participants()));
                    int spinnerPosition = typeadapter.getPosition(post_n.getType());
                    spintype.setSelection(spinnerPosition);
                    postdescreption.setText(post_n.getDescription());
                }
                progressDialog.dismiss();
            }
        });
        update.setOnClickListener(view -> {
            String txtName = NameInsert.getText().toString();
            String txtaddrresscity = cityInsert.getText().toString();
            String txtphonenum = PhoneNumberInsert.getText().toString();
            String txttype = spintype.getSelectedItem().toString();
            String txtpostnumofvol = postnumofvol.getText().toString();
            String txtpostdesc = postdescreption.getText().toString();
            update_post(txtName, txtaddrresscity, txtphonenum, txttype, txtpostnumofvol, txtpostdesc);
        });
        back_btn.setOnClickListener(view -> {
            Intent intent = new Intent(EditPost.this, DetailsPostAssociation.class);
            intent.putExtra("id", assoc_id);
            intent.putExtra("post_id", post_id);
            startActivity(intent);
        });
    }

    private void update_post(String name, String city, String phonenum, String type, String numofvol, String desc) {
        Address address = new Address(city, "s1", 2424);
        Association_post post_n2 = new Assoc_post(name, address, Integer.valueOf(numofvol), type, phonenum, assoc_id, desc);
        mDatabase.child("posts").child(post_id).setValue(post_n2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditPost.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditPost.this, DetailsPostAssociation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("assoc_id", assoc_id);
                    intent.putExtra("post_id", post_id);
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
                Intent intent1 = new Intent(EditPost.this, AssociationPage.class);
                intent1.putExtra("id", assoc_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(EditPost.this, AssociationLogIn.class);
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