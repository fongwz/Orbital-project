package com.example.weizheng.forkedmain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;



public class resultAdapter extends ArrayAdapter<String> {

    private static final String TAG = "Results";

    public resultAdapter(@NonNull Context context, String[] recipes) {
        super(context, R.layout.custom_row, recipes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater wzsInflator = LayoutInflater.from(getContext());
        final View customView = wzsInflator.inflate(R.layout.custom_row, parent, false);


        String recipeName = getItem(position);
        TextView wzsText = (TextView) customView.findViewById(R.id.resultText);
        ImageView wzsImage = (ImageView) customView.findViewById(R.id.resultImage);

        wzsText.setText(recipeName);
        wzsText.setTextColor(Color.BLACK);

        FirebaseStorage myFirebaseStorage = FirebaseStorage.getInstance();
        try {
            StorageReference storageRef = myFirebaseStorage.getReference().child(recipeName);
            Glide.with(getContext() /* context */)
                    .using(new FirebaseImageLoader())
                    .load(storageRef)
                    .into(wzsImage);
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }

        customView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView myText = (TextView) customView.findViewById(R.id.resultText);
                        String name = myText.getText().toString();
                        Intent i = new Intent(v.getContext(), ResultDetails.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("recipeName", name);
                        v.getContext().startActivity(i);
                    }
                }
        );
        return customView;
    }
}