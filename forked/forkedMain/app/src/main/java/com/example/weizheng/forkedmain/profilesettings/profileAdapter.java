package com.example.weizheng.forkedmain.profilesettings;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.weizheng.forkedmain.R;

public class profileAdapter extends ArrayAdapter<String> {

    private static final String TAG = "Results";

    public profileAdapter(@NonNull Activity context, String[] profileSettings) {
        super(context, R.layout.results_custom_row, profileSettings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater wzsInflator = LayoutInflater.from(getContext());
        final View customView = wzsInflator.inflate(R.layout.profilesettings_profile_settings_rows, parent, false);

        String setting = getItem(position);
        TextView settingsText = (TextView) customView.findViewById(R.id.profile_settings_rows_textview);
        settingsText.setText(setting);
        settingsText.setTextColor(Color.BLACK);

        customView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        TextView settingsText = (TextView) customView.findViewById(R.id.profile_settings_rows_textview);
                        chooseProfileChild(settingsText.getText().toString(), v);
                    }
                }
        );

        return customView;
    }

    public void chooseProfileChild(String settingsText, View v){

        if(settingsText.equals("Account Information")) {

            Log.i(TAG, "Selected account information");
            Intent i = new Intent(v.getContext(), ProfileAccountInformation.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(i);
            Activity activity = (Activity) profileAdapter.this.getContext();
            activity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);


        } else if(settingsText.equals("My Preferences")) {

            Log.i(TAG, "Selected my preferences");
            Intent i = new Intent(v.getContext(), ProfileMyPreferences.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(i);
            Activity activity = (Activity) profileAdapter.this.getContext();
            activity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

        } else if(settingsText.equals("My Uploads")) {

            Log.i(TAG, "Selected my uploads");
            Intent i = new Intent(v.getContext(), ProfileMyUploads.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(i);
            Activity activity = (Activity) profileAdapter.this.getContext();
            activity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

        } else if(settingsText.equals("Change Password")){

            Log.i(TAG, "Selected change password");
            Intent i = new Intent(v.getContext(), ProfileChangePassword.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(i);
            Activity activity = (Activity) profileAdapter.this.getContext();
            activity.overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

        }
    }
}