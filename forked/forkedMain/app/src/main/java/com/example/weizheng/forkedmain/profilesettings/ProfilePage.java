package com.example.weizheng.forkedmain.profilesettings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.weizheng.forkedmain.R;
import com.example.weizheng.forkedmain.homescreens.LoggedInPage;

public class ProfilePage extends AppCompatActivity {

    private static final String TAG = "Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesettings_profile_page);

        String[] profileSettings = new String[4];
        profileSettings[0] = "Account Information"; //email,display,password
        profileSettings[1] = "My Preferences";
        profileSettings[2] = "My Uploads";
        profileSettings[3] = "Change Password";

        Log.i(TAG, "Creating adapter");
        ListAdapter adapter = new profileAdapter(getApplicationContext(), profileSettings);
        ListView list = (ListView) findViewById(R.id.profile_page_listview);
        Log.i(TAG, "Loading adapter");
        list.setAdapter(adapter);
        Log.i(TAG,"Adapter loaded");
    }

    @Override
    public void onBackPressed(){

        Intent i = new Intent(ProfilePage.this, LoggedInPage.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

    }
}
