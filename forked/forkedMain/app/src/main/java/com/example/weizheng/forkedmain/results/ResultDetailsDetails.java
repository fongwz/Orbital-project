package com.example.weizheng.forkedmain.results;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weizheng.forkedmain.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultDetailsDetails extends Fragment {

    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.results_result_detail_detail, container, false);

        String recipeName = ResultDetails.resultData.getString("recipeName");
        myFirebaseDatabase = FirebaseDatabase.getInstance(); //reference to root directory
        myFirebaseRef = myFirebaseDatabase.getReference();

        DatabaseReference recipeReference = myFirebaseRef.child("Recipe List").child(recipeName);
        DatabaseReference ingredientReference = recipeReference.child("Ingredients");
        DatabaseReference stepsReference = recipeReference.child("Steps");

        ingredientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float ingredientTranslation = 0;
                float stepTranslation = 0;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Resources r = getResources();
                    RelativeLayout bottomLayout = (RelativeLayout) rootView.findViewById(R.id.result_detail_detail_steps_layout);
                    RelativeLayout topLayout = (RelativeLayout) rootView.findViewById(R.id.result_detail_detail_ingredient_layout);

                    /** Creating new TextView to store ingredients name */
                    TextView addIngredientName = new TextView(getActivity());
                    RelativeLayout.LayoutParams addIngredientNameParams = new RelativeLayout.LayoutParams(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, r.getDisplayMetrics()),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
                    );
                    addIngredientNameParams.addRule(RelativeLayout.ALIGN_START, R.id.result_detail_detail_textview1);
                    addIngredientNameParams.addRule(RelativeLayout.BELOW, R.id.result_detail_detail_textview1);
                    addIngredientName.setLayoutParams(addIngredientNameParams);
                    addIngredientName.setPadding(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()),
                            0,
                            0,
                            0
                    );
                    addIngredientName.setBackgroundResource(R.drawable.user_upload_rectangle_border);
                    addIngredientName.setText(postSnapshot.getKey());
                    addIngredientName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    addIngredientName.setTextColor(Color.BLACK);
                    addIngredientName.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientTranslation, r.getDisplayMetrics()));
                    topLayout.addView(addIngredientName);


                    /** Creating new TextView to store ingredients qty */
                    TextView addQty = new TextView(getActivity());
                    RelativeLayout.LayoutParams addQtyParams = new RelativeLayout.LayoutParams(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics()),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
                    );
                    addQtyParams.addRule(RelativeLayout.ALIGN_START, R.id.result_detail_detail_textview1);
                    addQtyParams.addRule(RelativeLayout.BELOW, R.id.result_detail_detail_textview1);
                    addQtyParams.setMargins(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, r.getDisplayMetrics()),
                            0,
                            0,
                            0
                    );
                    addQty.setLayoutParams(addQtyParams);
                    addQty.setPadding(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()),
                            0,
                            0,
                            0
                    );
                    addQty.setBackgroundResource(R.drawable.user_upload_rectangle_border);
                    addQty.setText(postSnapshot.getValue().toString());
                    addQty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    addQty.setTextColor(Color.BLACK);
                    addQty.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientTranslation, r.getDisplayMetrics()));
                    topLayout.addView(addQty);

                    /** Readjusting layout */
                    topLayout.getLayoutParams().height+=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics()));
                    //topLayout.setMinimumHeight((topLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));

                    /** Shifting steps downwards */
                    stepTranslation += 30;
                    ingredientTranslation += 30;
                    RelativeLayout stepLayout = (RelativeLayout)rootView.findViewById(R.id.result_detail_detail_steps_layout);
                    stepLayout.setTranslationY((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, stepTranslation, r.getDisplayMetrics()));

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        stepsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float stepsTranslation = 0;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Resources r = getResources();
                    RelativeLayout bottomLayout = (RelativeLayout) rootView.findViewById(R.id.result_detail_detail_steps_layout);
                    RelativeLayout topLayout = (RelativeLayout) rootView.findViewById(R.id.result_detail_detail_ingredient_layout);


                    /** Creating new TextView to store steps */
                    TextView addSteps = new TextView(getActivity());
                    RelativeLayout.LayoutParams addStepsParams = new RelativeLayout.LayoutParams(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics()),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
                    );
                    addStepsParams.addRule(RelativeLayout.ALIGN_START, R.id.result_detail_detail_textview2);
                    addStepsParams.addRule(RelativeLayout.BELOW, R.id.result_detail_detail_textview2);
                    addSteps.setLayoutParams(addStepsParams);
                    addSteps.setPadding(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()),
                            0,
                            0,
                            0
                    );
                    addSteps.setBackgroundResource(R.drawable.user_upload_rectangle_border);
                    addSteps.setText(postSnapshot.getValue().toString());
                    addSteps.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    addSteps.setTextColor(Color.BLACK);
                    addSteps.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, stepsTranslation, r.getDisplayMetrics()));
                    bottomLayout.addView(addSteps);

                    /** Readjusting Layout */
                    //topLayout.setMinimumHeight((topLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
                    //bottomLayout.setMinimumHeight((bottomLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
                    topLayout.getLayoutParams().height+=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics()));
                    bottomLayout.getLayoutParams().height+=((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics()));
                    stepsTranslation+=30;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "error for steps", Toast.LENGTH_SHORT).show();
            }
        });



        return rootView;
    }

}