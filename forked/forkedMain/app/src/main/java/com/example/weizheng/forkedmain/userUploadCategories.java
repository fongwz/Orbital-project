package com.example.weizheng.forkedmain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;


public class userUploadCategories extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_upload_categories, container, false);

        final CheckBox chinese = (CheckBox) rootView.findViewById(R.id.user_upload_categories_chinese);
        final CheckBox malay = (CheckBox) rootView.findViewById(R.id.user_upload_categories_malay);
        final CheckBox indian = (CheckBox) rootView.findViewById(R.id.user_upload_categories_indian);
        final CheckBox western = (CheckBox) rootView.findViewById(R.id.user_upload_categories_western);
        final CheckBox korean = (CheckBox) rootView.findViewById(R.id.user_upload_categories_korean);

        final CheckBox sweet = (CheckBox) rootView.findViewById(R.id.user_upload_categories_sweet);
        final CheckBox sour = (CheckBox) rootView.findViewById(R.id.user_upload_categories_sour);
        final CheckBox spicy = (CheckBox) rootView.findViewById(R.id.user_upload_categories_spicy);

        final CheckBox meat = (CheckBox) rootView.findViewById(R.id.user_upload_categories_meat);
        final CheckBox seafood = (CheckBox) rootView.findViewById(R.id.user_upload_categories_seafood);
        final CheckBox vegetables = (CheckBox) rootView.findViewById(R.id.user_upload_categories_vegetables);
        final CheckBox dessert = (CheckBox) rootView.findViewById(R.id.user_upload_categories_dessert);

        userUploadSlide.myBundle.putInt("isChinese", 0);
        userUploadSlide.myBundle.putInt("isMalay", 0);
        userUploadSlide.myBundle.putInt("isIndian", 0);
        userUploadSlide.myBundle.putInt("isWestern", 0);
        userUploadSlide.myBundle.putInt("isKorean", 0);
        userUploadSlide.myBundle.putInt("isSweet", 0);
        userUploadSlide.myBundle.putInt("isSour", 0);
        userUploadSlide.myBundle.putInt("isSpicy", 0);
        userUploadSlide.myBundle.putInt("isMeat", 0);
        userUploadSlide.myBundle.putInt("isSeafood", 0);
        userUploadSlide.myBundle.putInt("isVegetables", 0);
        userUploadSlide.myBundle.putInt("isDessert", 0);


        chinese.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isChinese = chinese.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isChinese", isChinese);
                    }
                }
        );

        malay.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isMalay = malay.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isMalay", isMalay);
                    }
                }
        );

        indian.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isIndian = indian.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isIndian", isIndian);
                    }
                }
        );

        western.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isWestern = western.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isWestern", isWestern);
                    }
                }
        );

        korean.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isKorean = korean.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isKorean", isKorean);
                    }
                }
        );

        sweet.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isSweet = sweet.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isSweet", isSweet);
                    }
                }
        );

        sour.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isSour = sour.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isSour", isSour);
                    }
                }
        );

        spicy.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isSpicy = spicy.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isSpicy", isSpicy);
                    }
                }
        );

        meat.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isMeat = meat.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isMeat", isMeat);
                    }
                }
        );

        seafood.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isSeafood = seafood.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isSeafood", isSeafood);
                    }
                }
        );

        vegetables.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isVegetables = vegetables.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isVegetables", isVegetables);
                    }
                }
        );

        dessert.setOnClickListener(
                new CheckBox.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int isDessert = dessert.isChecked() ? 1 : 0;
                        userUploadSlide.myBundle.putInt("isDessert", isDessert);
                    }
                }
        );
        return rootView;
    }
}
