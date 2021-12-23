package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class EditCredentialsAssoc extends AppCompatActivity {

    private TextView volEmail, volCurrentPass, volNewPass, volConfirmNewPass;
    private Button backBtn, updateBtn;
    private String assoc_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credentials_assoc);

        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            assoc_user_id = bun.getString("id");
        }

        volEmail = (TextView) findViewById(R.id.email_activity_edit_credentials_assoc);
        volCurrentPass = (TextView) findViewById(R.id.current_password_activity_edit_credentials_assoc);
        volNewPass = (TextView) findViewById(R.id.new_password_activity_edit_credentials_assoc);
        volConfirmNewPass = (TextView) findViewById(R.id.confirm_password_activity_edit_credentials_assoc);

        backBtn = (Button) findViewById(R.id.back_btn_activity_edit_credentials_assoc);
        updateBtn = (Button) findViewById(R.id.update_btn_activity_edit_credentials_assoc);

        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(EditCredentialsAssoc.this, EditAssociation.class);
            intent.putExtra("id", assoc_user_id);
            startActivity(intent);
        });

        updateBtn.setOnClickListener(view -> {
            // LIEL - update email and password to firebase
        });

    }
}