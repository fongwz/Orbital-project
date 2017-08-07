package com.example.weizheng.forkedmain.results;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
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


import java.util.ArrayList;
import java.util.Random;

public class Result extends AppCompatActivity {

    private Bundle data;
    private Random random;
    private int[] firebasePreferences;
    private int[] tempPreferences;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;
    private static final String TAG = "Results";
    private static final String animTAG = "Animations";
    private static final String numTAG = "Numbers";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loading_screen);

        random = new Random();
        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();

        data = getIntent().getExtras();
        new FetchData().execute(data);
    }

    public View showResults(Bundle data){

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.results_result, null);

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
                    ArrayList<String> recipes = new ArrayList<String>();
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        DataSnapshot categoryValues = postSnapshot.child("Categories");
                        if(giveList(firebasePreferences, categoryValues)){
                            String name = postSnapshot.getKey();
                            recipes.add(name);
                            i++;
                        }
                    }
                    if(recipes.size()>5){
                        //repeat num generator process size-5 times
                        //delete the recipes in those num positions
                        Log.i(numTAG, "Result size : " + String.valueOf(recipes.size()));
                        recipes = pruneArrayList(recipes);

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

                        String[] recipesArray = new String[recipes.size()];
                        ListAdapter adapter = new resultAdapter(Result.this, recipes.toArray(recipesArray));
                        ListView list = (ListView) view.findViewById(R.id.result_list_view);
                        list.setDivider(null);
                        list.setDividerHeight(0);
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
                    ArrayList<String> recipes = new ArrayList<String>();
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        DataSnapshot categoryValues = postSnapshot.child("Categories");
                        if(giveList(tempPreferences, categoryValues)){
                            String name = postSnapshot.getKey();
                            recipes.add(name);
                            i++;
                        }
                        if(recipes.size()>5){
                            //repeat num generator process size-5 times
                            //delete the recipes in those num positions

                            Log.i(numTAG, "Recipe size : " + String.valueOf(recipes.size()));
                            recipes = pruneArrayList(recipes);
                        }
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

                        String[] recipeArray = new String[recipes.size()];
                        ListAdapter adapter = new resultAdapter(Result.this, recipes.toArray(recipeArray));
                        ListView list = (ListView) view.findViewById(R.id.result_list_view);
                        list.setDivider(null);
                        list.setDividerHeight(0);
                        list.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return view;
    }

    public boolean giveList(final int[] mPreferences, DataSnapshot categoryValues){
        /** Take in preference values, filter according to result, and returns a boolean value to indicate whether recipe should be added to list */

        int i = 0;
        for(DataSnapshot postSnapshot : categoryValues.getChildren()) {
            String preferenceValue = String.valueOf(mPreferences[i]);
            String recipeValue = postSnapshot.getValue().toString();
            Log.i(TAG, preferenceValue + " : " + recipeValue);
            if (recipeValue.equals("1")) {
                if (!preferenceValue.equals(recipeValue)) {
                    Log.i(TAG, "failed comparison");
                    return false;
                } else {
                    i++;
                    continue;
                }
            } else {
                i++;
                continue;
            }
        }
        Log.i(TAG, "passed comparison");
        return true;
            /** Old version ********************
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
        return true; */
    }

    public int[] getUserPreference(){

        final int[] preferences = new int[13];

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


    private class FetchData extends AsyncTask<Bundle, Void, View> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected View doInBackground(Bundle... data) {
            View view = showResults(data[0]);
            return view;
        }

        @Override
        protected void onPostExecute(View view) {
            setContentView(view);
            super.onPostExecute(view);
        }
    }

    public ArrayList<String> pruneArrayList(ArrayList<String> list){

        int pruneCount = list.size()-5;
        Log.i(numTAG, "Prune count : " + pruneCount);
        for(int i=0 ; i<pruneCount ; i++){

            int pruneIndex = random.nextInt(list.size());
            Log.i(numTAG, "Prune index : " + pruneIndex);
            list.remove(pruneIndex);

        }
        return list;
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, LoggedInPage.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        finish();
    }

}
