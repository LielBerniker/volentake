
package com.example.volentake;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model_voluntake_class.Address;
import com.example.model_voluntake_class.Vol_user;
import com.example.model_voluntake_class.Volunteer_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;


public class VolunteerUserSignUp extends AppCompatActivity {

    private EditText inputFirstName;
    private EditText inputLastName;
    private EditText inputCity;
    private EditText inputStreet;
    private EditText inputHouseNumber;
    private EditText inputBirthday;
    private EditText inputPhone;
    private EditText inputPassword1;
    private EditText inputPassword2;
    private EditText inputMail;
    private Button register;
    private Button logInVolunteer;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
//    firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRootRef;

    String BirthDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_user_sign_up);

        inputFirstName = (EditText) findViewById(R.id.detailName);
        inputLastName =(EditText)  findViewById(R.id.inputLastName);
        inputCity = (EditText) findViewById(R.id.inputcity);
        inputStreet = (EditText) findViewById(R.id.insertStreet2);
        inputHouseNumber = (EditText) findViewById(R.id.insertHouseNumber2);
        inputMail = (EditText) findViewById(R.id.inputMail);
        inputPhone = (EditText) findViewById(R.id.inputPhoneVolSU);
        inputPassword1 = (EditText) findViewById(R.id.inputPassword1);
        inputPassword2 =(EditText)  findViewById(R.id.inputPassword2);
        register = (Button)findViewById(R.id.update_btn_back_btn_activity_edit_volunteer);
        logInVolunteer = (Button)findViewById(R.id.btnBackToFeedOfPosts);
        mDisplayDate = (TextView) findViewById(R.id.birthdateselector);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        VolunteerUserSignUp.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                BirthDay = day + "/" + month + "/" + year;
                mDisplayDate.setText(BirthDay);
            }
        };
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtFirstName = inputFirstName.getText().toString();
                String txtLastName = inputLastName.getText().toString();

                String txtPhoneNumber = inputPhone.getText().toString();
                String txtEmail = inputMail.getText().toString();
                String txtPassword1 = inputPassword1.getText().toString();
                String txtPassword2 = inputPassword2.getText().toString();
                String txtCity = inputCity.getText().toString().toLowerCase(Locale.ROOT);
                String txtStreet = inputStreet.getText().toString();
                String txtHouseNumber = inputHouseNumber.getText().toString();
                String txtBirthDay = BirthDay;

                if (TextUtils.isEmpty(txtFirstName) || TextUtils.isEmpty(txtLastName ) || TextUtils.isEmpty(txtCity) || TextUtils.isEmpty(txtStreet) || TextUtils.isEmpty(txtHouseNumber)
                        || TextUtils.isEmpty(txtBirthDay) || TextUtils.isEmpty(txtPhoneNumber)  || TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword1) || TextUtils.isEmpty(txtPassword2)){
                    Toast.makeText(VolunteerUserSignUp.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (!(txtPassword1.equals(txtPassword2))){
                    Toast.makeText(VolunteerUserSignUp.this, "Incompatible Passwords!", Toast.LENGTH_SHORT).show();
                } else if (txtPassword1.length() < 6){
                    Toast.makeText(VolunteerUserSignUp.this, "Password too short!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtFirstName , txtLastName , txtEmail , txtPassword1,txtCity,txtStreet,txtHouseNumber,txtBirthDay,txtPhoneNumber);
                }
            }
        });

        logInVolunteer.setOnClickListener(view -> {
            Intent intent = new Intent(VolunteerUserSignUp.this, VolunteerLogIn.class);
            startActivity(intent);
        });
    }
    private void registerUser(final String firstName, final String lastName, final String email, String password,final String city,final String street,final String numhouse,final String Birthdaycur,final String phonenum) {


        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Address address = new Address(city,street,Integer.parseInt(numhouse));

                String user_id = mAuth.getCurrentUser().getUid();
                Volunteer_user cur_user = new Vol_user(firstName,lastName,address,phonenum,Birthdaycur,email);
                mRootRef.child("vol_users").child(user_id).setValue(cur_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(VolunteerUserSignUp.this, "Done Successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VolunteerUserSignUp.this, VolunteerPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("id",mAuth.getCurrentUser().getUid());
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VolunteerUserSignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}