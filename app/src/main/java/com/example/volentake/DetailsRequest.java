package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.Request_vol;
import com.example.myapplication.Response;
import com.example.myapplication.Response_Interface;
import com.example.myapplication.Status;
import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DetailsRequest extends AppCompatActivity {
    private TextView PostName,volfullname,volemail,numofvol,content;
    private Button back,approvebtn,rejectbtn,seevoldetails;
    private DatabaseReference mRootRef;
    String  request_id ;
    String assoc_id ;
    String post_name ;
    String vol_user_name ;
    String vol_user_email ;
    String vol_user_id;
    String post_id;
    Request_vol cur_req;
    int numofvolreq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_request);
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
        back = (Button) findViewById(R.id.btnBackToSmallRequestDetailsRequest);
        approvebtn = (Button) findViewById(R.id.btnaprovereuest);
        rejectbtn = (Button) findViewById(R.id.btnrejectrequest);
        seevoldetails = (Button) findViewById(R.id.voldetailsfullforrequest);
        PostName = (TextView) findViewById(R.id.volnameforrequest);
        volfullname = (TextView) findViewById(R.id.fullNameDetailsRequest);
        numofvol = (TextView) findViewById(R.id.numberofparicerequestdetails);
        volemail = (TextView) findViewById(R.id.requestMailVolunteer);
        content = (TextView) findViewById(R.id.cntantofrequestdetails);
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
                    numofvolreq = cur_req.getNum_of_vol();
                    numofvol.setText(Integer.toString(cur_req.getNum_of_vol()));
                    content.setText(cur_req.getContent());
                    vol_user_id = cur_req.getVol_user_id();
                }
            }
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsRequest.this, InboxAssociation.class);
            intent.putExtra("id", assoc_id);
            startActivity(intent);
        });
        seevoldetails.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsRequest.this, VolunteerDetailsForAssoc.class);
            intent.putExtra("request_id", request_id);
            intent.putExtra("assoc_id", assoc_id);
            intent.putExtra("post_name", post_name);
            intent.putExtra("post_id", post_id);
            intent.putExtra("vol_user_name", vol_user_name);
            intent.putExtra("vol_user_email", vol_user_email);
            intent.putExtra("vol_user_id", vol_user_id);
            intent.putExtra("status_def",0);
            startActivity(intent);
        });
        approvebtn.setOnClickListener(view -> {
            cur_req.setStatus(Status.APPROVED);
            update_information(0);
        });
        rejectbtn.setOnClickListener(view -> {
            cur_req.setStatus(Status.REJECTED);
            update_information(1);
        });
    }
    public void update_information(int cur_status)
    {
        mRootRef.child("massage_assoc").child(request_id).setValue(cur_req).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Status status1;
                    if(cur_status == 0)
                        status1 = Status.APPROVED;
                    else
                        status1 = Status.REJECTED;
                    Response_Interface cur_res = new Response(cur_req.getVol_user_id(),assoc_id,cur_req.getPost_id(),status1);
                    String res_id = mRootRef.child("massage_vol").push().getKey();
                    String vol_user_id = cur_req.getVol_user_id();
                    mRootRef.child("massage_vol").child(res_id).setValue(cur_res).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mRootRef.child("vol_users").child(vol_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if (!task.isSuccessful()) {
                                            Log.e("firebase", "Error getting data", task.getException());
                                        }
                                        else {
                                            Vol_user vol_user1 =  task.getResult().getValue(Vol_user.class);
                                            vol_user1.massages_res.add(res_id);
                                            if(cur_status == 0)
                                            {
                                                vol_user1.active_posts.add(post_id);
                                            }
                                            mRootRef.child("vol_users").child(vol_user_id).setValue(vol_user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        if(cur_status==0)
                                                        {
                                                            Toast.makeText(DetailsRequest.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(DetailsRequest.this, edit_num_of_volunteers_request.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            intent.putExtra("id", assoc_id);
                                                            intent.putExtra("vol_user_name", vol_user_name);
                                                            intent.putExtra("post_id", post_id);
                                                            intent.putExtra("post_name", post_name);
                                                            intent.putExtra("numofvolreq", numofvolreq);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(DetailsRequest.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(DetailsRequest.this, InboxAssociation.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            intent.putExtra("id", assoc_id);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });

                            }

                        }

                    });
                }
            }
        });

    }
}
