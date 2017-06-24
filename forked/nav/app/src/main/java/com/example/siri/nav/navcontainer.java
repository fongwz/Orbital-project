package com.example.siri.nav;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class navcontainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navcontainer);

        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new onUpload()).commit();
        //transaction.replace(((ViewGroup)(getView().getParent())).getId(), fragment);
    }
}
