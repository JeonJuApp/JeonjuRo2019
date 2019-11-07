package org.androidtown.jeonjuro2018;

import android.os.StrictMode;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class DBRest {

    boolean storeName = false, storeAddr = false, storeMenu = false, storeImg = false, storeOpen = false, b_holiday = false, b_opentime = false, inPosx = false, inPosy = false;
    String storeNm = null, storeAd = null, Menu = null, ImgURL = null, storeop = null, holiday = null, opentime = null, posx = null, posy = null;

    ArrayList<FoodInfo> foodInfoArrayList;
    ArrayList<TourInfo> foodDataList;

    public void load()
    {
        foodInfoArrayList = new ArrayList<>();
        foodDataList = new ArrayList<>();
        StrictMode.enableDefaults();

        try {
            String rl = "http://openapi.jeonju.go.kr/rest/jeonjufood/getWhiteRiceList?authApiKey=";
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D";
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
                        if (parser.getName().equals("posx"))
                            inPosx = true;
                        if (parser.getName().equals("posy"))
                            inPosy = true;
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
                            foodDataList.add(new TourInfo(storeNm,ImgURL,posx,posy));
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
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D";
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
                        if (parser.getName().equals("posx"))
                            inPosx = true;
                        if (parser.getName().equals("posy"))
                            inPosy = true;
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
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                            foodDataList.add(new TourInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("posx"))
                            inPosx = true;
                        if (parser.getName().equals("posy"))
                            inPosy = true;
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
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                            foodDataList.add(new TourInfo(storeNm,ImgURL,posx,posy));
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
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D";
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
                        if (parser.getName().equals("posx"))
                            inPosx = true;
                        if (parser.getName().equals("posy"))
                            inPosy = true;
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
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                            foodDataList.add(new TourInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("posx"))
                            inPosx = true;
                        if (parser.getName().equals("posy"))
                            inPosy = true;
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
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                            foodDataList.add(new TourInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("posx"))
                            inPosx = true;
                        if (parser.getName().equals("posy"))
                            inPosy = true;
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
                            foodInfoArrayList.add(new FoodInfo(ImgURL, storeNm, storeAd, Menu,storeop,holiday,opentime));
                            foodDataList.add(new TourInfo(storeNm,ImgURL,posx,posy));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
        }

        //DB에 데이터 넣기
        for(int i = 0; i< foodInfoArrayList.size(); i++)
        {
            LoginActivity.dbHelper.insert("Food", null, foodInfoArrayList.get(i).getStoreAddr(), null, foodDataList.get(i).getPosx(), foodDataList.get(i).getPosy(), null, foodInfoArrayList.get(i).getStoreName(), foodInfoArrayList.get(i).getMenu(), foodInfoArrayList.get(i).getopenTime(), foodInfoArrayList.get(i).getholiday(), foodInfoArrayList.get(i).getStoreImg());
            Log.i("음식정보", LoginActivity.dbHelper.getResult());
        }
    }
}
