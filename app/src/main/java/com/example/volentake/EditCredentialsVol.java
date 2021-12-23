package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EditCredentialsVol extends AppCompatActivity {

    private TextView volEmail, volCurrentPass, volNewPass, volConfirmNewPass;
    private Button backBtn, updateBtn;
    private String vol_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credentials_vol);

        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            vol_user_id = bun.getString("id");
        }

        volEmail = (TextView) findViewById(R.id.email_activity_edit_credentials_vol);
        volCurrentPass = (TextView) findViewById(R.id.current_password_activity_edit_credentials_vol);
        volNewPass = (TextView) findViewById(R.id.new_password_activity_edit_credentials_vol);
        volConfirmNewPass = (TextView) findViewById(R.id.confirm_password_activity_edit_credentials_vol);

        backBtn = (Button) findViewById(R.id.back_activity_edit_credentials_vol);
        updateBtn = (Button) findViewById(R.id.update_btn_activity_edit_credentials_vol);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditCredentialsVol.this, EditVolunteer.class);
            intent.putExtra("id", vol_user_id);
            startActivity(intent);
        });

        updateBtn.setOnClickListener(view -> {
            // LIEL - update email and password to firebase
        });


    }
}