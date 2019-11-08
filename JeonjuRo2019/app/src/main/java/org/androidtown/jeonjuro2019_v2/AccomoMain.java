package org.androidtown.jeonjuro2019_v2;

import android.os.StrictMode;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.net.URL;
import java.util.ArrayList;

public class AccomoMain extends AppCompatActivity {
    RecyclerView accomoRecyclerView;
    RecyclerView.LayoutManager accomoLayoutManager;

    boolean inAddr = false, infileImg = false, indataTtitle = false, infileUrl = false, indataSid = false;
    boolean inhonokTypeStr = false, inhomepage = false, inintroContent = false, indataContent = false;
    String addr = null, dataTitle = null, fileUrl = null, dataSid = null, honokTypeStr = null, homepage = null, introContent = null, dataContent = null;
    ArrayList<TourInfo> tourInfoArrayList;
    ImageView imageView;
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomo_main);
        setCustomActionbar();

        imageView = findViewById(R.id.tour_picture);
        accomoRecyclerView = findViewById(R.id.recycler_view);
        accomoRecyclerView.setHasFixedSize(true);
        accomoLayoutManager = new LinearLayoutManager(this);
        accomoRecyclerView.setLayoutManager(accomoLayoutManager);
        tourInfoArrayList = new ArrayList<>();


        StrictMode.enableDefaults();
        try {
            String rl = "http://openapi.jeonju.go.kr/rest/hanokhouse/getHanokHouseList?authApiKey=";
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D";
            URL url = new URL(rl + key);


            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);
            int parserEvent = parser.getEventType();
            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("dataSid"))
                            indataSid = true;
                        if (parser.getName().equals("dataTitle"))
                            indataTtitle = true;
                        if (parser.getName().equals("addr"))
                            inAddr = true;
                        if (parser.getName().equals("honokTypeStr"))
                            inhonokTypeStr = true;
                        if (parser.getName().equals("homepage"))
                            inhomepage = true;
                        if (parser.getName().equals("introContent"))
                            inintroContent = true;
                        if (parser.getName().equals("dataContent"))
                            indataContent = true;
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
                        if (inhonokTypeStr) {
                            honokTypeStr = parser.getText();
                            inhonokTypeStr = false;
                        }
                        if (inhomepage) {
                            homepage = parser.getText();
                            inhomepage = false;
                        }
                        if (inintroContent) {
                            introContent = parser.getText();
                            inintroContent = false;
                        }
                        if (indataContent) {
                            dataContent = parser.getText();
                            indataContent = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            comeonImage(dataSid);
                            if (i != 1) {
                                tourInfoArrayList.add(new TourInfo(fileUrl, dataTitle, addr, introContent, homepage));
                            } else {
                                tourInfoArrayList.add(new TourInfo("http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/2dc57345-3f23-47d7-842c-712ca4807a78.jpg.png", dataTitle, addr, introContent, homepage));
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
            String rl = "http://openapi.jeonju.go.kr/rest/hanokhouse/getHanokHosueFile?authApiKey=";
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D&dataSid=";
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