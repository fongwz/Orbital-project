package com.example.weizheng.forkedmain;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class resultAdapter extends ArrayAdapter<String> {

    public resultAdapter(@NonNull Context context, String[] recipes) {
        super(context, R.layout.custom_row, recipes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater wzsInflator = LayoutInflater.from(getContext());
        View customView = wzsInflator.inflate(R.layout.custom_row, parent, false);

        final String recipeName = getItem(position);
        TextView wzsText = (TextView) customView.findViewById(R.id.resultText);
        ImageView apple = (ImageView) customView.findViewById(R.id.resultImage);

        wzsText.setText(recipeName);

        customView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                        v.startAnimation(shake);
                        final String name=recipeName;
                        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        return customView;
    }
}