package com.salon.mysalon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.digits.sdk.android.Digits;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "wUTlItKsceFZ3IwETMZMcNyR2";
    private static final String TWITTER_SECRET = "LsYHViCy7Hqy6TSDpV8hzqAYBUisLuyL17fb2aBFFPJq0xt08f";


    /**
     * Time to display the splash screen in milliseconds.
     */
    private static final int SPLASH_DURATION = 2000;
    private static final String TAG = "SplashActivity";
    boolean backPressed = false;


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            final Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

            finish();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits.Builder().build());
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!backPressed) {
                    Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
                    finish();
                    startActivity(myIntent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        }, SPLASH_DURATION);
    }


    @Override
    public void onBackPressed() {
        backPressed = true;
        finish();
    }
}
