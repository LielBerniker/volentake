package com.example.volentake;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txt_username;
    EditText txt_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       txt_username =(EditText)findViewById(R.id.txtuser);
        txt_pass =(EditText)findViewById(R.id.txtpass);
        String user_name = txt_username.getText().toString();
        String pass = txt_pass.getText().toString();
    }

    public void onClicklog(View view) {
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
    }

    public void onClickreg(View view) {
    }
}