package com.example.weizheng.forkedmain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        DatabaseReference recipesReference = myFirebaseRef.child("Recipe List");
        recipesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                String[] recipes = new String[5];
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String name = postSnapshot.getKey();
                    recipes[i]=name;
                    i++;
                    if(i>4){break;}
                }
                ListAdapter adapter = new resultAdapter(getApplicationContext(), recipes);
                ListView list = (ListView) findViewById(R.id.result_list_view);
                list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
