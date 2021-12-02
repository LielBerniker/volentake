package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DetailsPost extends AppCompatActivity {

    private Button back, btnPageRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_post);

        back = (Button) findViewById(R.id.btnBackToFeedOfPosts);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsPost.this, searchPosts.class);
            startActivity(intent);
        });
    }
}