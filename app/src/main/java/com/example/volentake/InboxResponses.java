package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.model_voluntake_class.AdapterResponse;
import com.example.model_voluntake_class.Response;
import com.example.model_voluntake_class.Vol_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class InboxResponses extends AppCompatActivity {
    private Button back;
    private RecyclerView recycleResponses;
    private DatabaseReference mRootRef;
    AlertDialog.Builder builder;
    String vol_id = "";
    ArrayList<String> cur_responses ;
    ArrayList<Pair<Response,String>> responses = new ArrayList<>();
    int num_of_responses;
    Vol_user cure_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_responses);
        builder = new AlertDialog.Builder(this);
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("loading...");
        progressDialog.setCancelable(false);
        progressDialog.setIcon(R.drawable.logovector_01);
        progressDialog.show();
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            vol_id = bun.getString("id");
        }

        recycleResponses = findViewById(R.id.recycleResponses);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        back = (Button) findViewById(R.id.backtovolfromresponses);

        mRootRef.child("vol_users").child(vol_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                     cure_user =  task.getResult().getValue(Vol_user.class);
                    cur_responses= cure_user.getMassages_res();

                    num_of_responses = cur_responses.size();
                    if(num_of_responses==1)
                    {
                        progressDialog.dismiss();
                        builder.setTitle("inbox")
                                .setMessage("you do not have any massages")
                                .setCancelable(true)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent1 = new Intent(InboxResponses.this, VolunteerPage.class);
                                        intent1.putExtra("id",vol_id);
                                        startActivity(intent1);
                                    }
                                });
                        AlertDialog alterdialod = builder.create();
                        alterdialod.show();
                    }
                    for (int i = 1; i <num_of_responses ; i++) {
                        int count = i;
                        String res_id = cur_responses.get(count);
                        mRootRef.child("massage_vol").child(res_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    Response cur_res1 =  task.getResult().getValue(Response.class);
                                    Response cur_res2 = new Response(cur_res1.getVol_user_id(),cur_res1.getAssociation_user_id(),cur_res1.getPost_id(),cur_res1.getStatus());
                                    cur_res2.setMassage_update(cur_res1.getMassage_update());
                                    Pair<Response,String> pair1 =new Pair<>(cur_res2,res_id);
                                    responses.add(pair1);
                                    if(count+1==num_of_responses)
                                    {
                                        Collections.reverse(responses);
                                        AdapterResponse adapter = new AdapterResponse( InboxResponses.this,vol_id);
                                        adapter.setResponses(responses);

                                        recycleResponses.setAdapter(adapter);
                                        recycleResponses.setLayoutManager(new GridLayoutManager(InboxResponses.this,1));
                                        progressDialog.dismiss();}

                                }
                            }
                        });
                    }


                }
            }
        });
        back.setOnClickListener(view -> {
            Intent intent = new Intent(InboxResponses.this,VolunteerPage.class);
            intent.putExtra("id", vol_id);
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
                Intent intent1 = new Intent(InboxResponses.this, VolunteerPage.class);
                intent1.putExtra("id",vol_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(InboxResponses.this, VolunteerLogIn.class);
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