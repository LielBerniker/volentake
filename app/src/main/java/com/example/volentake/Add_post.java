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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.model_voluntake_class.Address;
import com.example.model_voluntake_class.Assoc_post;
import com.example.model_voluntake_class.Assoc_user;
import com.example.model_voluntake_class.Association_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Add_post extends AppCompatActivity {
    private EditText inputName;
    private EditText inputCity;
    private EditText inputStreet;
    private EditText inputNum;
    private EditText inputPhone;
    private EditText inputDescription;
    private EditText inputNumOfParticipants;
    private Button addPost;
    private Button backToAssoc;
    private Spinner spintype;
    //    firebase
    private DatabaseReference mDatabase;
    AlertDialog.Builder builder;
    String assoc_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
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

        inputName =(EditText)  findViewById(R.id.inputPostName);
        inputCity = (EditText) findViewById(R.id.inputcity3);
        inputStreet = (EditText) findViewById(R.id.inputstreet3);
        inputNum = (EditText) findViewById(R.id.addressnuminput3);
        inputPhone = (EditText) findViewById(R.id.InputPhoneNum);
        inputDescription = (EditText) findViewById(R.id.InputDescreption);
        inputNumOfParticipants = (EditText) findViewById(R.id.InputNumOfPartic);
        addPost = (Button)findViewById(R.id.btnCreatePost);
        backToAssoc = (Button)findViewById(R.id.btnBackToAssoc);

        spintype = (Spinner) findViewById(R.id.spinnerporttype);
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(Add_post.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.types));
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(typeadapter);


        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = inputName.getText().toString();
                String txtPhone = inputPhone.getText().toString();
                String txtDescription = inputDescription.getText().toString();
                String txtcity = inputCity.getText().toString().toLowerCase(Locale.ROOT);
                String txtstreet = inputStreet.getText().toString();
                String txtnum = inputNum.getText().toString();
                String txtType = spintype.getSelectedItem().toString();
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
            intent.putExtra("id",assoc_id);
            startActivity(intent);
        });

    }
    public void add_post_info(String txtName , String txtPhone ,String txtDescription ,String txtType,String txtcity,String txtstreet,String txtnum,String txtnumofpar)
    {
        Address address = new Address(txtcity,txtstreet,Integer.parseInt(txtnum));
       Association_post cur_post = new Assoc_post(txtName,address,Integer.parseInt(txtnumofpar),txtType,txtPhone,assoc_id,txtDescription);
        String post_id =  mDatabase.child("posts").push().getKey();
        mDatabase.child("posts").child(post_id).setValue(cur_post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

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
                Intent intent1 = new Intent(Add_post.this,AssociationPage.class);
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
                                Intent intent1 = new Intent(Add_post.this, AssociationLogIn.class);
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