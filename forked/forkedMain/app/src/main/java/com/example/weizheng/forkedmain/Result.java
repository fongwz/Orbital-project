package com.example.weizheng.forkedmain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Result extends AppCompatActivity {

    private Bundle data;
    private int[] preferences;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }

        preferences = data.getIntArray("preferences");

        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();


        ListAdapter adapter = new resultAdapter(this, recipes);
        ListView list = (ListView) findViewById(R.id.result_list_view);
        list.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){}
}
