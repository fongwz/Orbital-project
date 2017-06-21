package com.example.weizheng.forkedmain;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInPage extends AppCompatActivity {

    private Firebase myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;
    private FirebaseUser myFirebaseuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_page);

        Firebase.setAndroidContext(this);
        myFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseuser=myFirebaseAuth.getCurrentUser();
        myFirebaseRef = new Firebase("https://forked-up.firebaseio.com/"); //reference to root directory

    }

    //public void onPreferenceClick()

    public void onDiscoverClick(View view){

        Intent i = new Intent(this, Settings1.class);

        // add extra boolean attribute to note that user does not want to save preferences to list of saved preferences already
        i.putExtra("savePreferences", false);
        startActivity(i);
    }

    public void onUploadClick(View view){

        String uid = myFirebaseuser.getUid();
        Toast.makeText(this, "uid = "+uid, Toast.LENGTH_SHORT).show(); //debugging purposes
        Intent i = new Intent(this, userUpload.class);
        startActivity(i);

    }

    public void onUpdatePrefClick(View view){

        Intent i = new Intent(this, Settings1.class);
        i.putExtra("savePreferences", true);
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

    public void testClick(View view){
        Intent i = new Intent(this, userUploadSlide.class);
        startActivity(i);
    }

    /** Remove back button functionality *****************************************/
    @Override
    public void onBackPressed(){

    }
}