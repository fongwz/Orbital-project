package com.example.weizheng.forkedmain;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Wei Zheng on 24/6/2017.
 */

public class ResultDetailsSummary extends Fragment {

    private String recipeName;
    private TextView resultDetailsSummaryTextview;
    private ImageView resultDetailsSummaryImageview;
    private TextView resultDetailsSummaryUserDisplay;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_detail_summary, container, false);

        recipeName = ResultDetails.resultData.getString("recipeName");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(recipeName);
        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();

        resultDetailsSummaryTextview = (TextView) rootView.findViewById(R.id.result_detail_summary_textView);
        resultDetailsSummaryUserDisplay = (TextView) rootView.findViewById(R.id.result_detail_summary_userDisplay);
        resultDetailsSummaryImageview = (ImageView) rootView.findViewById(R.id.result_detail_summary_imageView);

        // Load the image using Glide
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(resultDetailsSummaryImageview);
        resultDetailsSummaryTextview.setText(recipeName);
        DatabaseReference displayRef = myFirebaseRef.child("Recipe List").child(recipeName).child("User Upload");
        displayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultDetailsSummaryUserDisplay.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

}
