package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Assoc_post;
import com.example.myapplication.Assoc_user;
import com.example.myapplication.Request;
import com.example.myapplication.Request_vol;
import com.example.myapplication.Status;
import com.example.myapplication.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestAssociation extends AppCompatActivity {
    private Button SendRequest;
    private Button Back;
    private TextView volName;
    private TextView volEmail;
    private EditText content;
    private EditText numParticipant;
    //    firebase
    private DatabaseReference mDatabase;
    AlertDialog.Builder builder;
    String vol_user_id = "";
    String post_id = "";
    String Assoc_id ="";
    String search_keyword;
    int state;
    Request cur_req;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_association);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        builder = new AlertDialog.Builder(this);
        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            vol_user_id = bun.getString("vol_id");
            post_id = bun.getString("post_id");
            Assoc_id = bun.getString("assoc_id");
            search_keyword = bun.getString("search_keyword");
            state = bun.getInt("state");
        }

        SendRequest = (Button)findViewById(R.id.btnsendrequest);
        Back = (Button)findViewById(R.id.btnbackrequest);
        volName = (TextView)findViewById(R.id.volfirstnamereq);
        volEmail = (TextView)findViewById(R.id.mailVolunteer);
        content = (EditText)findViewById(R.id.contantinsert);
        numParticipant = (EditText)findViewById(R.id.numofvolinsert);
        mDatabase.child("vol_users").child(vol_user_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Vol_user cure_user =  task.getResult().getValue(Vol_user.class);
                    assert cure_user != null;
                    volName.setText(cure_user.getFirst_name()+" " + cure_user.getLast_name());
                    volEmail.setText(cure_user.getEmail());
                }
                progressDialog.dismiss();
            }
        });

        SendRequest.setOnClickListener(view -> {

            String txtcontant = content.getText().toString();
            String txtnumpar = numParticipant.getText().toString();
            if (TextUtils.isEmpty(txtnumpar) || TextUtils.isEmpty(txtcontant) ){
                Toast.makeText(RequestAssociation.this, "Empty credentials!", Toast.LENGTH_SHORT).show();}
            else {
                send_request(txtcontant,txtnumpar);}
        });
        Back.setOnClickListener(view -> {
            Intent intent = new Intent(RequestAssociation.this, DetailsPostVol.class);
            intent.putExtra("vol_id",vol_user_id);
            intent.putExtra("post_id",post_id);
            intent.putExtra("search_keyword",search_keyword);
            intent.putExtra("state",state);
            startActivity(intent);
        });
    }
    private void send_request(String content,String numpar)
    {

        cur_req = new Request_vol(vol_user_id,post_id,content,Integer.valueOf(numpar), Status.WAITING);
        String req_id =  mDatabase.child("massage_assoc").push().getKey();
        mDatabase.child("massage_assoc").child(req_id).setValue(cur_req).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mDatabase.child("assoc_users").child(Assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                                Log.e("firebase", "Error getting data", task.getException());
                            }
                            else {
                                Assoc_user assoc_user2 =  task.getResult().getValue(Assoc_user.class);
                                assoc_user2.massages_req.add(req_id);
                                mDatabase.child("assoc_users").child(Assoc_id).setValue(assoc_user2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RequestAssociation.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                                            if(state==0)
                                            {
                                                Intent intent = new Intent(RequestAssociation.this, FeedPostsVolByCity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("id", vol_user_id);
                                                intent.putExtra("city", search_keyword);
                                                startActivity(intent);
                                            }
                                            else if(state==1)
                                            {
                                                Intent intent = new Intent(RequestAssociation.this, FeedPostsVolByType.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("id", vol_user_id);
                                                intent.putExtra("type", search_keyword);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Intent intent = new Intent(RequestAssociation.this, FeedPostsVol.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("id", vol_user_id);
                                                intent.putExtra("no_word", search_keyword);
                                                startActivity(intent);
                                            }
                                            finish();
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
                Intent intent1 = new Intent(RequestAssociation.this,VolunteerPage.class);
                intent1.putExtra("id",vol_user_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(RequestAssociation.this, VolunteerLogIn.class);
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