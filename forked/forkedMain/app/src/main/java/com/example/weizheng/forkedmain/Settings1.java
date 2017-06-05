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

    public void onCuisineClick(View view){

        /** Launching sqlitedb to save user preferences */
        if(savePreferences) {
            chinese = (CheckBox) findViewById(R.id.settings_cuisine_chinese);
            malay = (CheckBox) findViewById(R.id.settings_cuisine_malay);
            indian = (CheckBox) findViewById(R.id.settings_cuisine_indian);
            western = (CheckBox) findViewById(R.id.settings_cuisine_western);
            korean = (CheckBox) findViewById(R.id.settings_cuisine_korean);

            if (chinese.isChecked()) {
            }//add chinese to temporary user preferences
            if (malay.isChecked()) {
            }//add malay to temporary user preferences
        }else{
        Intent i = new Intent(this, Settings2.class);
        i.putExtra("saveRecipe",false);
        startActivity(i);
    }
}
