package com.example.weizheng.forkedmain.profilesettings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.weizheng.forkedmain.R;
import com.example.weizheng.forkedmain.settings.Settings1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileMyPreferences extends AppCompatActivity {

    private TextView chineseYN;
    private TextView malayYN;
    private TextView indianYN;
    private TextView westernYN;
    private TextView koreanYN;
    private TextView sweetYN;
    private TextView sourYN;
    private TextView spicyYN;
    private TextView meatYN;
    private TextView seafoodYN;
    private TextView vegetableYN;
    private TextView dessertYN;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;
    private static final String TAG = "Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesettings_profile_my_preferences);

        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference mRef = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Preferences");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                chineseYN = (TextView) findViewById(R.id.profile_preferences_chinese_yn);
                malayYN = (TextView) findViewById(R.id.profile_preferences_malay_yn);
                indianYN = (TextView) findViewById(R.id.profile_preferences_indian_yn);
                westernYN = (TextView) findViewById(R.id.profile_preferences_western_yn);
                koreanYN = (TextView) findViewById(R.id.profile_preferences_korean_yn);
                sweetYN = (TextView) findViewById(R.id.profile_preferences_sweet_yn);
                sourYN = (TextView) findViewById(R.id.profile_preferences_sour_yn);
                spicyYN = (TextView) findViewById(R.id.profile_preferences_spicy_yn);
                meatYN = (TextView) findViewById(R.id.profile_preferences_meat_yn);
                seafoodYN = (TextView) findViewById(R.id.profile_preferences_seafood_yn);
                vegetableYN = (TextView) findViewById(R.id.profile_preferences_vegetables_yn);
                dessertYN = (TextView) findViewById(R.id.profile_preferences_dessert_yn);

                chineseYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isChinese").getValue().toString())));
                malayYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isMalay").getValue().toString())));
                indianYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isIndian").getValue().toString())));
                westernYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isWestern").getValue().toString())));
                koreanYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isKorean").getValue().toString())));
                sweetYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isSweet").getValue().toString())));
                sourYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isSour").getValue().toString())));
                spicyYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isSpicy").getValue().toString())));
                meatYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isMeat").getValue().toString())));
                seafoodYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isSeafood").getValue().toString())));
                vegetableYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isVegetables").getValue().toString())));
                dessertYN.setText(setYN(Integer.valueOf(dataSnapshot.child("isDessert").getValue().toString())));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage());
            }
        });

    }

    public String setYN(int yn){

        if(yn == 1){
            return "Yes";
        } else {
            return "No";
        }

    }

    public void profileUpdatePref(View view){

        Intent i = new Intent(this, Settings1.class);
        i.putExtra("updatePreferences", true);
        startActivity(i);

    }
}
