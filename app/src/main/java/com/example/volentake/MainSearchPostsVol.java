package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainSearchPostsVol extends AppCompatActivity {

    private Button btnCityMainSearchPostsVol, btnTypeMainSearchPostsVol, btnlastaddedMainSearchPostsVol;
    private EditText editTextCityMainSearchPostsVol;
    private Spinner spintype;
     String vol_user_id ;
    String txtType;
    String txtcity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search_posts_vol);
        //        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
           vol_user_id = bun.getString("id");
        }

        btnCityMainSearchPostsVol = (Button)findViewById(R.id.btnCityMainSearchPostsVol);
        btnTypeMainSearchPostsVol = (Button)findViewById(R.id.btnTypeMainSearchPostsVol);
        btnlastaddedMainSearchPostsVol = (Button)findViewById(R.id.btnsearchbylastadded);
        editTextCityMainSearchPostsVol = (EditText)findViewById(R.id.editTextCityMainSearchPostsVol);
        spintype = (Spinner) findViewById(R.id.spineroftypepost);
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(MainSearchPostsVol.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.types));
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(typeadapter);

        btnCityMainSearchPostsVol.setOnClickListener(view -> {
            txtcity = editTextCityMainSearchPostsVol.getText().toString();
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVolByCity.class);
            intent.putExtra("id",vol_user_id);
            intent.putExtra("city",txtcity);
            startActivity(intent);
        });

        btnTypeMainSearchPostsVol.setOnClickListener(view -> {
           txtType = spintype.getSelectedItem().toString();
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVolByType.class);
            intent.putExtra("id",vol_user_id);
          intent.putExtra("type",txtType);
            startActivity(intent);
        });
        btnlastaddedMainSearchPostsVol.setOnClickListener(view -> {
            txtType = spintype.getSelectedItem().toString();
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVol.class);
            intent.putExtra("id",vol_user_id);
            startActivity(intent);
        });
    }
}