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

import com.example.model_voluntake_class.Assoc_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditNumOfVolunteersRequest extends AppCompatActivity {
    private EditText numofvol;
    private TextView vol_name_view;
    private TextView post_name_view;
    private TextView num_vol_view;
    private Button updateData;
    private DatabaseReference mDatabase;
    String assoc_id = "";
    String vol_user_name = "";
    String post_id = "";
    int numofvolreq;
    String post_name = "";
    Assoc_post cur_post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_num_of_volunteers_request);
        // firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //  get information bundle
        Bundle bun = null;

        bun = getIntent().getExtras();
        if (bun != null) {
            assoc_id = bun.getString("id");
            vol_user_name = bun.getString("vol_user_name");
            post_id = bun.getString("post_id");
            numofvolreq = bun.getInt("numofvolreq");
            post_name = bun.getString("post_name");
        }
        updateData = (Button) findViewById(R.id.update_btn_activity_edit_num_of_volunteers_request);
        vol_name_view = (TextView) findViewById(R.id.vol_name_activity_edit_num_of_volunteers_request);
        post_name_view = (TextView) findViewById(R.id.post_name_activity_edit_num_of_volunteers_request);
        num_vol_view = (TextView) findViewById(R.id.num_vol_activity_edit_num_of_volunteers_request);
        numofvol = (EditText) findViewById(R.id.insrtnumofvolafterrequest);
        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    cur_post = task.getResult().getValue(Assoc_post.class);
                    assert cur_post != null;
                    numofvol.setText(String.valueOf(cur_post.getNum_of_participants()));
                }
            }
        });

        vol_name_view.setText(vol_user_name);
        post_name_view.setText(post_name);
        num_vol_view.setText(String.valueOf(numofvolreq));

        updateData.setOnClickListener(view -> {
            String txtnewnumofvol = numofvol.getText().toString();
            update_post(txtnewnumofvol);
        });

    }

    private void update_post(String updatednumofvol) {
        cur_post.setNum_of_participants(Integer.valueOf(updatednumofvol));
        mDatabase.child("posts").child(post_id).setValue(cur_post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditNumOfVolunteersRequest.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditNumOfVolunteersRequest.this, InboxAssociation.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("id", assoc_id);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}