package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Address;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Assoc_user;
import com.example.myapplication.Association_post;
import com.example.myapplication.Association_user;
import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Add_post extends AppCompatActivity {
    private EditText inputName;
    private EditText inputCity;
    private EditText inputStreet;
    private EditText inputNum;
    private EditText inputPhone;
    private EditText inputDescription;
    private EditText inputType;
    private EditText inputNumOfParticipants;
    private Button addPost;
    private Button backToAssoc;
    //    firebase
    private DatabaseReference mDatabase;

    String assoc_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            assoc_id = bun.getString("id");
        }

        inputName =(EditText)  findViewById(R.id.inputPostName);
        inputCity = (EditText) findViewById(R.id.inputcity3);
        inputStreet = (EditText) findViewById(R.id.inputstreet3);
        inputNum = (EditText) findViewById(R.id.addressnuminput3);
        inputPhone = (EditText) findViewById(R.id.InputPhoneNum);
        inputDescription = (EditText) findViewById(R.id.InputDescreption);
        inputType = (EditText) findViewById(R.id.InputType);
        inputNumOfParticipants = (EditText) findViewById(R.id.InputNumOfPartic);
        addPost = (Button)findViewById(R.id.btnCreatePost);
        backToAssoc = (Button)findViewById(R.id.btnBackToAssoc);

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = inputName.getText().toString();
                String txtPhone = inputPhone.getText().toString();
                String txtDescription = inputDescription.getText().toString();
                String txtcity = inputCity.getText().toString();
                String txtstreet = inputStreet.getText().toString();
                String txtnum = inputNum.getText().toString();
                String txtType = inputType.getText().toString();
                String txtnumofpar = inputNumOfParticipants.getText().toString();

                if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtPhone ) || TextUtils.isEmpty(txtcity)|| TextUtils.isEmpty(txtstreet)|| TextUtils.isEmpty(txtnum)
                        || TextUtils.isEmpty(txtDescription) || TextUtils.isEmpty(txtType) || TextUtils.isEmpty(txtnumofpar)) {
                    Toast.makeText(Add_post.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    add_post_info(txtName , txtPhone , txtDescription , txtType,txtcity,txtstreet,txtnum,txtnumofpar);
                }

            }
        });
        backToAssoc.setOnClickListener(view -> {
            Intent intent = new Intent(Add_post.this, AssociationPage.class);
            startActivity(intent);
        });

    }
    public void add_post_info(String txtName , String txtPhone ,String txtDescription ,String txtType,String txtcity,String txtstreet,String txtnum,String txtnumofpar)
    {
        Address address = new Address(txtcity,txtstreet,Integer.parseInt(txtnum));
       Association_post cur_post = new Assoc_post(txtName,address,Integer.parseInt(txtnumofpar),txtType,txtPhone,assoc_id);
        mDatabase.child("posts").push().setValue(cur_post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    String post_id = mDatabase.getKey();
                    mDatabase.child("assoc_users").child(assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                               Assoc_user assoc_user2 =  task.getResult().getValue(Assoc_user.class);
                                    assoc_user2.posts.add(post_id);
                                mDatabase.child("assoc_users").child(assoc_id).setValue(assoc_user2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Add_post.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Add_post.this, AssociationPage.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.putExtra("id",assoc_id);
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