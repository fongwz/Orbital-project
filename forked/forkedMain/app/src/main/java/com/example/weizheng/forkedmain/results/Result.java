package com.example.weizheng.forkedmain.results;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loading_screen);
        animateLoadScreen();

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

                        int repeatNum = recipes.size()-5;
                        int[] removeNumArray = generateNumbers(recipes.size(),repeatNum);
                        for(int j=0; j<repeatNum; j++){
                            recipes.remove(removeNumArray[j]);
                        }
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

                            int repeatNum = recipes.size()-5;
                            int[] removeNumArray = generateNumbers(recipes.size(),repeatNum);
                            for(int j=0; j<repeatNum; j++){
                                recipes.remove(removeNumArray[j]);
                            }
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

    public void animateLoadScreen(){

        Log.i(animTAG, "started animation for loadscreen");
        final ImageView viewRight = (ImageView) findViewById(R.id.loading_screen_icon_right);
        final ImageView viewLeft = (ImageView) findViewById(R.id.loading_screen_icon_left);

        final RotateAnimation anim = new RotateAnimation(0, 20, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(400);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setInterpolator(new LinearInterpolator());

        final RotateAnimation anim2 = new RotateAnimation(20, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim2.setDuration(400);
        anim2.setRepeatCount(Animation.INFINITE);
        anim2.setInterpolator(new LinearInterpolator());

        final RotateAnimation anim3 = new RotateAnimation(340, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim3.setDuration(400);
        anim3.setRepeatCount(Animation.INFINITE);
        anim3.setInterpolator(new LinearInterpolator());

        final RotateAnimation anim4 = new RotateAnimation(360, 340, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim4.setDuration(400);
        anim4.setRepeatCount(Animation.INFINITE);
        anim4.setInterpolator(new LinearInterpolator());


        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(animTAG, "loading anim2");
                viewRight.startAnimation(anim2);

            }
        });


        anim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i(animTAG, "loading anim1");
                viewRight.startAnimation(anim);

            }
        });

        anim3.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                viewLeft.startAnimation(anim4);
            }
        });

        anim4.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                int i=0;
                viewLeft.startAnimation(anim3);
                i++;
                Log.i(TAG, Integer.valueOf(i).toString());
            }
        });

        viewLeft.startAnimation(anim4);
        viewRight.startAnimation(anim);
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

    public int[] generateNumbers(int max, int repeatCount){

        int val = random.nextInt(max);
        int[] numArray = new int[repeatCount];
        ArrayList<Integer> numList = new ArrayList<Integer>();
        for(int i = 0;i<repeatCount;i++){
            //generate random number
            //if random number not inside arraylist, add to numArray
            //if inside arraylist, generate again.

            while(numList.contains(val)){
                val = random.nextInt(max);
            }
            numList.add(val);
            numArray[i]=val;
            val = random.nextInt(max);
        }
        return numArray;
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, LoggedInPage.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
        finish();
    }

}
