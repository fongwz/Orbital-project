package com.example.weizheng.forkedmain;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.InputStream;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class userUploadRecipe extends Fragment {

    private static final int GET_FROM_GALLERY = 1;
    private float ingredientButtonTranslation = 0;
    private float ingredientTextTranslation = 0;
    private Integer stepNumber = 2;
    private float recipeLayoutTranslation = 0;
    private float recipeButtonTranslation = 0;
    private float recipeTextTranslation = 0;
    private ImageView imageview;
    private Uri selectedImage;
    private FirebaseDatabase myFirebaseDatabase;
    private StorageReference myStorageRef;
    private FirebaseAuth myFirebaseAuth;
    private DatabaseReference myFirebaseRef;
    private int ingredientNameID = 0;  // increment max to 99
    private int ingredientQtyID = 100; // increment max to 199
    private int recipeID = 200;        // increment max to 299
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_user_upload, container, false);

        Button addImage = (Button) rootView.findViewById(R.id.upload_recipe_image_button);
        final Button addIngredient = (Button) rootView.findViewById(R.id.upload_recipe_ingredients_button);
        final Button addRecipe = (Button) rootView.findViewById(R.id.upload_recipe_recipe_button);

        addImage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage(v);
                    }
                }
        );

        addIngredient.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addIngredient(v);
                    }
                }
        );

        addRecipe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addRecipe(v);
                    }
                }
        );

        return rootView;
    }

    /**** Selecting Image *********************************************************/

    public void selectImage(View view) {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GET_FROM_GALLERY);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK && null != data) {

            selectedImage = data.getData();

            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(selectedImage);
                Bitmap image = BitmapFactory.decodeStream(inputStream);

                imageview.setImageBitmap(image);


            } catch (Exception e) {
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        }

    }

    /**** Adding Ingredients ******************************************************/
    public void addIngredient(View view) {

        Resources r = getResources();
        RelativeLayout wzsLayout = (RelativeLayout) rootView.findViewById(R.id.upload_recipe_top_relative_layout);

        /** Creating new Edit Text for name */
        EditText addName = new EditText(getContext());
        RelativeLayout.LayoutParams addNameParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
        );
        addNameParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_ingredients_name);
        addNameParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_ingredients_name);
        addName.setLayoutParams(addNameParams);
        addName.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientTextTranslation, r.getDisplayMetrics()));
        addName.setBackgroundResource(R.drawable.user_upload_rectangle_border);
        addName.setId(ingredientNameID);
        addName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        addName.setPadding(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()),
                0,
                0,
                0
        );
        wzsLayout.addView(addName);


        /** Creating new Edit Text for qty */
        EditText addQty = new EditText(getContext());
        RelativeLayout.LayoutParams addQtyParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
        );
        addQtyParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_ingredients_qty);
        addQtyParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_ingredients_qty);
        addQty.setLayoutParams(addQtyParams);
        addQty.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientTextTranslation, r.getDisplayMetrics()));
        addQty.setBackgroundResource(R.drawable.user_upload_rectangle_border);
        addQty.setId(ingredientQtyID);
        addQty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        addQty.setPadding(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()),
                0,
                0,
                0
        );
        wzsLayout.addView(addQty);

        /** Translating button downwards */
        Button ingredientButton = (Button) rootView.findViewById(R.id.upload_recipe_ingredients_button);
        ingredientButtonTranslation+=30;
        ingredientButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientButtonTranslation, r.getDisplayMetrics()));

        /** Updating Edit Text translation */
        ingredientTextTranslation += 30;

        /** Updating Edit Text Id numbers */
        ingredientNameID++;
        ingredientQtyID++;

        /** Shifting recipe section downwards */
        recipeLayoutTranslation += 30;
        RelativeLayout wzsBottomLayout = (RelativeLayout)rootView.findViewById(R.id.upload_recipe_bottom_relative_layout);
        wzsBottomLayout.setTranslationY((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeLayoutTranslation, r.getDisplayMetrics()));

        /** Readjusting Layout */
        wzsLayout.setMinimumHeight((wzsLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
    }

    /**** Adding Recipe ***********************************************************/
    public void addRecipe(View view){

        Resources r = getResources();
        RelativeLayout wzsTopLayout = (RelativeLayout)rootView.findViewById(R.id.upload_recipe_top_relative_layout);
        RelativeLayout wzsBottomLayout =(RelativeLayout)rootView.findViewById(R.id.upload_recipe_bottom_relative_layout);

        /** Creating new Step number */
        TextView addStepNum = new TextView(getContext());
        RelativeLayout.LayoutParams addStepNumParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, r.getDisplayMetrics())
        );
        addStepNumParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_recipe_num);
        addStepNumParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_recipe_num);
        addStepNum.setLayoutParams(addStepNumParams);
        addStepNum.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeTextTranslation, r.getDisplayMetrics()));
        addStepNum.setText(stepNumber.toString() + ")");
        wzsBottomLayout.addView(addStepNum);

        /** Creating new Step name */
        EditText addStepName = new EditText(getContext());
        RelativeLayout.LayoutParams addStepNameParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
        );
        addStepNameParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_recipe_name);
        addStepNameParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_recipe_name);
        addStepName.setLayoutParams(addStepNameParams);
        addStepName.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeTextTranslation, r.getDisplayMetrics()));
        addStepName.setBackgroundResource(R.drawable.user_upload_rectangle_border);
        addStepName.setId(recipeID);
        addStepName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        addStepName.setPadding(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics()),
                0,
                0,
                0
        );
        wzsBottomLayout.addView(addStepName);

        /** Updating Step parameters */
        stepNumber++;
        recipeID++;
        recipeTextTranslation += 30;

        /** Translating button downwards */
        Button recipeButton = (Button) rootView.findViewById(R.id.upload_recipe_recipe_button);
        Button uploadButton = (Button) rootView.findViewById(R.id.upload_recipe_upload_button);
        recipeButtonTranslation += 30;
        recipeButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeButtonTranslation, r.getDisplayMetrics()));
        uploadButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeButtonTranslation, r.getDisplayMetrics()));

        /** Readjusting Layout */
        wzsTopLayout.setMinimumHeight((wzsTopLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
        wzsBottomLayout.setMinimumHeight((wzsBottomLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
    }

    /**** TO-DO: CREATE A BUTTON TO DELETE INGREDIENTS AND RECIPES ***************/
}
