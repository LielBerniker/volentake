package com.example.volentake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainSearchPostsVol extends AppCompatActivity {

    private Button btnCityMainSearchPostsVol, btnTypeMainSearchPostsVol, btnlastaddedMainSearchPostsVol;
    private EditText editTextCityMainSearchPostsVol;
    private Spinner spintype;
    private Button backBtn;
    AlertDialog.Builder builder;
    String vol_user_id;
    String txtType;
    String txtcity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search_posts_vol);
        builder = new AlertDialog.Builder(this);
        //        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            vol_user_id = bun.getString("id");
        }

        btnCityMainSearchPostsVol = (Button) findViewById(R.id.btnCityMainSearchPostsVol);
        btnTypeMainSearchPostsVol = (Button) findViewById(R.id.btnTypeMainSearchPostsVol);
        btnlastaddedMainSearchPostsVol = (Button) findViewById(R.id.btnsearchbylastadded);
        editTextCityMainSearchPostsVol = (EditText) findViewById(R.id.editTextCityMainSearchPostsVol);
        backBtn = (Button) findViewById(R.id.back_activity_main_search_posts_vol);
        spintype = (Spinner) findViewById(R.id.spineroftypepost);
        ArrayAdapter<String> typeadapter = new ArrayAdapter<String>(MainSearchPostsVol.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.types));
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spintype.setAdapter(typeadapter);

        btnCityMainSearchPostsVol.setOnClickListener(view -> {
            txtcity = editTextCityMainSearchPostsVol.getText().toString();
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVolByCity.class);
            intent.putExtra("id", vol_user_id);
            intent.putExtra("city", txtcity);
            startActivity(intent);
        });

        btnTypeMainSearchPostsVol.setOnClickListener(view -> {
            txtType = spintype.getSelectedItem().toString();
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVolByType.class);
            intent.putExtra("id", vol_user_id);
            intent.putExtra("type", txtType);
            startActivity(intent);
        });
        btnlastaddedMainSearchPostsVol.setOnClickListener(view -> {
            txtType = spintype.getSelectedItem().toString();
            Intent intent = new Intent(MainSearchPostsVol.this, FeedPostsVol.class);
            intent.putExtra("id", vol_user_id);
            startActivity(intent);
        });
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainSearchPostsVol.this, VolunteerPage.class);
            intent.putExtra("id", vol_user_id);
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
                Intent intent1 = new Intent(MainSearchPostsVol.this, VolunteerPage.class);
                intent1.putExtra("id", vol_user_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(MainSearchPostsVol.this, VolunteerLogIn.class);
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