package com.example.weizheng.forkedmain.homescreens;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weizheng.forkedmain.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

public class MainActivity extends AppCompatActivity {

    private EditText log_in_email;
    private EditText log_in_password;
    private CheckBox log_in_checkbox;
    private FirebaseAuth myFirebaseAuth;
    private Firebase myFirebaseRef;
    private static final String TAG = "Results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_activity_main);
        Firebase.setAndroidContext(this);
        //FirebaseApp.initializeApp(this); //only required for gradle 11.0.1
        myFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseRef = new Firebase("https://forked-up.firebaseio.com/"); //reference to root directory
        log_in_email = (EditText) findViewById(R.id.log_in_email_edittext);
        log_in_password = (EditText) findViewById(R.id.log_in_password_edittext);
        log_in_checkbox = (CheckBox) findViewById(R.id.log_in_checkbox);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = myFirebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signIn(String email, String password){

        myFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Signed in", Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser = myFirebaseAuth.getCurrentUser();
                    updateUI(currentUser);
                } else {
                    task.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i(TAG, e.getMessage());
                        }
                    });
                    Toast.makeText(MainActivity.this, "Authentication error", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }

        });
    }

    private void updateUI(FirebaseUser currentUser){

        if(currentUser != null){
            finish();
            Intent i = new Intent(this, LoggedInPage.class);
            startActivity(i);
        }
    }

    public void onLoginClick(View view){

        String email = log_in_email.getText().toString();
        String password = log_in_password.getText().toString();
        try {
            signIn(email, password);
        } catch (Exception e) {
            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRegisterClick(View view){

        Intent i = new Intent(this, Register.class);
        startActivity(i);

    }

    public void loginDisplayPassword(View view){

        if(log_in_checkbox.isChecked()){
            log_in_password.setInputType(TYPE_CLASS_TEXT);
        } else {
            log_in_password.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        }

    }
}
