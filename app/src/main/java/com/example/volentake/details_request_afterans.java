package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Request_vol;
import com.example.myapplication.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class details_request_afterans extends AppCompatActivity {
    private TextView PostName,volfullname,volemail,numofvol,content,status_inform;
    private Button back,seevoldetails;
    private DatabaseReference mRootRef;
    String  request_id ;
    String assoc_id = "";
    String post_name = "";
    String vol_user_name = "";
    String vol_user_email = "";
    String vol_user_id="";
    String post_id="";
    Request_vol cur_req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_request_afterans);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        //        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if (bun != null) {
            request_id = bun.getString("request_id");
            assoc_id = bun.getString("assoc_id");
            post_name = bun.getString("post_name");
            vol_user_name = bun.getString("vol_user_name");
            vol_user_email = bun.getString("vol_user_email");
            post_id = bun.getString("post_id");
        }
        String rejected_massage,approved_massage;
        rejected_massage = "this request has already been rejected";
        approved_massage = "this request has already been approved";
        back = (Button) findViewById(R.id.btnBackToSmallRequestDetailsRequestafterans);
        seevoldetails = (Button) findViewById(R.id.voldetailsfullforrequestafterans);
        PostName = (TextView) findViewById(R.id.volnameforrequestafterans);
        volfullname = (TextView) findViewById(R.id.fullNameDetailsRequestafterans);
        numofvol = (TextView) findViewById(R.id.numberofparicerequestdetailsafterans);
        volemail = (TextView) findViewById(R.id.requestMailVolunteerafterans);
        content = (TextView) findViewById(R.id.cntantofrequestdetailsafterans);
        status_inform = (TextView) findViewById(R.id.statusupdateinformforreq);
        PostName.setText(post_name);
        volemail.setText(vol_user_email);
        volfullname.setText(vol_user_name);
        mRootRef.child("massage_assoc").child(request_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    cur_req = task.getResult().getValue(Request_vol.class);
                    numofvol.setText(Integer.toString(cur_req.getNum_of_vol()));
                    content.setText(cur_req.getContent());
                    vol_user_id = cur_req.getVol_user_id();
                    if(cur_req.getStatus()== Status.APPROVED)
                    {
                        status_inform.setText(approved_massage);
                    }
                    else
                    {
                        status_inform.setText(rejected_massage);
                    }
                }
            }
        });
        back.setOnClickListener(view -> {
            Intent intent = new Intent(details_request_afterans.this, InboxAssociation.class);
            intent.putExtra("id", assoc_id);
            startActivity(intent);
        });
        seevoldetails.setOnClickListener(view -> {
            Intent intent = new Intent(details_request_afterans.this, VolunteerDetailsForAssoc.class);
            intent.putExtra("request_id", request_id);
            intent.putExtra("assoc_id", assoc_id);
            intent.putExtra("post_name", post_name);
            intent.putExtra("vol_user_name", vol_user_name);
            intent.putExtra("vol_user_email", vol_user_email);
            intent.putExtra("vol_user_id", vol_user_id);
            intent.putExtra("status_def",1);
            startActivity(intent);
        });
    }
}