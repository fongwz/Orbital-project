package com.example.weizheng.forkedmain.profilesettings;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weizheng.forkedmain.R;
import com.example.weizheng.forkedmain.results.resultAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileMyUploads extends AppCompatActivity {

    private ListView uploadList;
    private FirebaseDatabase myFirebaseDatabase;
    private DatabaseReference myFirebaseRef;
    private FirebaseAuth myFirebaseAuth;
    private static final String TAG = "Results";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.profilesettings_profile_my_uploads);

        uploadList = (ListView) findViewById(R.id.profile_uploads_listview);
        myFirebaseDatabase = FirebaseDatabase.getInstance();
        myFirebaseRef = myFirebaseDatabase.getReference();
        myFirebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference uploadsRef = myFirebaseRef.child("Users").child(myFirebaseAuth.getCurrentUser().getUid()).child("Uploads");
        uploadsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> uploadArrayList = new ArrayList<String>(0);
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    uploadArrayList.add(postSnapshot.getKey());
                }

                if(uploadArrayList.size()<1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMyUploads.this);
                    builder.setMessage("You haven't uploaded any recipes!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    onBackPressed();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                } else {

                    ListAdapter adapter = new resultAdapter(getApplicationContext(), uploadArrayList.toArray(new String[uploadArrayList.size()]));
                    uploadList.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, databaseError.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }
}
