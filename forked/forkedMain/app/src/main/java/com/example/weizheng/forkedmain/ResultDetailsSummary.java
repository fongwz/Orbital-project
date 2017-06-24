package com.example.weizheng.forkedmain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Wei Zheng on 24/6/2017.
 */

public class ResultDetailsSummary extends Fragment {

    private String recipeName;
    private TextView resultDetailsSummaryTextview;
    private ImageView resultDetailsSummaryImageview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.result_detail_summary, container, false);

        recipeName = ResultDetails.resultData.getString("recipeName");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child(recipeName);
        resultDetailsSummaryTextview = (TextView) rootView.findViewById(R.id.result_detail_summary_textView);
        resultDetailsSummaryImageview = (ImageView) rootView.findViewById(R.id.result_detail_summary_imageView);

        // Load the image using Glide
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(storageRef)
                .into(resultDetailsSummaryImageview);

        resultDetailsSummaryTextview.setText(recipeName);

        return rootView;
    }

}
