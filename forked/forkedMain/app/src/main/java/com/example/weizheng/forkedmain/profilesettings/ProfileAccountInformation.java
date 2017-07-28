package com.example.weizheng.forkedmain.profilesettings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profilesettings_profile_account_information);


        Log.i(TAG, "Loading account information");
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();
        display = (TextView) findViewById(R.id.account_information_display_details);
        email = (TextView) findViewById(R.id.account_information_email_details);
        loadInfo();
    }

    public void loadInfo(){

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

    public void updateFields(View view){

        Intent i = new Intent(this, ProfileUpdateDetails.class);
        i.putExtra("Display", display.getText().toString());
        i.putExtra("Email", email.getText().toString());
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}
