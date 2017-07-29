package com.example.weizheng.forkedmain.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.view.View;

import com.example.weizheng.forkedmain.R;

public class Settings1 extends AppCompatActivity {

    private static CheckBox chinese;
    private static CheckBox malay;
    private static CheckBox indian;
    private static CheckBox western;
    private static CheckBox korean;
    private Bundle data;
    private Boolean updatePreferences = false;
    private Boolean setupPreferences = false;
    static Settings1 mRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.settings_settings1);
        mRemove = this;

        data = getIntent().getExtras();
        if(data!=null){
            try{
                updatePreferences = data.getBoolean("updatePreferences");
                setupPreferences = data.getBoolean("setupPreferences");
            }catch (Exception e) { }
        }
    }

    public void onCuisineClick(View view) {

        int[] preferenceValues = new int[12];
        chinese = (CheckBox) findViewById(R.id.settings_cuisine_chinese);
        malay = (CheckBox) findViewById(R.id.settings_cuisine_malay);
        indian = (CheckBox) findViewById(R.id.settings_cuisine_indian);
        western = (CheckBox) findViewById(R.id.settings_cuisine_western);
        korean = (CheckBox) findViewById(R.id.settings_cuisine_korean);

        if (chinese.isChecked()) {
            preferenceValues[0] = 1;
        } else {
            preferenceValues[0] = 0;
        }

        if (malay.isChecked()) {
            preferenceValues[4] = 1;
        } else {
            preferenceValues[4] = 0;
        }

        if (indian.isChecked()) {
            preferenceValues[2] = 1;
        } else {
            preferenceValues[2] = 0;
        }

        if (western.isChecked()) {
            preferenceValues[11] = 1;
        } else {
            preferenceValues[11] = 0;
        }

        if (korean.isChecked()) {
            preferenceValues[3] = 1;
        } else {
            preferenceValues[3] = 0;
        }


        Intent i = new Intent(this, Settings2.class);
        i.putExtra("preferenceValues", preferenceValues);
        i.putExtra("updatePreferences",updatePreferences);
        i.putExtra("setupPreferences",setupPreferences);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
    }

    public static Settings1 getInstance(){
        return mRemove;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
    }
}
