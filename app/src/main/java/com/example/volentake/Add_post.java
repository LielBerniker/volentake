package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_post extends AppCompatActivity {
    private EditText inputName;
    private EditText inputAddress;
    private EditText inputPhone;
    private EditText inputDescription;
    private EditText inputType;
    private EditText inputNumOfParticipants;
    private Button addPost;
    private Button backToAssoc;
    //    firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        inputName =(EditText)  findViewById(R.id.inputPostName);
        inputAddress = (EditText) findViewById(R.id.inputcity);
        inputPhone = (EditText) findViewById(R.id.InputPhoneNum);
        inputDescription = (EditText) findViewById(R.id.InputDescreption);
        inputType = (EditText) findViewById(R.id.InputType);
        inputNumOfParticipants = (EditText) findViewById(R.id.InputNumOfPartic);
        addPost = (Button)findViewById(R.id.btnCreatePost);
        backToAssoc = (Button)findViewById(R.id.btnBackToAssoc);

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = inputName.getText().toString();
                String txtPhone = inputPhone.getText().toString();
                String txtDescription = inputDescription.getText().toString();
                String txtaddrress = inputAddress.getText().toString();
                String txtType = inputType.getText().toString();
                String txtnumofpar = inputNumOfParticipants.getText().toString();

                if (TextUtils.isEmpty(txtName) || TextUtils.isEmpty(txtPhone ) || TextUtils.isEmpty(txtaddrress)
                        || TextUtils.isEmpty(txtDescription) || TextUtils.isEmpty(txtType) || TextUtils.isEmpty(txtnumofpar)) {
                    Toast.makeText(Add_post.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    add_post_info(txtName , txtPhone , txtDescription , txtType,txtaddrress,txtnumofpar);
                }

            }
        });
        backToAssoc.setOnClickListener(view -> {
            Intent intent = new Intent(Add_post.this, AssociationPage.class);
            startActivity(intent);
        });

    }
    public void add_post_info(String txtName , String txtPhone ,String txtDescription ,String txtType,String txtaddrress,String txtnumofpar)
    {

    }
}