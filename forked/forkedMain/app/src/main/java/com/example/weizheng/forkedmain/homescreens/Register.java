package com.example.weizheng.forkedmain.homescreens;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

import com.example.weizheng.forkedmain.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    private EditText register_email;
    private EditText register_displayname;
    private EditText register_password;
    private CheckBox register_checkbox;
    private DatabaseReference myFirebaseRef;
    private FirebaseDatabase myFirebaseDatabase;
    private FirebaseAuth myFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_activity_register);
        Firebase.setAndroidContext(this);

        myFirebaseAuth = FirebaseAuth.getInstance();
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myFirebaseRef = myFirebaseDatabase.getReference(); //reference to root directory
        register_email = (EditText) findViewById(R.id.register_email_edittext);
        register_displayname = (EditText) findViewById(R.id.register_displayname_edittext);
        register_password = (EditText) findViewById(R.id.register_password_edittext);
        register_checkbox = (CheckBox) findViewById(R.id.register_checkbox);
    }

    public void registerDisplayPassword(View view){

        if(register_checkbox.isChecked()){
            register_password.setInputType(TYPE_CLASS_TEXT);
        } else {
            register_password.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
        }

    }

    public void register(View view){

        String email = register_email.getText().toString();
        String displayname = register_displayname.getText().toString();
        String password = register_password.getText().toString();
        createAccount(email, displayname, password);

    }

    private void createAccount(String email,final String displayname, String password){

        myFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //update user object inside the database
                    FirebaseUser currentUser = myFirebaseAuth.getCurrentUser();
                    Toast.makeText(getApplicationContext(), currentUser.getUid(), Toast.LENGTH_SHORT).show();
                    DatabaseReference userDetailsAdmin = myFirebaseRef.child("Users");
                    userDetailsAdmin.child(currentUser.getUid()).child("Display").setValue(displayname);
                    userDetailsAdmin.child(currentUser.getUid()).child("isAdmin").setValue(false);
                    //move to sign in page with only email filled in
                    Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();
                    myFirebaseAuth.signOut();

                    Intent i = new Intent(Register.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else {
                    Toast.makeText(Register.this, "Authentication error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
