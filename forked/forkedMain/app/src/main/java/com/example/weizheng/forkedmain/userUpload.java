package com.example.weizheng.forkedmain;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class userUpload extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 1;
    private float ingredientButtonTranslation = 0;
    private float ingredientTextTranslation = 0;
    private Integer stepNumber = 2;
    private float recipeLayoutTranslation = 0;
    private float recipeButtonTranslation = 0;
    private float recipeTextTranslation = 0;
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_upload);

        imageview = (ImageView)findViewById(R.id.upload_recipe_imageView);
    }

    /**** Uploading Image *********************************************************/

    public void uploadImage(View view) {

        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GET_FROM_GALLERY);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap image = BitmapFactory.decodeStream(inputStream);

                imageview.setImageBitmap(image);


            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                        .show();
            }
        }

    }

    /**** Adding Ingredients ******************************************************/
    public void addIngredient(View view) {

        Resources r = getResources();
        RelativeLayout wzsLayout = (RelativeLayout) findViewById(R.id.upload_recipe_top_relative_layout);

        /** Creating new Edit Text for name */
        EditText addName = new EditText(this);
        RelativeLayout.LayoutParams addNameParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
        );
        addNameParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_ingredients_name);
        addNameParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_ingredients_name);
        addName.setLayoutParams(addNameParams);
        addName.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientTextTranslation, r.getDisplayMetrics()));
        addName.setBackgroundResource(R.drawable.user_upload_rectangle_border);
        wzsLayout.addView(addName);


        /** Creating new Edit Text for qty */
        EditText addQty = new EditText(this);
        RelativeLayout.LayoutParams addQtyParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
        );
        addQtyParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_ingredients_qty);
        addQtyParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_ingredients_qty);
        addQty.setLayoutParams(addQtyParams);
        addQty.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientTextTranslation, r.getDisplayMetrics()));
        addQty.setBackgroundResource(R.drawable.user_upload_rectangle_border);
        wzsLayout.addView(addQty);

        /** Translating button downwards */
        Button ingredientButton = (Button) findViewById(R.id.upload_recipe_ingredients_button);
        ingredientButtonTranslation+=30;
        ingredientButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientButtonTranslation, r.getDisplayMetrics()));

        /** Updating Edit Text translation */
        ingredientTextTranslation += 30;

        /** Shifting recipe section downwards */
        recipeLayoutTranslation += 30;
        RelativeLayout wzsBottomLayout = (RelativeLayout)findViewById(R.id.upload_recipe_bottom_relative_layout);
        wzsBottomLayout.setTranslationY((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeLayoutTranslation, r.getDisplayMetrics()));

        /** Readjusting Layout */
        wzsLayout.setMinimumHeight((wzsLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
    }

    /**** Adding Recipe ***********************************************************/
    public void addRecipe(View view){

        Resources r = getResources();
        RelativeLayout wzsTopLayout = (RelativeLayout)findViewById(R.id.upload_recipe_top_relative_layout);
        RelativeLayout wzsBottomLayout =(RelativeLayout)findViewById(R.id.upload_recipe_bottom_relative_layout);

        /** Creating new Step number */
        TextView addStepNum = new TextView(this);
        RelativeLayout.LayoutParams addStepNumParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
        );
        addStepNumParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_recipe_num);
        addStepNumParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_recipe_num);
        addStepNum.setLayoutParams(addStepNumParams);
        addStepNum.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeTextTranslation, r.getDisplayMetrics()));
        addStepNum.setText(stepNumber.toString() + ")");
        wzsBottomLayout.addView(addStepNum);

        /** Creating new Step name */
        EditText addStepName = new EditText(this);
        RelativeLayout.LayoutParams addStepNameParams = new RelativeLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, r.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())
        );
        addStepNameParams.addRule(RelativeLayout.BELOW, R.id.upload_recipe_recipe_name);
        addStepNameParams.addRule(RelativeLayout.ALIGN_START, R.id.upload_recipe_recipe_name);
        addStepName.setLayoutParams(addStepNameParams);
        addStepName.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeTextTranslation, r.getDisplayMetrics()));
        addStepName.setBackgroundResource(R.drawable.user_upload_rectangle_border);
        wzsBottomLayout.addView(addStepName);

        /** Updating Step parameters */
        stepNumber++;
        recipeTextTranslation += 30;

        /** Translating button downwards */
        Button recipeButton = (Button) findViewById(R.id.upload_recipe_recipe_button);
        recipeButtonTranslation += 30;
        recipeButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeButtonTranslation, r.getDisplayMetrics()));

        /** Readjusting Layout */
        wzsTopLayout.setMinimumHeight((wzsTopLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
        wzsBottomLayout.setMinimumHeight((wzsBottomLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
    }
}