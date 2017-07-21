package com.example.weizheng.forkedmain.results;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.weizheng.forkedmain.homescreens.LoggedInPage;
import com.example.weizheng.forkedmain.R;
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
    private static final String TAG = "Results";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_result);

        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();


        data = getIntent().getExtras();
        if(data==null){

            /** Taking user preference data from firebase to compare */
            firebasePreferences = new int[12];
            firebasePreferences = getUserPreference();

            DatabaseReference recipesReference = myFirebaseRef.child("Recipe List");
            Log.i(TAG, "created reference to list of recipes");
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
                    Log.i(TAG, "created string array to display in adapter");


                    if( i==0 ){
                        Log.i(TAG,"no results found");
                        AlertDialog.Builder builder = new AlertDialog.Builder(Result.this);
                        builder.setMessage("Sorry! No results were found with your preferences.")
                                .setCancelable(false)
                                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(Result.this, LoggedInPage.class);
                                        finish();
                                        startActivity(i);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        Log.i(TAG, "loading adapter");
                        ListAdapter adapter = new resultAdapter(getApplicationContext(), recipes);
                        ListView list = (ListView) findViewById(R.id.result_list_view);
                        list.setAdapter(adapter);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            /** Discover a recipe */
            tempPreferences = data.getIntArray("preferences");
            for(int j=0;j<12;j++){
                Log.i(TAG, String.valueOf(tempPreferences[j]));
            }
            DatabaseReference recipesReference = myFirebaseRef.child("Recipe List");
            Log.i(TAG, "created reference to list of recipes");
            recipesReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i=0;
                    String[] recipes = new String[5];
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        DataSnapshot categoryValues = postSnapshot.child("Categories");
                        if(giveList(tempPreferences, categoryValues)){
                            String name = postSnapshot.getKey();
                            recipes[i]=name;
                            i++;
                        }
                        if(i>4){break;}
                    }
                    Log.i(TAG, "created string array to display in adapter");

                    if( i == 0){
                        Log.i(TAG,"no results found");
                        AlertDialog.Builder builder = new AlertDialog.Builder(Result.this);
                        builder.setMessage("Sorry! No results were found with your preferences.")
                                .setCancelable(false)
                                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent i = new Intent(Result.this, LoggedInPage.class);
                                        finish();
                                        startActivity(i);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        ListAdapter adapter = new resultAdapter(getApplicationContext(), recipes);
                        ListView list = (ListView) findViewById(R.id.result_list_view);
                        list.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public boolean giveList(final int[] mPreferences, DataSnapshot categoryValues){
        /** Take in preference values, filter according to result, and returns a boolean value to indicate whether recipe should be added to list */

        int i = 0;
        for(DataSnapshot postSnapshot : categoryValues.getChildren()){
            String recipeValue = postSnapshot.getValue().toString();
            String preferenceValue = String.valueOf(mPreferences[i]);
            Log.i(TAG, preferenceValue + " : " + recipeValue);
            if( Integer.valueOf(postSnapshot.getValue().toString()) != mPreferences[i] ){
                Log.i(TAG, "failed comparison");
                return false;
            } else {
                i++;
                continue;
            }
        }
        Log.i(TAG, "passed comparison");
        return true;
    }

    public int[] getUserPreference(){

        final int[] preferences = new int[12];

        /*** Obtains user preference values from FireBase database */
        DatabaseReference preferenceReference = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Preferences");
        Log.i(TAG, "created reference to firebase preferences");
        preferenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;

                Log.i(TAG, "starting retrieval");
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    preferences[i] = Integer.valueOf(postSnapshot.getValue().toString());
                    i++;
                }
                Log.i(TAG, "retrieval success");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String error = databaseError.getMessage();
                String details = String.valueOf(databaseError.getCode());
                Log.i(TAG, "retrieval failed\n " + error + "\n " + details);
            }
        });

        return preferences;
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, LoggedInPage.class);
        finish();
        startActivity(i);
    }
}
