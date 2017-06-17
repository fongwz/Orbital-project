package com.example.weizheng.forkedmain;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class userUpload extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 1;
    private float ingredientButtonTranslation = 0;
    private float ingredientTextTranslation = 0;
    private Integer stepNumber = 2;
    private float recipeLayoutTranslation = 0;
    private float recipeButtonTranslation = 0;
    private float recipeTextTranslation = 0;
    private ImageView imageview;
    private Uri selectedImage;
    private Firebase myFirebaseRef;
    private StorageReference myStorageRef;
    private FirebaseAuth myFirebaseAuth;
    private int ingredientNameID = 0;  // increment max to 99
    private int ingredientQtyID = 100; // increment max to 199
    private int recipeID = 200;        // increment max to 299

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_upload);

        imageview = (ImageView)findViewById(R.id.upload_recipe_imageView);
        Firebase.setAndroidContext(this);

        myFirebaseAuth = FirebaseAuth.getInstance();
        myStorageRef = FirebaseStorage.getInstance().getReference();
        myFirebaseRef = new Firebase("https://forked-up.firebaseio.com/"); //reference to root directory
    }

    /**** Selecting Image *********************************************************/

    public void selectImage(View view) {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GET_FROM_GALLERY);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK && null != data) {

            selectedImage = data.getData();
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
        Button ingredientButton = (Button) findViewById(R.id.upload_recipe_ingredients_button);
        ingredientButtonTranslation+=30;
        ingredientButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ingredientButtonTranslation, r.getDisplayMetrics()));

        /** Updating Edit Text translation */
        ingredientTextTranslation += 30;

        /** Updating Edit Text Id numbers */
        ingredientNameID++;
        ingredientQtyID++;

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
        Button recipeButton = (Button) findViewById(R.id.upload_recipe_recipe_button);
        Button uploadButton = (Button) findViewById(R.id.upload_recipe_upload_button);
        recipeButtonTranslation += 30;
        recipeButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeButtonTranslation, r.getDisplayMetrics()));
        uploadButton.setTranslationY((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, recipeButtonTranslation, r.getDisplayMetrics()));

        /** Readjusting Layout */
        wzsTopLayout.setMinimumHeight((wzsTopLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
        wzsBottomLayout.setMinimumHeight((wzsBottomLayout.getHeight())+((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics())));
    }

    /**** Uploading Recipe ********************************************************/
    public void uploadRecipe(View view){

        Integer stepNum=2;


        /** Getting reference to initial edit texts */
        EditText firstIngredientName = (EditText) findViewById(R.id.upload_recipe_ingredients_name);
        EditText firstIngredientQty = (EditText) findViewById(R.id.upload_recipe_ingredients_qty);
        EditText firstRecipeName = (EditText) findViewById(R.id.upload_recipe_recipe_name);
        EditText firstRecipeTitle = (EditText) findViewById(R.id.upload_recipe_recipe_title);

        String ingredientName = firstIngredientName.getText().toString();
        String ingredientQty = firstIngredientQty.getText().toString();
        String recipeName = firstRecipeName.getText().toString();
        String recipeTitle = firstRecipeTitle.getText().toString();

        /** Uploading initial edit texts to FireBase */
        Firebase ingredientNameRef = myFirebaseRef.child("Recipe List").child(recipeTitle).child("Ingredients").child(ingredientName);
        Firebase ingredientQtyRef = ingredientNameRef.child("Qty");
        Firebase recipeRef = myFirebaseRef.child("Recipe List").child(recipeTitle).child("Steps").child("1");

        ingredientQtyRef.setValue(ingredientQty);
        recipeRef.setValue(recipeName);

        /** Uploading subsequent ingredient edit texts to Fire base */
        for(int i=0,j=100 ; i<ingredientNameID && j<ingredientQtyID ; i++,j++){

            int nameID = this.getResources().getIdentifier(""+i, "id", this.getPackageName());
            int qtyID = this.getResources().getIdentifier(""+j, "id", this.getPackageName());
            EditText subsequentIngredientName = (EditText) findViewById(nameID);
            EditText subsequentIngredientQty = (EditText) findViewById(qtyID);

            ingredientName = subsequentIngredientName.getText().toString();
            ingredientQty = subsequentIngredientQty.getText().toString();

            ingredientNameRef = myFirebaseRef.child("Recipe List").child(recipeTitle).child("Ingredients").child(ingredientName);
            ingredientQtyRef = ingredientNameRef.child("Qty");
            ingredientQtyRef.setValue(ingredientQty, new Firebase.CompletionListener(){
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    Toast.makeText(userUpload.this, "worked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        /** Uploading subsequent recipe edit texts to Fire base */
        for(int i=200; i<recipeID ; i++){

            int id = this.getResources().getIdentifier(""+i, "id", this.getPackageName());
            EditText subsequentRecipe = (EditText) findViewById(id);
            String stepNumToDB = stepNum.toString();

            recipeName = subsequentRecipe.getText().toString();
            recipeRef = myFirebaseRef.child("Recipe List").child(recipeTitle).child("Steps").child(stepNumToDB);
            recipeRef.setValue(recipeName);
            stepNum++;
        }
        stepNum=2; //housekeeping in case

        /** Uploading Image to Fire Base */
        try {
            StorageReference uploadPath = myStorageRef.child("user").child(recipeTitle);
            uploadPath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(userUpload.this, "Upload Success", Toast.LENGTH_SHORT).show();

                    /** Sending back to main screen */
                    finish();
                    Intent i = new Intent(userUpload.this, LoggedInPage.class);
                    startActivity(i);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(userUpload.this, "Upload In Progress", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(userUpload.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            Toast.makeText(this, "No Image has been uploaded", Toast.LENGTH_LONG).show();
        }


    }

    /**** TO-DO: CREATE A BUTTON TO DELETE INGREDIENTS AND RECIPES ***************/
}