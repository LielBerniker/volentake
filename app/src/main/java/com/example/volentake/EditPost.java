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
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Assoc_user;
import com.example.myapplication.Association_post;
import com.example.myapplication.Association_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPost extends AppCompatActivity {
    private Button update;
    private EditText NameInsert;
    private EditText PhoneNumberInsert;
    private EditText addresscityinsert;
    private EditText addressstreetinsert;
    private EditText addressnuminsert;
    private EditText posttype;
    private EditText postnumofvol;
    private EditText postdescreption;
    private DatabaseReference mDatabase;
    String assoc_id = "";
    String post_id = "";
    Assoc_post post_n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            assoc_id = bun.getString("id");
            post_id = bun.getString("post_id");
        }
        update = (Button)findViewById(R.id.btnUpdatePost);
        NameInsert = (EditText)findViewById(R.id.editPostName);
        PhoneNumberInsert = (EditText)findViewById(R.id.editPostPhone);
        addresscityinsert = (EditText)findViewById(R.id.editPostLocation);
        postnumofvol= (EditText)findViewById(R.id.editPostNumVol);
        posttype= (EditText)findViewById(R.id.editPostType);
//        addressstreetinsert = (EditText)findViewById(R.id.streetinsert2);
//        addressnuminsert = (EditText)findViewById(R.id.streetnuminsert2);
        postdescreption= (EditText)findViewById(R.id.editPostDescription);
        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    post_n =  task.getResult().getValue(Assoc_post.class);
                    NameInsert.setText(post_n.getName());
                    PhoneNumberInsert.setText(post_n.getPhone_number());
                    addresscityinsert.setText(post_n.getLocation().getCity());
                    postnumofvol.setText(Integer.toString(post_n.getNum_of_participants()));
                    posttype.setText(post_n.getType());
                    postdescreption.setText(post_n.getDescription());
                }
            }
        });
        update.setOnClickListener(view -> {
            String txtName = NameInsert.getText().toString();
            String txtaddrresscity = addresscityinsert.getText().toString();
            String txtphonenum = PhoneNumberInsert.getText().toString();
            String txttype = posttype.getText().toString();
            String txtpostnumofvol = postnumofvol.getText().toString();
            String txtpostdesc = postdescreption.getText().toString();
            update_post(txtName,txtaddrresscity,txtphonenum,txttype,txtpostnumofvol,txtpostdesc);
        });
    }
    private void update_post(String name,String city, String phonenum,String type,String numofvol ,String desc)
    {
        Address address = new Address(city,"s1",2424);
        Association_post post_n2 = new Assoc_post(name,address,Integer.valueOf(numofvol),type,phonenum,assoc_id,desc);
        mDatabase.child("posts").child(post_id).setValue(post_n2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(EditPost.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditPost.this, DetailsPostAssociation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("assoc_id",assoc_id);
                    intent.putExtra("post_id",post_id);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}