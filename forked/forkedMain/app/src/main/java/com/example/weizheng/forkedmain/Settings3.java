package com.example.weizheng.forkedmain;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.content.Intent;
import android.widget.Toast;

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
    private boolean updatePreferences;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings3);

        data = getIntent().getExtras();
        if(data==null){
            return;
        }
        preferenceValues = data.getIntArray("preferenceValues");
        updatePreferences = data.getBoolean("updatePreferences");
        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();

    }

    public void onDishClick(View view) {
        meat = (CheckBox) findViewById(R.id.checkboxMeat);
        seafood = (CheckBox) findViewById(R.id.checkBoxFish);
        veg = (CheckBox) findViewById(R.id.checkBoxVeg);
        dessert = (CheckBox) findViewById(R.id.checkBoxDessert);

        if (meat.isChecked()) {
            preferenceValues[5] = 1;
        } else {
            preferenceValues[5] = 0;
        }

        if (seafood.isChecked()) {
            preferenceValues[6] = 1;
        } else {
            preferenceValues[6] = 0;
        }

        if (veg.isChecked()) {
            preferenceValues[10] = 1;
        } else {
            preferenceValues[10] = 0;
        }

        if (dessert.isChecked()) {
            preferenceValues[1] = 1;
        } else {
            preferenceValues[1] = 0;
        }

        if (updatePreferences) {

            DatabaseReference preferenceReference = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Preferences");
            preferenceReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    for(DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        postSnapShot.getRef().setValue(preferenceValues[i]);
                        i++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            Intent i = new Intent(this, LoggedInPage.class);
            Toast.makeText(this, "Preferences Updated", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(i);

        } else {
            Intent i = new Intent(this, Result.class);
            i.putExtra("preferences", preferenceValues);
            finish();
            startActivity(i);
        }
    }
}
