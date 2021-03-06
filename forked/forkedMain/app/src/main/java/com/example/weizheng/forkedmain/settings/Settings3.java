package com.example.weizheng.forkedmain.settings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.Toast;

import com.example.weizheng.forkedmain.homescreens.LoggedInPage;
import com.example.weizheng.forkedmain.R;
import com.example.weizheng.forkedmain.results.Result;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings3 extends AppCompatActivity {

    private static CheckBox meat;
    private static CheckBox seafood;
    private static CheckBox veg;
    private static CheckBox dessert;
    private Bundle data;
    private int[] preferenceValues;
    private Boolean updatePreferences;
    private Boolean setupPreferences;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;
    private static final String TAG = "Results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.settings_settings3);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }
        preferenceValues = data.getIntArray("preferenceValues");
        updatePreferences = data.getBoolean("updatePreferences");
        setupPreferences = data.getBoolean("setupPreferences");
        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();

        Log.i(TAG,updatePreferences.toString());
        Log.i(TAG,setupPreferences.toString());

    }

    public void onDishClick(View view) {
        meat = (CheckBox) findViewById(R.id.settings_dish_meat);
        seafood = (CheckBox) findViewById(R.id.settings_dish_seafood);
        veg = (CheckBox) findViewById(R.id.settings_dish_vegetables);
        dessert = (CheckBox) findViewById(R.id.settings_dish_dessert);

        if (meat.isChecked()) {
            preferenceValues[5] = 1;
        } else {
            preferenceValues[5] = 0;
        }

        if (seafood.isChecked()) {
            preferenceValues[7] = 1;
        } else {
            preferenceValues[7] = 0;
        }

        if (veg.isChecked()) {
            preferenceValues[11] = 1;
        } else {
            preferenceValues[11] = 0;
        }

        if (dessert.isChecked()) {
            preferenceValues[1] = 1;
        } else {
            preferenceValues[1] = 0;
        }


        if(setupPreferences) {

            DatabaseReference preferenceReference = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Preferences");
            preferenceReference.child("isChinese").setValue(preferenceValues[0]);
            preferenceReference.child("isDessert").setValue(preferenceValues[1]);
            preferenceReference.child("isIndian").setValue(preferenceValues[2]);
            preferenceReference.child("isKorean").setValue(preferenceValues[3]);
            preferenceReference.child("isMalay").setValue(preferenceValues[4]);
            preferenceReference.child("isMeat").setValue(preferenceValues[5]);
            preferenceReference.child("isSalty").setValue(preferenceValues[6]);
            preferenceReference.child("isSeafood").setValue(preferenceValues[7]);
            preferenceReference.child("isSour").setValue(preferenceValues[8]);
            preferenceReference.child("isSpicy").setValue(preferenceValues[9]);
            preferenceReference.child("isSweet").setValue(preferenceValues[10]);
            preferenceReference.child("isVegetables").setValue(preferenceValues[11]);
            preferenceReference.child("isWestern").setValue(preferenceValues[12]);

            Intent i = new Intent(this, LoggedInPage.class);
            i.putExtra("firstSetup", true);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

        } else if (updatePreferences) {

            DatabaseReference preferenceReference = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Preferences");
            preferenceReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    for(DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        postSnapShot.getRef().setValue(preferenceValues[i]).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(TAG, e.getMessage());
                            }
                        });
                        i++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i(TAG, databaseError.getMessage());
                }
            });

            Intent i = new Intent(this, LoggedInPage.class);
            Toast.makeText(this, "Preferences Updated", Toast.LENGTH_SHORT).show();
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

        } else {
            Intent i = new Intent(this, Result.class);
            i.putExtra("preferences", preferenceValues);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
    }
}
