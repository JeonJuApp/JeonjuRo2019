package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class Result3 extends AppCompatActivity {
    ArrayList<Place> data = new ArrayList<>();

    ArrayList<FoodInfo> foodDataList;

    ArrayList<TourInfo> accomoDataList;

    ArrayList<TourInfo> tourDataList;


    GridView gridView;
    GridViewAdapter adapter;
    String type = null;

    int randnum;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        foodDataList = getIntent().getParcelableArrayListExtra("food");
        accomoDataList = getIntent().getParcelableArrayListExtra("acco");
        tourDataList = getIntent().getParcelableArrayListExtra("tour");

        //type = getIntent().getStringExtra("type");
        randnum = getIntent().getExtras().getInt("randnum");

        gridView = (GridView)findViewById(R.id.grid);
        adapter = new GridViewAdapter(data,this);

        gridView.setAdapter(adapter);

        int save = 26;
        randnum += 2;
        for(int k = 1; k <= 2; k++) { // 식당 2개 추가
            int num = randnum * k % 25;
            if (num == save)
                data.add(new Place(1,foodDataList.get(num + 1).getStoreName(),foodDataList.get(num + 1).getStoreImg(),foodDataList.get(num + 1).getPosx(),foodDataList.get(num + 1).getPosy()));
            else
                data.add(new Place(1,foodDataList.get(num).getStoreName(),foodDataList.get(num).getStoreImg(),foodDataList.get(num).getPosx(),foodDataList.get(num).getPosy()));
            save = num;
        }

        data.add(new Place(2,accomoDataList.get(randnum % 10).getTourName(),accomoDataList.get(randnum % 10).getUrl(),accomoDataList.get(randnum % 10).getPosx(),accomoDataList.get(randnum % 10).getPosy())); // 숙소 1개 추가

        int save2 = 11;
        for(int k = 1; k <= 2; k++) { // 식당 2개 추가
            int num = randnum * k % 10;
            if (num == save2)
                data.add(new Place(3,tourDataList.get(num+1).getTourName(),tourDataList.get(num+1).getUrl(),tourDataList.get(num+1).getPosx(),tourDataList.get(num+1).getPosy()));
            else
                data.add(new Place(3,tourDataList.get(num).getTourName(),tourDataList.get(num).getUrl(),tourDataList.get(num).getPosx(),tourDataList.get(num).getPosy()));
            save2 = num;
        }


        setCustomActionbar();
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(Result3.this,Result.class);
                myintent.putExtra("randnum", randnum);
                myintent.putParcelableArrayListExtra("food",foodDataList);
                myintent.putParcelableArrayListExtra("tour",tourDataList);
                myintent.putParcelableArrayListExtra("acco",accomoDataList);
                startActivity(myintent);
                finish();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(Result3.this,ScheduleActivity.class);
                myintent.putParcelableArrayListExtra("data",data);
                startActivity(myintent);
                finish();
            }
        });

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
}
