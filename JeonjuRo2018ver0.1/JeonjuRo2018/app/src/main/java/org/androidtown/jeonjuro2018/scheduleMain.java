package org.androidtown.jeonjuro2018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class scheduleMain extends AppCompatActivity {
    private Gson gson;
    RecyclerView scheduleRecyclerView;
    RecyclerView.LayoutManager scheduleLayoutManager;
    ArrayList<scheduleInfo> scheduleInfoArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_main);
        setCustomActionbar();

        scheduleRecyclerView = findViewById(R.id.recycler_view);
        scheduleRecyclerView.setHasFixedSize(true);
        scheduleLayoutManager = new LinearLayoutManager(this);
        scheduleRecyclerView.setLayoutManager(scheduleLayoutManager);
        onSearchData();
    }


    protected void onSearchData() {
        scheduleInfoArrayList = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String strRoute = sp.getString("routeItem", "");
        Gson gson = new Gson();
        RouteItem routeItem = gson.fromJson(strRoute, RouteItem.class);

        if(!strRoute.equals("")) {
            String title = routeItem.getTitle();
            scheduleInfoArrayList.add(new scheduleInfo(title));
            scheduleAdapter scheduleAdapter = new scheduleAdapter(this, scheduleInfoArrayList);
            scheduleRecyclerView.setAdapter(scheduleAdapter);
        }else{
            scheduleInfoArrayList.add(new scheduleInfo("등록된 일정이 없습니다."));
            scheduleAdapter scheduleAdapter = new scheduleAdapter(this, scheduleInfoArrayList);
            scheduleRecyclerView.setAdapter(scheduleAdapter);

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

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView, params);
    }
}
