package com.example.weizheng.forkedmain.profilesettings;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.weizheng.forkedmain.R;
import com.example.weizheng.forkedmain.homescreens.LoggedInPage;

public class ProfilePage extends AppCompatActivity {

    private static final String TAG = "Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.profilesettings_profile_page);
        getWindow().setEnterTransition(new Fade().setDuration(1500));

        String[] profileSettings = new String[4];
        profileSettings[0] = "Account Information"; //email,display,password
        profileSettings[1] = "My Preferences";
        profileSettings[2] = "My Uploads";
        profileSettings[3] = "Change Password";

        Log.i(TAG, "Creating adapter");
        ListAdapter adapter = new profileAdapter(this, profileSettings);
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
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        finish();

    }
}
