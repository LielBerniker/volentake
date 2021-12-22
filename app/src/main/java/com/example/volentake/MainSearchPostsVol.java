package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainSearchPostsVol extends AppCompatActivity {

    private Button btnCityMainSearchPostsVol, btnTypeMainSearchPostsVol;
    private EditText editTextCityMainSearchPostsVol, editTextTypeMainSearchPostsVol;
    private TextView textViewCityMainSearchPostsVol, textViewTypeMainSearchPostsVol, titleMainSearchPostsVol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search_posts_vol);

        btnCityMainSearchPostsVol = (Button)findViewById(R.id.btnCityMainSearchPostsVol);
        btnTypeMainSearchPostsVol = (Button)findViewById(R.id.btnTypeMainSearchPostsVol);
        editTextCityMainSearchPostsVol = (EditText)findViewById(R.id.editTextCityMainSearchPostsVol);
        editTextTypeMainSearchPostsVol = (EditText)findViewById(R.id.editTextTypeMainSearchPostsVol);
        textViewCityMainSearchPostsVol = (TextView) findViewById(R.id.textViewCityMainSearchPostsVol);
        textViewTypeMainSearchPostsVol = (TextView) findViewById(R.id.textViewTypeMainSearchPostsVol);
        titleMainSearchPostsVol = (TextView)findViewById(R.id.titleMainSearchPostsVol);

        btnCityMainSearchPostsVol.setOnClickListener(view -> {
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVolByCity.class);
//            intent.putExtra("id",user_id);
            startActivity(intent);
        });

        btnTypeMainSearchPostsVol.setOnClickListener(view -> {
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVolByType.class);
//            intent.putExtra("id",user_id);
            startActivity(intent);
        });
    }
}