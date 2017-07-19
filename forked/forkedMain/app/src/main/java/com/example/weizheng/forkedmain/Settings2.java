package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.content.Intent;

public class    Settings2 extends AppCompatActivity {

    private static CheckBox sweet;
    private static CheckBox sour;
    private static CheckBox spicy;
    private Bundle data;
    private Boolean updatePreferences;
    private Boolean setupPreferences;
    private int[] preferenceValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }
        preferenceValues = data.getIntArray("preferenceValues");
        updatePreferences = data.getBoolean("updatePreferences");
        setupPreferences = data.getBoolean("setupPreferences");
    }

    public void onTasteClick(View view) {

        sweet = (CheckBox) findViewById(R.id.settings_taste_sweet);
        sour = (CheckBox) findViewById(R.id.settings_taste_sour);
        spicy = (CheckBox) findViewById(R.id.settings_taste_spicy);

        if (sweet.isChecked()) {
            preferenceValues[9] = 1;
        } else {
            preferenceValues[9] = 0;
        }

        if (sour.isChecked()) {
            preferenceValues[7] = 1;
        } else {
            preferenceValues[7] = 0;
        }

        if (spicy.isChecked()) {
            preferenceValues[8] = 1;
        } else {
            preferenceValues[8] = 0;
        }

        Intent i = new Intent(this, Settings3.class);
        i.putExtra("preferenceValues", preferenceValues);
        i.putExtra("updatePreferences",updatePreferences);
        i.putExtra("setupPreferences",setupPreferences);
        startActivity(i);
    }
}
