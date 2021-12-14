package com.example.volentake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Assoc_post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsRequest extends AppCompatActivity {
    private TextView fullNameDetailsRequest, requestMailVolunteer, textNumVolRequest, contentRequest;
    private Button back;
    private DatabaseReference mDatabase;
    String ass_user_id = "";
    String req_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_request);
        //        firebase code
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        get information bundle
        Bundle bun = null;
        bun = getIntent().getExtras();
        if(bun != null)
        {
            ass_user_id = bun.getString("assoc_id");
            req_id = bun.getString("req_id");
        }
        back = (Button) findViewById(R.id.btnBackToSmallRequestDetailsRequest);
        fullNameDetailsRequest = (TextView) findViewById(R.id.fullNameDetailsRequest);
        requestMailVolunteer = (TextView) findViewById(R.id.requestMailVolunteer);
        textNumVolRequest = (TextView) findViewById(R.id.editTextNumVolRequest);
        contentRequest = (TextView) findViewById(R.id.contentRequest);
        //////////////////////for Liel
//        mDatabase.child("posts").child(post_id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Log.e("firebase", "Error getting data", task.getException());
//                }
//                else {
//                    Assoc_post cur_post =  task.getResult().getValue(Assoc_post.class);
//                    assert cur_post != null;
//                    PostName.setText(cur_post.getName());
//                    NumOfVol.setText(String.valueOf(cur_post.getNum_of_participants()));
//                    PostPhoneNum.setText(cur_post.getPhone_number());
//                    PostDescription.setText(cur_post.getDescription());
//                    PostType.setText(cur_post.getType());
//                    assoc_id = cur_post.userId;
//                    System.out.println("this id the user id in the post" + assoc_id);
//                }
//            }
//        });
        back.setOnClickListener(view -> {
            Intent intent = new Intent(DetailsRequest.this, InboxAssociation.class);
            intent.putExtra("id",ass_user_id);
            startActivity(intent);
        });


    }
}