package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Request_vol;
import com.example.myapplication.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsResponse extends AppCompatActivity {
    private TextView postName,nameAss,mailAss, contentStatus;
    private Button back;
    private DatabaseReference mRootRef;
    String vol_id = "";
    String post_name = "";
    String assoc_name = "";
    String assoc_email = "";
    String status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_response);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            vol_id = bun.getString("vol_id");
            post_name = bun.getString("post_name");
            assoc_name = bun.getString("assoc_name");
            assoc_email = bun.getString("assoc_email");
            status = bun.getString("status");

        }
        back = (Button) findViewById(R.id.btnBackToSmallResponseDRes);
        postName = (TextView) findViewById(R.id.namePostDRes);
        nameAss = (TextView) findViewById(R.id.nameAssDRes);
        mailAss = (TextView) findViewById(R.id.mailAssDRes);
        contentStatus = (TextView) findViewById(R.id.statusDres);
        postName.setText(post_name);
        mailAss.setText(assoc_email);
        nameAss.setText(assoc_name);
        String show_massage;
        if(status.equals("approved")==true)
            show_massage = "the request to join this volunteering has been approved congratulations! ";
        else
            show_massage = "the request to join this volunteering has been rejected , were sorry, please try a different volunteering ";
        contentStatus.setText(show_massage);

        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsResponse.this, InboxResponses.class);
            intent.putExtra("id", vol_id);
            startActivity(intent);
        });

    }

}