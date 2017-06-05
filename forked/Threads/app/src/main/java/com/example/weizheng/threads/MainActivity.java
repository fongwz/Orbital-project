package com.example.weizheng.threads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.os.Handler;
import android.os.Message;

//import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            TextView wzsText = (TextView) findViewById(R.id.wzsText);
            wzsText.setText("done");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){

        Runnable r = new Runnable(){
            @Override
            public void run(){
                long futureTime = System.currentTimeMillis() + 10000;
                while(System.currentTimeMillis()<futureTime){
                    synchronized (this){
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch(Exception e){}
                    }
                }
                handler.sendEmptyMessage(0);
            }
        };

        Thread thread = new Thread(r);
        thread.start();
    }
}
