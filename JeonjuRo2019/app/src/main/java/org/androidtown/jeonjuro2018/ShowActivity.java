package org.androidtown.jeonjuro2018;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {
    ArrayList<scheduleInfo> scheduleInfoArrayList;
    TextView Time;
    private ListView m_oListView;
    ArrayList<ItemData> oData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        setCustomActionbar();
        Time = (TextView) findViewById(R.id.txt2);

        scheduleInfoArrayList = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        String strRoute = sp.getString("routeItem", "");
        Log.d("Tag", strRoute + "");
        Gson gson = new Gson();
        RouteItem routeItem = gson.fromJson(strRoute, RouteItem.class);

        ArrayList<ItemData> itemData = routeItem.getRouteList();
        String totalTime = routeItem.getTotalTime();
        oData = new ArrayList<>();

        Time.setText(totalTime );
        for (int i = 0; i < 4; ++i) {
            ItemData oItem = new ItemData();
            oItem.setListTitle(itemData.get(i).listTitle);
            //Log.d("Tag",itemData.get(i).listTitle);
            
            oItem.setListTitle2(itemData.get(i).listTitle2);
            oItem.setListBus(itemData.get(i).listBus);
            oItem.setListBusStopNum(itemData.get(i).listBusStopNum);
            oItem.setListBus2(itemData.get(i).listBus2);
            oItem.setListBusTime(itemData.get(i).listBusTime);
            oData.add(oItem);
        }
        m_oListView = (ListView) findViewById(R.id.listView);
        ListAdapter oAdapter = new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);
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
