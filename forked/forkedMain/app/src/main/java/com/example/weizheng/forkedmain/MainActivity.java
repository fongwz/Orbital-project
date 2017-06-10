package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText was;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        was=(EditText)findViewById(R.id.editText2);
    }

    public void onClick(View view){
        Intent i = new Intent(this, LoggedInPage.class);
        String text=was.getText().toString();
        if(text.equals("hi")) {
            startActivity(i);
        }
    }
}
