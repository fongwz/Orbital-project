package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.content.Intent;

public class Settings3 extends AppCompatActivity {

    private static CheckBox meat;
    private static CheckBox seafood;
    private static CheckBox veg;
    private static CheckBox dessert;
    private Bundle data;
    private Boolean savePreferences;
    private int[] preferenceValues;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings3);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }
        savePreferences = data.getBoolean("savePreferences");
        preferenceValues = data.getIntArray("preferenceValues");

    }

    public void onDishClick(View view) {
        meat = (CheckBox) findViewById(R.id.checkboxMeat);
        seafood = (CheckBox) findViewById(R.id.checkBoxFish);
        veg = (CheckBox) findViewById(R.id.checkBoxVeg);
        dessert = (CheckBox) findViewById(R.id.checkBoxDessert);

        if (meat.isChecked()) {
            preferenceValues[8] = 1;
        } else {
            preferenceValues[8] = 0;
        }

        if (seafood.isChecked()) {
            preferenceValues[9] = 1;
        } else {
            preferenceValues[9] = 0;
        }

        if (veg.isChecked()) {
            preferenceValues[10] = 1;
        } else {
            preferenceValues[10] = 0;
        }

        if (dessert.isChecked()) {
            preferenceValues[11] = 1;
        } else {
            preferenceValues[11] = 0;
        }

        Preferences preferences = new Preferences(
                preferenceValues[0],
                preferenceValues[1],
                preferenceValues[2],
                preferenceValues[3],
                preferenceValues[4],
                preferenceValues[5],
                preferenceValues[6],
                preferenceValues[7],
                preferenceValues[8],
                preferenceValues[9],
                preferenceValues[10],
                preferenceValues[11]
        );

        if(savePreferences) {
            dbHandler = new MyDBHandler(this, null, null, 1);
            dbHandler.addSavePreferences(preferences);
        }else{
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHandler.addTempPreferences(preferences);
        //perform online check(discover a recipe)
        dbHandler.deleteTempPreferences(); // finished using temp references
        }
    }
}
