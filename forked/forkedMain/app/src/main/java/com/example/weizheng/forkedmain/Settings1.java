package com.example.weizheng.forkedmain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.view.View;

public class Settings1 extends AppCompatActivity {

    private static CheckBox chinese;
    private static CheckBox malay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings1);
    }

    public void onCuisineClick(View view){
        chinese = (CheckBox)findViewById(R.id.settings_cuisine_chinese);
        malay = (CheckBox)findViewById(R.id.settings_cuisine_malay);

        if (chinese.isChecked()) {
        }//add chinese to temporary user preferences
        if (malay.isChecked()) {
        }//add malay to temporary user preferences

        Intent i = new Intent(this, Settings2.class);
        i.putExtra("saveRecipe",false);
        startActivity(i);
    }
}
