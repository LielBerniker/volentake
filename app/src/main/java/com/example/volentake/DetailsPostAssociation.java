package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class DetailsPostAssociation extends AppCompatActivity {
    private TextView PostName,PostCity,PostStreet,Poststnum,NumOfVol,PostType,PostPhoneNum, PostDescription;
    private Button back, btnForEditPost;
    private ImageView post_pic;
    //    firebase
    private DatabaseReference mDatabase;
    private StorageReference mystorge;
    String association_user_id = "";
    String post_id = "";
    String assoc_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post_association);

        back = (Button) findViewById(R.id.btnBackToFeedOfPosts);
        btnForEditPost =  (Button) findViewById(R.id.btnBackToFeedOfPostsAss);
    }
}