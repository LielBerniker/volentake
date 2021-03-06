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

import com.example.model_voluntake_class.AdapterReqAssociation;
import com.example.model_voluntake_class.Assoc_user;
import com.example.model_voluntake_class.Request_vol;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class InboxAssociation extends AppCompatActivity {
    private Button back;
    private RecyclerView recycleRequests;
    private DatabaseReference mRootRef;
    AlertDialog.Builder builder;
    String assoc_id = "";
    ArrayList<String> cur_requests ;
    ArrayList<Pair<Request_vol,String>> requests = new ArrayList<>();
    int num_of_requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_association);
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
            assoc_id = bun.getString("id");
        }

        recycleRequests = findViewById(R.id.recycleRequests);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        back = (Button) findViewById(R.id.backtoassocuserfrominbox);
        mRootRef.child("assoc_users").child(assoc_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Assoc_user cure_user =  task.getResult().getValue(Assoc_user.class);
                    cur_requests = cure_user.getMassages_req();

                    num_of_requests = cur_requests.size();
                    if(num_of_requests==1)
                    {
                        progressDialog.dismiss();
                        builder.setTitle("inbox")
                                .setMessage("this association do not have any massages")
                                .setCancelable(true)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent1 = new Intent(InboxAssociation.this, AssociationPage.class);
                                        intent1.putExtra("id",assoc_id);
                                        startActivity(intent1);
                                    }
                                });
                        AlertDialog alterdialod = builder.create();
                        alterdialod.show();
                    }
                    for (int i = 1; i <num_of_requests ; i++) {
                        int count = i;
                        String req_id = cur_requests.get(count);
                        mRootRef.child("massage_assoc").child(req_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error getting data", task.getException());
                                }
                                else {
                                    Request_vol cur_req1 =  task.getResult().getValue(Request_vol.class);
                                    Request_vol cur_req2 = new Request_vol(cur_req1.getVol_user_id(),cur_req1.getPost_id(),cur_req1.getContent(),cur_req1.getNum_of_vol(),cur_req1.getStatus());
                                    Pair<Request_vol,String> pair1 =new Pair<>(cur_req2,req_id);
                                    requests.add(pair1);
                                    if(count+1==num_of_requests)
                                    {
                                        Collections.reverse(requests);
                                        AdapterReqAssociation adapter = new AdapterReqAssociation( InboxAssociation.this,assoc_id);
                                        adapter.setListRequests(requests);

                                        recycleRequests.setAdapter(adapter);
                                        recycleRequests.setLayoutManager(new GridLayoutManager(InboxAssociation.this,1));
                                        progressDialog.dismiss();}

                                }
                            }
                        });
                    }


                }
            }
        });

        back.setOnClickListener(view -> {
            Intent intent = new Intent(InboxAssociation.this,AssociationPage.class);
            intent.putExtra("id", assoc_id);
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
                Intent intent1 = new Intent(InboxAssociation.this, AssociationPage.class);
                intent1.putExtra("id",assoc_id);
                startActivity(intent1);
                return true;
            case R.id.itemlogout:
                builder.setTitle("log out")
                        .setMessage("are you sure you want to log out?")
                        .setCancelable(true)
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(InboxAssociation.this, AssociationLogIn.class);
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