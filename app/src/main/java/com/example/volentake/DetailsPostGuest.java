package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model_voluntake_class.Assoc_post;
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

public class DetailsPostGuest extends AppCompatActivity {
    private TextView PostName,PostCity,NumOfVol,PostType,PostPhoneNum, PostDescription;
    private Button back;
    private ImageView post_pic;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    String post_id = "";
    String assoc_id;
    int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post_guest);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
//        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null) {
            post_id = bun.getString("post_id");
            assoc_id = bun.getString("id");
            state= bun.getInt("state");
        }
        mystorge = FirebaseStorage.getInstance().getReference().child("post_description_pic/"+post_id);
        back = (Button) findViewById(R.id.btnBackToFeedOfPostsGuest);
        PostName = (TextView)findViewById(R.id.detailPostNameGuest);
        NumOfVol = (TextView)findViewById(R.id.detailNumVolPostGuest);
        PostPhoneNum = (TextView)findViewById(R.id.detailPhoneNumberPostGuest);
        PostDescription = (TextView)findViewById(R.id.detailDescriptionPostGuest);
        PostType = (TextView)findViewById(R.id.detailTypePostGuest);
        PostCity = (TextView)findViewById(R.id.detailLocationPostGuest);
        post_pic = (ImageView)findViewById(R.id.PostImage2Guest);
        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Assoc_post cur_post =  task.getResult().getValue(Assoc_post.class);
                    assert cur_post != null;
                    PostName.setText(cur_post.getName());
                    NumOfVol.setText(String.valueOf(cur_post.getNum_of_participants()));
                    PostPhoneNum.setText(cur_post.getPhone_number());
                    PostDescription.setText(cur_post.getDescription());
                    PostType.setText(cur_post.getType());
                    PostCity.setText(cur_post.getLocation().getCity());
                }
            }
        });
        mystorge.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final File localFile = File.createTempFile(post_id,"");
                    mystorge.getFile(localFile)
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
                }
                catch (IOException e){

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                post_pic.setImageResource(R.drawable.logo_01);
                progressDialog.dismiss();
            }
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsPostGuest.this, FeedPostGuest.class);
            intent.putExtra("state", state);
            intent.putExtra("id", assoc_id);
            startActivity(intent);
        });

    }
}