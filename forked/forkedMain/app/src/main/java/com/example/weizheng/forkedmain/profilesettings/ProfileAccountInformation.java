package com.example.weizheng.forkedmain.profilesettings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.weizheng.forkedmain.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileAccountInformation extends AppCompatActivity {

    private TextView display;
    private TextView email;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;
    private static final String TAG = "Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesettings_profile_account_information);

        Log.i(TAG, "Loading account information");
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();
        display = (TextView) findViewById(R.id.account_information_display_details);
        email = (TextView) findViewById(R.id.account_information_email_details);



        DatabaseReference displayRef = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Display");
        displayRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                display.setText(dataSnapshot.getValue().toString());
                Log.i(TAG, "Loaded display name");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage());
            }
        });


        email.setText(myFirebaseAuth.getCurrentUser().getEmail());
        Log.i(TAG, "Loaded email");
    }
}
