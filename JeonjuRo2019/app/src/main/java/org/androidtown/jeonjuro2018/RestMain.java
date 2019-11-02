package org.androidtown.jeonjuro2018;

import android.content.Intent;

import android.graphics.Bitmap;

import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.net.URL;
import java.util.ArrayList;

public class RestMain extends AppCompatActivity {
    RecyclerView restRecyclerView;
    RecyclerView.LayoutManager restLayoutManager;


    boolean storeName = false, storeAddr = false, storeMenu = false, storeImg = false, storeOpen = false, b_holiday = false, b_opentime = false;
    String storeNm = null, storeAd = null, Menu = null, ImgURL = null, storeop = null, holiday = null, opentime = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_main);
        setCustomActionbar();


        StrictMode.enableDefaults();

        restRecyclerView  = findViewById(R.id.recycler_view);
        restRecyclerView.setHasFixedSize(true);
        restLayoutManager = new LinearLayoutManager(this);
        restRecyclerView.setLayoutManager(restLayoutManager);

        ArrayList<TourInfo> tourInfoArrayList = new ArrayList<>();

        ArrayList<FoodInfo> foodInfoArrayList = new ArrayList<>();
        FoodInfo food = null;


        try {
            String rl = "http://openapi.jeonju.go.kr/rest/jeonjufood/getWhiteRiceList?authApiKey=";
            String key = "l%2Fbl3sZQ3YhS3%2BFhJ2byNgr0196DxOsYpBwiuxXai9lXFDCQk0uLB6cCO3K8sNazZBbLeDQigvUWgmkZn3i86A%3D%3D";
            URL url = new URL(rl+key);//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("storeNm")) {
                            storeName = true;
                        }
                        if (parser.getName().equals("mainMenu")) {
                            storeMenu = true;
                        }
                        if (parser.getName().equals("newAddr")) {
                            storeAddr = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            storeOpen = true;
                        }
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("holiday")) {
                            b_holiday = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            b_opentime = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeMenu) { //isTitle이 true일 때 태그의 내용을 저장.
                            Menu = parser.getText();
                            storeMenu = false;
                        }
                        if (storeOpen) {
                            storeop = parser.getText();
                        }
                        if (storeAddr) { //isTitle이 true일 때 태그의 내용을 저장.
                            if(parser.getText().charAt(0) == '0' || parser.getText().charAt(0) == '1' || parser.getText().charAt(0) == '2')
                                storeAd = "전라북도 전주시";
                            else
                                storeAd = parser.getText();
                            storeAddr = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_holiday) {
                            holiday = parser.getText();
                            b_holiday = false;
                        }
                        if (b_opentime) {
                            opentime = parser.getText();
                            b_opentime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            ;
        }

        try {
            String rl = "http://openapi.jeonju.go.kr/rest/jeonjufood/getMimbapList?authApiKey=";
            String key = "l%2Fbl3sZQ3YhS3%2BFhJ2byNgr0196DxOsYpBwiuxXai9lXFDCQk0uLB6cCO3K8sNazZBbLeDQigvUWgmkZn3i86A%3D%3D";
            String data = "&keyword=%EC%84%B1%EB%AF%B8%EB%8B%B9";
            URL url = new URL(rl+key);//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("storeNm")) {
                            storeName = true;
                        }
                        if (parser.getName().equals("mainMenu")) {
                            storeMenu = true;
                        }
                        if (parser.getName().equals("newAddr")) {
                            storeAddr = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            storeOpen = true;
                        }
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("holiday")) {
                            b_holiday = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            b_opentime = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeMenu) { //isTitle이 true일 때 태그의 내용을 저장.
                            Menu = parser.getText();
                            storeMenu = false;
                        }
                        if (storeOpen) {
                            storeop = parser.getText();
                        }
                        if (storeAddr) { //isTitle이 true일 때 태그의 내용을 저장.
                            if(parser.getText().charAt(0) == '0' || parser.getText().charAt(0) == '1' || parser.getText().charAt(0) == '2')
                                storeAd = "전라북도 전주시";
                            else
                                storeAd = parser.getText();
                            storeAddr = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_holiday) {
                            holiday = parser.getText();
                            b_holiday = false;
                        }
                        if (b_opentime) {
                            opentime = parser.getText();
                            b_opentime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            ;
        }

        try {
            URL url = new URL("http://openapi.jeonju.go.kr/rest/jeonjufood/getGongBapList?authApiKey=DIFVRMOVXUMNJHY");//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("storeNm")) {
                            storeName = true;
                        }
                        if (parser.getName().equals("mainMenu")) {
                            storeMenu = true;
                        }
                        if (parser.getName().equals("newAddr")) {
                            storeAddr = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            storeOpen = true;
                        }
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("holiday")) {
                            b_holiday = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            b_opentime = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeMenu) { //isTitle이 true일 때 태그의 내용을 저장.
                            Menu = parser.getText();
                            storeMenu = false;
                        }
                        if (storeOpen) {
                            storeop = parser.getText();
                        }
                        if (storeAddr) { //isTitle이 true일 때 태그의 내용을 저장.
                            if(parser.getText().charAt(0) == '0' || parser.getText().charAt(0) == '1' || parser.getText().charAt(0) == '2')
                                storeAd = "전라북도 전주시";
                            else
                                storeAd = parser.getText();
                            storeAddr = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_holiday) {
                            holiday = parser.getText();
                            b_holiday = false;
                        }
                        if (b_opentime) {
                            opentime = parser.getText();
                            b_opentime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            ;
        }

        try {
            String rl = "http://openapi.jeonju.go.kr/rest/jeonjufood/getMimbapList?authApiKey=";
            String key = "l%2Fbl3sZQ3YhS3%2BFhJ2byNgr0196DxOsYpBwiuxXai9lXFDCQk0uLB6cCO3K8sNazZBbLeDQigvUWgmkZn3i86A%3D%3D";
            String data = "&keyword=%EC%A0%84%EC%A3%BC%ED%95%9C%EC%98%A5%EC%B6%94%EC%96%B4%ED%83%95";
            URL url = new URL(rl+key);//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("storeNm")) {
                            storeName = true;
                        }
                        if (parser.getName().equals("mainMenu")) {
                            storeMenu = true;
                        }
                        if (parser.getName().equals("newAddr")) {
                            storeAddr = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            storeOpen = true;
                        }
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("holiday")) {
                            b_holiday = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            b_opentime = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeMenu) { //isTitle이 true일 때 태그의 내용을 저장.
                            Menu = parser.getText();
                            storeMenu = false;
                        }
                        if (storeOpen) {
                            storeop = parser.getText();
                        }
                        if (storeAddr) { //isTitle이 true일 때 태그의 내용을 저장.
                            if(parser.getText().charAt(0) == '0' || parser.getText().charAt(0) == '1' || parser.getText().charAt(0) == '2')
                                storeAd = "전라북도 전주시";
                            else
                                storeAd = parser.getText();
                            storeAddr = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_holiday) {
                            holiday = parser.getText();
                            b_holiday = false;
                        }
                        if (b_opentime) {
                            opentime = parser.getText();
                            b_opentime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            ;
        }

        try {
            URL url = new URL("http://openapi.jeonju.go.kr/rest/jeonjufood/getKoreaWineList?authApiKey=l%2Fbl3sZQ3YhS3%2BFhJ2byNgr0196DxOsYpBwiuxXai9lXFDCQk0uLB6cCO3K8sNazZBbLeDQigvUWgmkZn3i86A%3D%3D");//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("storeNm")) {
                            storeName = true;
                        }
                        if (parser.getName().equals("mainMenu")) {
                            storeMenu = true;
                        }
                        if (parser.getName().equals("newAddr")) {
                            storeAddr = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            storeOpen = true;
                        }
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("holiday")) {
                            b_holiday = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            b_opentime = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeMenu) { //isTitle이 true일 때 태그의 내용을 저장.
                            Menu = parser.getText();
                            storeMenu = false;
                        }
                        if (storeOpen) {
                            storeop = parser.getText();
                        }
                        if (storeAddr) { //isTitle이 true일 때 태그의 내용을 저장.
                            if(parser.getText().charAt(0) == '0' || parser.getText().charAt(0) == '1' || parser.getText().charAt(0) == '2')
                                storeAd = "전라북도 전주시";
                            else
                                storeAd = parser.getText();
                            storeAddr = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_holiday) {
                            holiday = parser.getText();
                            b_holiday = false;
                        }
                        if (b_opentime) {
                            opentime = parser.getText();
                            b_opentime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {

        }

        try {
            URL url = new URL("http://openapi.jeonju.go.kr/rest/jeonjufood/getHanOkFoodList?authApiKey=l%2Fbl3sZQ3YhS3%2BFhJ2byNgr0196DxOsYpBwiuxXai9lXFDCQk0uLB6cCO3K8sNazZBbLeDQigvUWgmkZn3i86A%3D%3D");//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("storeNm")) {
                            storeName = true;
                        }
                        if (parser.getName().equals("mainMenu")) {
                            storeMenu = true;
                        }
                        if (parser.getName().equals("newAddr")) {
                            storeAddr = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            storeOpen = true;
                        }
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("holiday")) {
                            b_holiday = true;
                        }
                        if (parser.getName().equals("openTime")) {
                            b_opentime = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeMenu) { //isTitle이 true일 때 태그의 내용을 저장.
                            Menu = parser.getText();
                            storeMenu = false;
                        }
                        if (storeOpen) {
                            storeop = parser.getText();
                        }
                        if (storeAddr) { //isTitle이 true일 때 태그의 내용을 저장.
                            if(parser.getText().charAt(0) == '0' || parser.getText().charAt(0) == '1' || parser.getText().charAt(0) == '2')
                                storeAd = "전라북도 전주시";
                            else
                                storeAd = parser.getText();
                            storeAddr = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_holiday) {
                            holiday = parser.getText();
                            b_holiday = false;
                        }
                        if (b_opentime) {
                            opentime = parser.getText();
                            b_opentime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
        }


        View.OnClickListener mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RestMain.this, LocationDialog.class);
                startActivity(intent);
            }
        };
        FoodAdapter myAdapter = new FoodAdapter(this,foodInfoArrayList,mListener);
        restRecyclerView.setAdapter(myAdapter);

    }

    private void setCustomActionbar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        //set custom view layout
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.actionbar_main,null);
        actionBar.setCustomView(mCustomView);

        //set no padding both side
        Toolbar parent = (Toolbar)mCustomView.getParent();
        parent.setContentInsetsAbsolute(0,0);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(mCustomView,params);
    }

}


