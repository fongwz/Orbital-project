package com.example.weizheng.forkedmain;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText log_in_email;
    private EditText log_in_password;
    private FirebaseAuth myFirebaseAuth;
    private Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);

        myFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseRef = new Firebase("https://forked-up.firebaseio.com/"); //reference to root directory
        log_in_email = (EditText) findViewById(R.id.log_in_email_edittext);
        log_in_password = (EditText) findViewById(R.id.log_in_password_edittext);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = myFirebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password){

        myFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //update user object inside the database
                    FirebaseUser currentUser = myFirebaseAuth.getCurrentUser();
                    Firebase userDetailsRef = myFirebaseRef.child("Users").child(currentUser.getUid()).child("isAdmin");
                    userDetailsRef.setValue("false");
                    //move to sign in page with only email filled in
                    Toast.makeText(MainActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                    updateUI(currentUser);
                } else {
                    Toast.makeText(MainActivity.this, "Authentication error", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });

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

        String email = log_in_email.getText().toString();
        String password = log_in_password.getText().toString();
        try {
            createAccount(email, password);
        } catch (Exception e) {
            Toast.makeText(this, "Please fill in the blank fields", Toast.LENGTH_SHORT).show();
        }
    }
}
