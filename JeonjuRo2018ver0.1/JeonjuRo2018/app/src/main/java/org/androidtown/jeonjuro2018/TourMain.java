package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channel;
import java.util.ArrayList;

public class TourMain extends AppCompatActivity {
    RecyclerView accomoRecyclerView;
    RecyclerView.LayoutManager accomoLayoutManager;

    boolean inAddr = false, infileImg = false, indataTtitle = false, infileUrl = false, indataSid = false, inPosx = false, inPosy = false;
    String addr = null, dataTitle = null, fileUrl = null, dataSid = null, posx = null, posy = null;

    Context mContext;
    boolean inHomepage = false;
    String homepage = null;
    boolean indataContent = false;
    String dataContent =  null;

    ArrayList<TourInfo> tourInfoArrayList;
    ArrayList<TourInfo> tourDataList;

    TextView textView;
    ImageView imageView;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_main);
        setCustomActionbar();

        imageView = findViewById(R.id.tour_picture);
        accomoRecyclerView = findViewById(R.id.recycler_view);
        accomoRecyclerView.setHasFixedSize(true);
        accomoLayoutManager = new LinearLayoutManager(this);
        accomoRecyclerView.setLayoutManager(accomoLayoutManager);
        tourInfoArrayList = new ArrayList<>();

        tourDataList = new ArrayList<>();



        StrictMode.enableDefaults();
        try {
            String rl = "http://openapi.jeonju.go.kr/rest/historic/getHistoricList?authApiKey=";
            String key = "mU%2F2QPvNKkLKVF3EQhuSvb0x6QgubDrqMD1yvNvBgPMnsJXU%2B8sB%2B9JHMzIYp5ZpDanA0ANHjgoPJmBkQIjTiw%3D%3D";
            URL url = new URL(rl + key);

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);
            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("dataSid")) {
                            indataSid = true;
                        }
                        if (parser.getName().equals("dataTitle")) {
                            indataTtitle = true;
                        }
                        if (parser.getName().equals("addrDtl")) {
                            inAddr = true;
                        }
                        if (parser.getName().equals("introDataContent")) {
                            indataContent = true;
                        }
                        if (parser.getName().equals("dataContent")) {
                            inHomepage = true;
                        }
                        if (parser.getName().equals("posx")) {
                            inPosx = true;
                        }
                        if (parser.getName().equals("posy")) {
                            inPosy = true;
                        }

                        break;
                    case XmlPullParser.TEXT:
                        if (indataSid) {
                            dataSid = parser.getText();
                            indataSid = false;
                        }
                        if (indataTtitle) {
                            dataTitle = parser.getText();
                            indataTtitle = false;
                        }
                        if (inAddr) {
                            addr = parser.getText();
                            inAddr = false;
                        }
                        if (indataContent) {
                            dataContent = parser.getText();
                            indataContent = false;
                        }
                        if (inHomepage) {
                            homepage = parser.getText();
                            inHomepage = false;
                        }
                        if (inPosx) {
                            posx = parser.getText();
                            inPosx = false;
                        }
                        if (inPosy) {
                            posy = parser.getText();
                            inPosy = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            comeonImage(dataSid);
                            if (i != 1) {
                                tourInfoArrayList.add(new TourInfo(fileUrl, dataTitle, addr, dataContent, homepage));
                                tourDataList.add(new TourInfo(dataTitle,fileUrl,posx,posy));
                            } else {
                                tourInfoArrayList.add(new TourInfo("http://encykorea.aks.ac.kr/Contents/GetImage?id=b96c2aec-02ff-4cf4-b0ad-e560d5dae826&w=260&h=260&fit=w&clip=1", dataTitle, addr, dataContent, homepage));
                                tourDataList.add(new TourInfo(dataTitle,"http://encykorea.aks.ac.kr/Contents/GetImage?id=b96c2aec-02ff-4cf4-b0ad-e560d5dae826&w=260&h=260&fit=w&clip=1",posx,posy));
                                i = 0;
                            }
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
        }

        MyAdapter myAdapter = new MyAdapter(this, tourInfoArrayList);
        accomoRecyclerView.setAdapter(myAdapter);

    }

    private void comeonImage(String dataSid) {
        try {
            String rl = "http://openapi.jeonju.go.kr/rest/historic/getHistoricFile?authApiKey=";
            String key = "mU%2F2QPvNKkLKVF3EQhuSvb0x6QgubDrqMD1yvNvBgPMnsJXU%2B8sB%2B9JHMzIYp5ZpDanA0ANHjgoPJmBkQIjTiw%3D%3D&dataSid=";
            String data = dataSid;
            URL url = new URL(rl + key + data);//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("fileUrl")) {
                            infileUrl = true;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        if (infileUrl) {
                            fileUrl = parser.getText();
                            infileUrl = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            infileImg = false;
                        }
                        break;
                }
                if (infileImg == false) {
                    infileImg = true;
                    break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
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