package com.example.weizheng.forkedmain.profilesettings;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weizheng.forkedmain.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileUpdateDetails extends AppCompatActivity {

    private EditText display;
    private EditText email;
    private Bundle data;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;
    private static final String TAG = "Results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesettings_profile_update_details);

        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();

        display = (EditText) findViewById(R.id.update_details_display_edittext);
        email = (EditText) findViewById(R.id.update_details_email_edittext);

        data = getIntent().getExtras();
        display.setText(data.getString("Display"));
        display.setTextColor(Color.BLACK);
        email.setText(data.getString("Email"));
        email.setTextColor(Color.BLACK);

    }

    public void updateDetails(View view){

        /** Updating display */
        DatabaseReference displayRef = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Display");
        displayRef.setValue(display.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.i(TAG, "email changed successfully");
                /** Updating email */
                myFirebaseAuth.getCurrentUser().updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "email changed successfully");
                        Toast.makeText(ProfileUpdateDetails.this, "Updated account information", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProfileUpdateDetails.this, ProfilePage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "email change unsuccessful");
                        Log.i(TAG, e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG, "email change unsuccessful");
                Log.i(TAG, e.getMessage());
            }
        });
    }
}
