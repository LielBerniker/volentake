package com.example.volentake;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Assoc_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity Association Home Page
 */
public class AssociationPage extends AppCompatActivity {
    private Button edit;
    private Button addPost;
    private Button logOut;
    private Button see_posts;
    private Button inboxRequests;
//    private TextView Nametxt;
//    private TextView PhoneNumbertxt;
//    private TextView Emailtxt;
    private TextView NameInsert;
    private TextView PhoneNumberInsert;
    private TextView EmailInsert;
    String assoc_id ;
    AlertDialog.Builder builder;
    //    firebase
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_page);
        builder = new AlertDialog.Builder(this);
//        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
//        get information bundle
        Bundle bun = null;

        bun = getIntent().getExtras();
        if(bun != null)
        {
            assoc_id = bun.getString("id");
        }
        edit = (Button)findViewById(R.id.btnEdit);
        addPost = (Button)findViewById(R.id.addPost);
        logOut = (Button)findViewById(R.id.btnBackToFeedOfPosts);
        see_posts = (Button)findViewById(R.id.btnseeallposts);
        inboxRequests = (Button)findViewById(R.id.btnInboxResponses);
//        Nametxt = (TextView)findViewById(R.id.nametxt2);
//        PhoneNumbertxt = (TextView)findViewById(R.id.phonenumbertxt2);
//        Emailtxt = (TextView)findViewById(R.id.emailtxt2);
        NameInsert = (TextView)findViewById(R.id.nameinsert2);
        PhoneNumberInsert = (TextView)findViewById(R.id.phonenumberinsert2);
        EmailInsert = (TextView)findViewById(R.id.emailinsert2);
        mDatabase.child("assoc_users").child(assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Assoc_user cure_user =  task.getResult().getValue(Assoc_user.class);
                    NameInsert.setText(cure_user.getName());
                    PhoneNumberInsert.setText(cure_user.getPhone_num());
                    EmailInsert.setText(cure_user.getEmail());
                    progressDialog.dismiss();
                }
            }
        });
        edit.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationPage.this, EditAssociation.class);
            intent.putExtra("id",assoc_id);
            startActivity(intent);
        });

        addPost.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationPage.this, Add_post.class);
            intent.putExtra("id",assoc_id);
            startActivity(intent);
        });

        logOut.setOnClickListener(view -> {
            builder.setTitle("logout")
                    .setMessage("you are about to log out")
                    .setCancelable(true)
                    .setPositiveButton("log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1 = new Intent(AssociationPage.this,  AssociationLogIn.class);
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
        });
        see_posts.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationPage.this, FeedPostsAssociation.class);
            intent.putExtra("id",assoc_id);
            startActivity(intent);
        });
        inboxRequests.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationPage.this, InboxAssociation.class);
            intent.putExtra("id",assoc_id);
            startActivity(intent);
        });

    }
}