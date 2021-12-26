package com.example.volentake;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsResponse extends AppCompatActivity {
    private TextView postName,nameAss,mailAss, contentStatus;
    private Button back;
    private DatabaseReference mRootRef;
    AlertDialog.Builder builder;
    String vol_id = "";
    String post_name = "";
    String assoc_name = "";
    String assoc_email = "";
    String status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_response);
        builder = new AlertDialog.Builder(this);
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
                Intent intent1 = new Intent(DetailsResponse.this,VolunteerPage.class);
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
                                Intent intent1 = new Intent(DetailsResponse.this, VolunteerLogIn.class);
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