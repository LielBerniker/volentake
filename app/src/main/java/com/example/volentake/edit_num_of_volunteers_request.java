package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Assoc_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_num_of_volunteers_request extends AppCompatActivity {
    private EditText numofvol;
    private TextView contant_req;
    private Button updateData;
    private Button backBtn;
    private DatabaseReference mDatabase;
    String assoc_id="";
    String vol_user_name="";
    String post_id = "";
    int numofvolreq;
    String post_name = "";
    Assoc_post cur_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_num_of_volunteers_request);
        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        get information bundle
        Bundle bun = null;

        bun = getIntent().getExtras();
        if(bun != null)
        {
            assoc_id = bun.getString("id");
            vol_user_name = bun.getString("vol_user_name");
            post_id = bun.getString("post_id");
            numofvolreq = bun.getInt("numofvolreq");
            post_name = bun.getString("post_name");
        }
        updateData = (Button)findViewById(R.id.update_btn_activity_edit_num_of_volunteers_request);
        backBtn = (Button)findViewById(R.id.back_btn_activity_edit_num_of_volunteers_request);
        contant_req = (TextView) findViewById(R.id.editnumofvolunteerdescription);
        numofvol = (EditText) findViewById(R.id.insrtnumofvolafterrequest);
        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    cur_post =  task.getResult().getValue(Assoc_post.class);
                    assert cur_post != null;
                    numofvol.setText(String.valueOf(cur_post.getNum_of_participants()));

                }
            }
        });
        String massage_con = "you just approved a volunteer request from: " + vol_user_name +"\n" +
                "to participate at the volunteering event: " + post_name +"\n" +
                "the request included a number of: " + String.valueOf(numofvolreq) + " volunteers\n" +
                "please update the number of volunteer needed for this volunteering event";
        contant_req.setText(massage_con);
        updateData.setOnClickListener(view -> {
            String txtnewnumofvol = numofvol.getText().toString();
            update_post(txtnewnumofvol);
        });
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(edit_num_of_volunteers_request.this, DetailsRequest.class);
            intent.putExtra("id", assoc_id);
            startActivity(intent);
        });

    }
    private void update_post(String updatednumofvol)
    {
        cur_post.setNum_of_participants(Integer.valueOf(updatednumofvol));
        mDatabase.child("posts").child(post_id).setValue(cur_post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(edit_num_of_volunteers_request.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(edit_num_of_volunteers_request.this, InboxAssociation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id",assoc_id);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}