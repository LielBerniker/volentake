package com.example.volentake;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Activity Association Home Page
 */
public class AssociationPage extends AppCompatActivity {
    private Button edit;
    private Button addPost;
    private Button logOut;
    //    firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_association_page);

        edit = (Button)findViewById(R.id.btnEdit);
        addPost = (Button)findViewById(R.id.addPost);
        logOut = (Button)findViewById(R.id.logOut);

        edit.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationPage.this, EditAssociation.class);
            startActivity(intent);
        });

        addPost.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationPage.this, Add_post.class);
            startActivity(intent);
        });

        logOut.setOnClickListener(view -> {
            Intent intent = new Intent(AssociationPage.this, AssociationLogIn.class);
            startActivity(intent);
        });
    }
}