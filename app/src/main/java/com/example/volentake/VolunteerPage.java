package com.example.volentake;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.AdapterActivePostVol;
import com.example.myapplication.Assoc_post;
import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Activity Volunteer Home Page
 */
public class VolunteerPage extends AppCompatActivity {
    private Button edit;
    private Button logOut;
    private Button edit_pic;
    private Button SerchPost;
    private Button inboxResponse;
    private Button activePosts;
    private TextView FirstNameInsert;
    private TextView PhoneNumberInsert;
    private TextView EmailInsert;
    private ImageView profile_pic;
    AlertDialog.Builder builder;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    Vol_user cure_user;
    String user_id = "";
    ArrayList<String> deleted_posts_id = new ArrayList<>();
    String deleted_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_page);
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
        if (bun != null) {
            user_id = bun.getString("id");
        }
        mystorge = FirebaseStorage.getInstance().getReference().child("vol_profile_pic/" + user_id);
        edit_pic = (Button) findViewById(R.id.btnaddpic);
        SerchPost = (Button) findViewById(R.id.searchPostBTN);
        edit = (Button) findViewById(R.id.btnEditVol);
        logOut = (Button) findViewById(R.id.btnLogOut);
        inboxResponse = (Button) findViewById(R.id.btnInboxResponses);
        activePosts = (Button) findViewById(R.id.btnActivePosts);
        FirstNameInsert = (TextView) findViewById(R.id.firstnameinsert);
//
        PhoneNumberInsert = (TextView) findViewById(R.id.phonenumberinsert);
        EmailInsert = (TextView) findViewById(R.id.emailinsert);
        profile_pic = (ImageView) findViewById(R.id.imagevolprofile);
        mDatabase.child("vol_users").child(user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    cure_user = task.getResult().getValue(Vol_user.class);
                    assert cure_user != null;
                    FirstNameInsert.setText(cure_user.getFirst_name() + " " + cure_user.getLast_name());
                    PhoneNumberInsert.setText(cure_user.getPhone_num());
                    EmailInsert.setText(cure_user.getEmail());
                    int num_of_posts = cure_user.getActive_posts().size();
                    System.out.println("size of posts" + Integer.toString(num_of_posts));
                    if (num_of_posts != 1) {
                        for (int i = 1; i < num_of_posts; i++) {
                            int pos = i;
                            System.out.println(pos);
                            deleted_id = "0";
                            String cur_post_id = cure_user.getActive_posts().get(pos);
                            mDatabase.child("posts").child(cur_post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    } else {
                                        Assoc_post cur_post1 = task.getResult().getValue(Assoc_post.class);
                                        if(cur_post1==null)
                                        {
                                            deleted_posts_id.add(cur_post_id);
                                        }
                                        if (pos + 1 == num_of_posts) {
                                            for (int j = 0; j < deleted_posts_id.size(); j++) {
                                                System.out.println("noe deleteng post  " + deleted_posts_id.get(j));
                                                cure_user.active_posts.remove(deleted_posts_id.get(j));
                                            }
                                            mDatabase.child("vol_users").child(user_id).setValue(cure_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                    }
                                                }
                                            });
                                        }
                                    }
                                }


                            });
                        }
                    }

                }
                progressDialog.dismiss();
            }
        });
        mystorge.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final File localFile = File.createTempFile(user_id, "");
                    mystorge.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    final Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    profile_pic.setImageBitmap(bitmap);
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    });
                } catch (IOException e) {
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // File not found

            }
        });


        activePosts.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, FeedActivePostsVol.class);
            intent.putExtra("id", user_id);
            startActivity(intent);
        });


        edit.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, EditVolunteer.class);
            intent.putExtra("id", user_id);
            startActivity(intent);
        });
        SerchPost.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, MainSearchPostsVol.class);
            intent.putExtra("id", user_id);
            startActivity(intent);
        });
        edit_pic.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, picture_edit_vol.class);
            intent.putExtra("id", user_id);
            startActivity(intent);
        });
        inboxResponse.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerPage.this, InboxResponses.class);
            intent.putExtra("id", user_id);
            startActivity(intent);
        });


        logOut.setOnClickListener(view -> {
            builder.setTitle("logout")
                    .setMessage("you are about to log out")
                    .setCancelable(true)
                    .setPositiveButton("log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent1 = new Intent(VolunteerPage.this, VolunteerLogIn.class);
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
                return true;
            case R.id.itemlogout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}