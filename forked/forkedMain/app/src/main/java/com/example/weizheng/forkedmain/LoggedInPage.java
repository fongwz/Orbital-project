package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class LoggedInPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_page);
    }

    //public void onPreferenceClick()

    public void onDiscoverClick(View view){

        Intent i = new Intent(this, Settings1.class);

        // add extra boolean attribute to note that user does not want to save preferences to list of saved preferences already
        i.putExtra("savePreferences", false);
        startActivity(i);
    }

    public void onUploadClick(View view){

        Intent i = new Intent(this, userUpload.class);
        startActivity(i);

    }

    public void onUpdatePrefClick(View view){

        Intent i = new Intent(this, Settings1.class);
        i.putExtra("savePreferences", true);
        startActivity(i);
    }

    /** Remove back button functionality *****************************************/
    @Override
    public void onBackPressed(){

    }
}