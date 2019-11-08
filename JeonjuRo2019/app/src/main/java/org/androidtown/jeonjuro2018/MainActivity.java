package org.androidtown.jeonjuro2018;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int FRAGMENT1 = 1;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    RadioButton homeTopbar, schedule_topbar, custom_topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCustomActionbar();

        //Intent intent = new Intent(this, SplashActivity.class);
        //startActivity(intent);
        schedule_topbar = (RadioButton) findViewById(R.id.schedule_topbar);
        custom_topbar = (RadioButton) findViewById(R.id.custom_topbar);
        homeTopbar = (RadioButton) findViewById(R.id.home_topbar);
        homeTopbar.toggle();
        homeTopbar.setOnClickListener(this);
        schedule_topbar.setOnClickListener(this);
        custom_topbar.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle==null) {
            callFragment(FRAGMENT1);

        }else if(!bundle.isEmpty()){
            callFragment(FRAGMENT3);
        }

    }

    private void setCustomActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        //set custom view layout
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_main, null);
        actionBar.setCustomView(mCustomView);

        //set no padding both side
        Toolbar parent = (Toolbar) mCustomView.getParent();
        parent.setContentInsetsAbsolute(0, 0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT);

        actionBar.setCustomView(mCustomView, params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_topbar:
                callFragment(FRAGMENT1);
                break;
            case R.id.custom_topbar:
                callFragment(FRAGMENT2);
                break;
            case R.id.schedule_topbar:
                callFragment(FRAGMENT3);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            homeTopbar.toggle();

        }
    }

    private void callFragment(int frament_no) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no) {
            case 1:
                HomeActivity fragment1 = new HomeActivity();
                transaction.replace(R.id.fragment_container, fragment1);
                transaction.commit();
                break;
            case 2:
                CustomActivity fragment2 = new CustomActivity();
                transaction.replace(R.id.fragment_container, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 3:
                scheduleMain fragment3 = new scheduleMain();
                transaction.replace(R.id.fragment_container, fragment3);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

        }
    }
}