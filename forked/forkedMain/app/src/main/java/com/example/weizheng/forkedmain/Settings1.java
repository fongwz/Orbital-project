package com.example.weizheng.forkedmain;

import android.content.Intent;
import android.os.health.ServiceHealthStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.view.View;

public class Settings1 extends AppCompatActivity {

    private static CheckBox chinese;
    private static CheckBox malay;
    private static CheckBox indian;
    private static CheckBox western;
    private static CheckBox korean;
    private Bundle data;
    private Boolean savePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings1);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }
        savePreferences = data.getBoolean("savePreferences");
    }

    public void onCuisineClick(View view) {

        int[] preferenceValues = new int[12];
        chinese = (CheckBox) findViewById(R.id.settings_cuisine_chinese);
        malay = (CheckBox) findViewById(R.id.settings_cuisine_malay);
        indian = (CheckBox) findViewById(R.id.settings_cuisine_indian);
        western = (CheckBox) findViewById(R.id.settings_cuisine_western);
        korean = (CheckBox) findViewById(R.id.settings_cuisine_korean);

        /** if save preferences, store them as 1/0 integer value in array first, initialize sqlite at last settings */

        if (chinese.isChecked()) {
            preferenceValues[0] = 1;
        } else {
            preferenceValues[0] = 0;
        }

        if (malay.isChecked()) {
            preferenceValues[1] = 1;
        } else {
            preferenceValues[1] = 0;
        }

        if (indian.isChecked()) {
            preferenceValues[2] = 1;
        } else {
            preferenceValues[2] = 0;
        }

        if (western.isChecked()) {
            preferenceValues[3] = 1;
        } else {
            preferenceValues[3] = 0;
        }

        if (korean.isChecked()) {
            preferenceValues[4] = 1;
        } else {
            preferenceValues[4] = 0;
        }


        Intent i = new Intent(this, Settings2.class);
        if(savePreferences){
            i.putExtra("savePreferences", true);
        } else {
            i.putExtra("savePreferences", false);
        }
        i.putExtra("preferenceValues", preferenceValues);
        startActivity(i);
    }
}
