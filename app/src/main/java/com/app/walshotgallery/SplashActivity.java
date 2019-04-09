package com.app.walshotgallery;

import Modals.IntroPrefManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 500;
    IntroPrefManager prefManager;

    /* renamed from: com.walshotbeta.walshotvbeta.SplashActivity$1 */
    class C14621 implements Runnable {
        C14621() {
        }

        public void run() {
            if (SplashActivity.this.prefManager.isFirstTimeLaunch()) {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                SplashActivity.this.finish();
                return;
            }
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(512, 512);
        getWindow().setFlags(1024, 1024);
        setContentView(C1420R.layout.activity_splash);
        this.prefManager = new IntroPrefManager(this);
        new Handler().postDelayed(new C14621(), (long) SPLASH_TIME_OUT);
    }
}
