
package org.androidtown.jeonjuro2018;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;

public class SplashActivity extends Activity {

    static DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbHelper = new DBHelper(getApplicationContext(), "INFO.db", null, 1);

        DBTour one = new DBTour();
        one.load();

        DBAccomo two = new DBAccomo();
        two.load();

        DBRest three = new DBRest();
        three.load();

        startLoading();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 10000);
    }
}