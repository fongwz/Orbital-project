package com.example.weizheng.forkedmain.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.content.Intent;

import com.example.weizheng.forkedmain.R;

public class    Settings2 extends AppCompatActivity {

    private static CheckBox sweet;
    private static CheckBox sour;
    private static CheckBox spicy;
    private Bundle data;
    private Boolean updatePreferences;
    private Boolean setupPreferences;
    private int[] preferenceValues;
    static Settings2 mRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.settings_settings2);
        mRemove = this;


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
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
    }

    public static Settings2 getInstance(){
        return mRemove;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
    }

}
