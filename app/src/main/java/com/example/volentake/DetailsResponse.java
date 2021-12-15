package com.example.volentake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Request_vol;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsResponse extends AppCompatActivity {
    private TextView postName,nameAss,mailAss, contentStatus;
    private Button back;
    private DatabaseReference mRootRef;
    String  response_id ;
    String vol_id = "";
    String post_name = "";
    String ass_user_name = "";
    String ass_user_email = "";
    Request_vol cur_res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_response);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            response_id = bun.getString("response_id");
            vol_id = bun.getString("vol_id");
            post_name = bun.getString("post_name");
            ass_user_name = bun.getString("ass_user_name");
            ass_user_email = bun.getString("ass_user_email");
            //////for Liel
            /////////status
        }
        back = (Button) findViewById(R.id.btnBackToSmallResponseDRes);
        postName = (TextView) findViewById(R.id.namePostDRes);
        nameAss = (TextView) findViewById(R.id.nameAssDRes);
        mailAss = (TextView) findViewById(R.id.mailAssDRes);
        contentStatus = (TextView) findViewById(R.id.statusDres);
        postName.setText(post_name);
        mailAss.setText(ass_user_email);
        nameAss.setText(ass_user_name);
                ///////for Liel
//        case (status):
//        case 1:
//            contentStatus.setText("Accepted");
//        case 2:
//        contentStatus.setText("Rejected");
        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsResponse.this, InboxResponses.class);
            intent.putExtra("id", vol_id);
            startActivity(intent);
        });

    }

}