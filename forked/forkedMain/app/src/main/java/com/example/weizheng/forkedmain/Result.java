package com.example.weizheng.forkedmain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Result extends AppCompatActivity {

    private Bundle data;
    private int[] firebasePreferences;
    private int[] tempPreferences;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }

        tempPreferences = data.getIntArray("preferences");

        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();
        firebasePreferences = new int[12];


        /*** Obtains user preference values from FireBase database */
        DatabaseReference preferenceReference = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Preferences");
        preferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    firebasePreferences[i] = (int) postSnapshot.getValue();
                    i++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference recipesReference = myFirebaseRef.child("Recipe List");
        recipesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i=0;
                String[] recipes = new String[5];
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    DataSnapshot categoryValues = postSnapshot.child("Categories");
                    if(giveList(firebasePreferences, categoryValues)){
                        String name = postSnapshot.getKey();
                        recipes[i]=name;
                        i++;
                    }
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

    public boolean giveList(final int[] mPreferences, DataSnapshot categoryValues){
        /** Take in preference values, filter according to result, and returns a boolean value to indicate whether recipe should be added to list */

        int i = 0;
        for(DataSnapshot postSnapshot : categoryValues.getChildren()){
            if( postSnapshot.getValue() != mPreferences[i] ){
                return false;
            } else {
                continue;
            }
        }
        return true;
    }
}
