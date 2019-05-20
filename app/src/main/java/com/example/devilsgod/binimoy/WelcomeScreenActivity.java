package com.example.devilsgod.binimoy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class WelcomeScreenActivity extends AppCompatActivity {
private ProgressBar progressBar;
private int progressrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);
  progressBar=findViewById(R.id.progressbarId);
  Thread thread=new Thread(new Runnable() {
      @Override
      public void run() {
          startWorking();
          Intent intent=new Intent(WelcomeScreenActivity.this,LoginActivity.class);
          startActivity(intent);
          finish();
      }
  });
     thread.start();


    }

    private void startWorking() {
        for (progressrate=20; progressrate<=100;progressrate=progressrate+20){
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progressrate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
