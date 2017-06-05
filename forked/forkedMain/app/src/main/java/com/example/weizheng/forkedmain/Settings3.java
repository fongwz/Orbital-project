package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.content.Intent;

public class Settings3 extends AppCompatActivity {

    private static CheckBox meat;
    private static CheckBox fish;
    private static CheckBox veg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings3);

    }

    public void onDishClick(View view){
        meat = (CheckBox)findViewById(R.id.checkboxMeat);
        fish = (CheckBox)findViewById(R.id.checkBoxFish);
        veg = (CheckBox)findViewById(R.id.checkBoxVeg);

        if(meat.isChecked()){} // set user preference
        if(fish.isChecked()){} //set user preference
        if(veg.isChecked()){} //set user preference
    }
}
