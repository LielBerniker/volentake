package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Assoc_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class DetailsPostAssociation extends AppCompatActivity {
    private TextView PostName, PostCity, PostStreet, Poststnum, NumOfVol, PostType, PostPhoneNum, PostDescription;
    private Button back, btnForEditPost, editPic;
    private ImageView post_pic;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference myStorage;
    AlertDialog.Builder builder;
    String association_user_id = "";
    String post_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post_association);
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
            association_user_id = bun.getString("assoc_id");
            post_id = bun.getString("post_id");
        }
        myStorage = FirebaseStorage.getInstance().getReference().child("post_description_pic/" + post_id);
        back = (Button) findViewById(R.id.btnBackToFeedOfPostsAss);
        editPic = (Button) findViewById(R.id.btneditpostpic);
        btnForEditPost = (Button) findViewById(R.id.btnPageEditPostsAss);
        PostName = (TextView) findViewById(R.id.detailPostNameAss);
        NumOfVol = (TextView) findViewById(R.id.detailNumVolPostAss);
        PostPhoneNum = (TextView) findViewById(R.id.detailPhoneNumberPostAss);
        PostDescription = (TextView) findViewById(R.id.detailDescriptionPostAss);
        PostType = (TextView) findViewById(R.id.detailTypePostAss);
        post_pic = (ImageView) findViewById(R.id.PostImage2Ass);
        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    Assoc_post cur_post = task.getResult().getValue(Assoc_post.class);
                    assert cur_post != null;
                    PostName.setText(cur_post.getName());
                    NumOfVol.setText(String.valueOf(cur_post.getNum_of_participants()));
                    PostPhoneNum.setText(cur_post.getPhone_number());
                    PostDescription.setText(cur_post.getDescription());
                    PostType.setText(cur_post.getType());
                }
            }
        });
        myStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final File localFile = File.createTempFile(post_id, "");
                    myStorage.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                    final Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    post_pic.setImageBitmap(bitmap);
                                    progressDialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    });
                } catch (IOException e) {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progressDialog.dismiss();
            }
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsPostAssociation.this, AssociationPage.class);
            intent.putExtra("id", association_user_id);
            startActivity(intent);
        });
        btnForEditPost.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsPostAssociation.this, EditPost.class);
            intent.putExtra("id", association_user_id);
            intent.putExtra("post_id", post_id);
            startActivity(intent);
        });
        editPic.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsPostAssociation.this, picture_edit_post.class);
            intent.putExtra("assoc_id", association_user_id);
            intent.putExtra("post_id", post_id);
            startActivity(intent);
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
                Intent intent1 = new Intent(DetailsPostAssociation.this, AssociationPage.class);
                intent1.putExtra("id", association_user_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(DetailsPostAssociation.this, AssociationLogIn.class);
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