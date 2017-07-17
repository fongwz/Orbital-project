package com.example.weizheng.forkedmain;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedInPage extends AppCompatActivity {

    private DatabaseReference myFirebaseRef;
    private FirebaseDatabase myFirebaseDatabase;
    private FirebaseAuth myFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_page);

        Firebase.setAndroidContext(this);
        myFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myFirebaseRef = myFirebaseDatabase.getReference(); //reference to root directory

        //checkForPreferences();
    }

    public void checkForPreferences(){

        DatabaseReference preferenceReference = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Preferences");
        preferenceReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                    builder.setMessage("You don't have any preferences set! Please set some preferences first!")
                            .setCancelable(false)
                            .setPositiveButton("Proceed>>", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), "LUL", Toast.LENGTH_SHORT).show();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onPreferenceClick(View view){

        Intent i = new Intent(this, Result.class);
        startActivity(i);

    }

    public void onDiscoverClick(View view){

        Intent i = new Intent(this, Settings1.class);
        startActivity(i);
    }

    public void onUploadClick(View view){

        FirebaseUser myFirebaseUser = myFirebaseAuth.getCurrentUser();
        String uid = myFirebaseUser.getUid();
        Toast.makeText(this, "uid = "+uid, Toast.LENGTH_SHORT).show(); //debugging purposes
        Intent i = new Intent(this, userUploadSlide.class);
        startActivity(i);

    }

    public void onUpdatePrefClick(View view){

        Intent i = new Intent(this, Settings1.class);
        i.putExtra("updatePreferences", true);
        startActivity(i);
    }

    public void onSignOutClick(View view){
        FirebaseAuth myFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseAuth.signOut();
        finish();
        Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /** Remove back button functionality *****************************************/
    @Override
    public void onBackPressed(){

    }
}