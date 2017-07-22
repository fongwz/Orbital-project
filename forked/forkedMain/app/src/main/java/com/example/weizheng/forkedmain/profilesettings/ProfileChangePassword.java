package com.example.weizheng.forkedmain.profilesettings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weizheng.forkedmain.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileChangePassword extends AppCompatActivity {

    private EditText currentPW;
    private EditText newPW1;
    private EditText newPW2;
    private FirebaseAuth myFirebaseAuth;
    private static final String TAG = "Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilesettings_profile_change_password);

        myFirebaseAuth = FirebaseAuth.getInstance();
        currentPW = (EditText) findViewById(R.id.change_password_current_edittext);
        newPW1 = (EditText) findViewById(R.id.change_password_new1_edittext);
        newPW2 = (EditText) findViewById(R.id.change_password_new2_edittext);
    }

    public void changePW(View view){

        try {

            String curr = currentPW.getText().toString();
            FirebaseUser user = myFirebaseAuth.getCurrentUser();
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), curr);
            user.reauthenticate(credential)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.i(TAG, "current pw is correct, reAuthenticated and changing pw");
                            try {

                                String pw1 = newPW1.getText().toString();
                                String pw2 = newPW2.getText().toString();

                                if (!pw1.equals(pw2)) {

                                    Log.i(TAG, "PW don't match!");
                                    Toast.makeText(ProfileChangePassword.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();

                                } else if (pw1.length() < 6 || pw2.length() < 6) {

                                    Log.i(TAG, "PW too short");
                                    Toast.makeText(ProfileChangePassword.this, "Password is too short!", Toast.LENGTH_SHORT).show();

                                } else {
                                    myFirebaseAuth.getCurrentUser().updatePassword(pw2);
                                    Log.i(TAG, "pw changed");
                                    Toast.makeText(ProfileChangePassword.this, "Password updated", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(ProfileChangePassword.this, ProfilePage.class);
                                    finish();
                                    startActivity(i);
                                }

                            } catch (Exception e) {
                                Log.i(TAG, e.getMessage());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i(TAG, e.getMessage());
                            Toast.makeText(ProfileChangePassword.this, "Current password is wrong.", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }
}
