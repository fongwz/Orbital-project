package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.content.Intent;

public class Settings2 extends AppCompatActivity {

    private static CheckBox sweet;
    private static CheckBox sour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
    }

    public void onTasteClick(View view){

        sweet = (CheckBox)findViewById(R.id.settings_taste_sweet);
        sour = (CheckBox)findViewById(R.id.settings_taste_sour);

        if(sweet.isChecked()){} //enable sweet in user preference
        if(sour.isChecked()){} //enable sour in user preference

        Intent i = new Intent(this, Settings3.class); // activity main is a placeholder. Create a settings 3 class for dish types(meat, fish etc)
        startActivity(i);
    }
}
