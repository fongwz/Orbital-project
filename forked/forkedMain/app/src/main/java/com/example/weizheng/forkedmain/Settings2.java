package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.content.Intent;

public class Settings2 extends AppCompatActivity {

    private static CheckBox sweet;
    private static CheckBox sour;
    private static CheckBox spicy;
    private Bundle data;
    private Boolean savePreferences;
    private int[] preferenceValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }
        savePreferences = data.getBoolean("savePreferences");
        preferenceValues = data.getIntArray("preferenceValues");
    }

    public void onTasteClick(View view){

        sweet = (CheckBox)findViewById(R.id.settings_taste_sweet);
        sour = (CheckBox)findViewById(R.id.settings_taste_sour);
        spicy = (CheckBox)findViewById(R.id.settings_taste_spicy);

        if(savePreferences){

            if(sweet.isChecked()){
                preferenceValues[5]=1;
            }else{ preferenceValues[5]=0; }

            if(sour.isChecked()){
                preferenceValues[6]=1;
            }else{ preferenceValues[6]=0; }

            if(spicy.isChecked()){
                preferenceValues[7]=1;
            }else{ preferenceValues[7]=0; }

        }else {//add temp reference?}

        Intent i = new Intent(this, Settings3.class);
        i.putExtra("saveRecipe",false);
        i.putExtra("cuisineValues", preferenceValues);
        startActivity(i);
    }
}
