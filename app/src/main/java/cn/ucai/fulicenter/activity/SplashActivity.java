package cn.ucai.fulicenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.fulicenter.R;

public class SplashActivity extends AppCompatActivity {

    private final long sleepTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {

                long start = System.currentTimeMillis();
                //create db
                long costTime = System.currentTimeMillis() - start;
                if(sleepTime-costTime>0){
                    try {
                        Thread.sleep(sleepTime-costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        }).start();
    }
}
