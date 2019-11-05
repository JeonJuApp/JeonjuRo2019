package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class DBTour {

    boolean inAddr = false, infileImg = false, indataTtitle = false, infileUrl = false, indataSid = false, inPosx = false, inPosy = false;
    String addr = null, dataTitle = null, fileUrl = null, dataSid = null, posx = null, posy = null;

    Context mContext;
    boolean inHomepage = false;
    String homepage = null;
    boolean indataContent = false;
    String dataContent =  null;
    int i = 1;

    ArrayList<TourInfo> tourInfoArrayList;
    ArrayList<TourInfo> tourDataList;

    public void load()
    {
        tourInfoArrayList = new ArrayList<>();
        tourDataList = new ArrayList<>();



        try {
            StrictMode.enableDefaults();

            String rl = "http://openapi.jeonju.go.kr/rest/historic/getHistoricList?authApiKey=";
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D";
            URL url = new URL(rl + key);

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                Log.i("정보","반복문");
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
                                Log.i("정보", "1번");
                                tourInfoArrayList.add(new TourInfo(fileUrl, dataTitle, addr, dataContent, homepage));
                                tourDataList.add(new TourInfo(dataTitle,fileUrl,posx,posy));
                                //dbHelper.insert("Tour", dataTitle, addr, null, dataContent, posx, posy, homepage, null, null, null, null, null, fileUrl);
                            } else {
                                Log.i("정보", "2번");
                                tourInfoArrayList.add(new TourInfo("http://encykorea.aks.ac.kr/Contents/GetImage?id=b96c2aec-02ff-4cf4-b0ad-e560d5dae826&w=260&h=260&fit=w&clip=1", dataTitle, addr, dataContent, homepage));
                                tourDataList.add(new TourInfo(dataTitle,"http://encykorea.aks.ac.kr/Contents/GetImage?id=b96c2aec-02ff-4cf4-b0ad-e560d5dae826&w=260&h=260&fit=w&clip=1",posx,posy));
                                //dbHelper.insert("Tour", dataTitle, addr, null, dataContent, posx, posy, homepage, null, null, null, null, null, "http://encykorea.aks.ac.kr/Contents/GetImage?id=b96c2aec-02ff-4cf4-b0ad-e560d5dae826&w=260&h=260&fit=w&clip=1");
                                i = 0;
                            }
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            Log.i("정보","캐치");
        }

        //DB에 데이터 넣기
        for(int i = 0; i< tourInfoArrayList.size(); i++)
        {
            SplashActivity.dbHelper.insert("Tour", tourInfoArrayList.get(i).getTourName(), tourInfoArrayList.get(i).getTourLocation(), tourInfoArrayList.get(i).getDataContent(), tourDataList.get(i).getPosx(), tourDataList.get(i).getPosy(), tourInfoArrayList.get(i).getHomepage(), null, null, null, null, tourInfoArrayList.get(i).getUrl());
            Log.i("투어정보", SplashActivity.dbHelper.getResult());
        }
    }

    private void comeonImage(String dataSid) {
        try {
            StrictMode.enableDefaults();

            String rl = "http://openapi.jeonju.go.kr/rest/historic/getHistoricFile?authApiKey=";
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
}
