package org.androidtown.jeonjuro2018;

import android.os.StrictMode;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class DBAccomo {

    boolean inAddr = false, infileImg = false, indataTtitle = false, infileUrl = false, indataSid = false,inPosx = false, inPosy = false;
    boolean inhonokTypeStr = false, inhomepage = false, inintroContent = false, indataContent = false;
    String addr = null, dataTitle = null, fileUrl = null, dataSid = null, honokTypeStr = null, homepage = null, introContent = null, dataContent = null, posx = null, posy = null;
    ArrayList<TourInfo> tourInfoArrayList;
    ArrayList<TourInfo> tourDataList;
    int i = 1;

    public void load()
    {
        tourInfoArrayList = new ArrayList<>();
        tourDataList = new ArrayList<>();

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
                        if (parser.getName().equals("posx"))
                            inPosx = true;
                        if (parser.getName().equals("posy"))
                            inPosy = true;
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
                                Log.i("정보","어코모 들어오냐?");
                                tourInfoArrayList.add(new TourInfo(fileUrl, dataTitle, addr, dataContent, homepage));
                                tourDataList.add(new TourInfo(dataTitle,fileUrl,posx,posy));
                            } else {
                                Log.i("정보","어코모 들어오냐?1");
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
            Log.i("정보","캐치다");
        }

        //DB에 데이터 넣기
        for(int i = 0; i< tourInfoArrayList.size(); i++)
        {
            SplashActivity.dbHelper.insert("Accomo", tourInfoArrayList.get(i).getTourName(), tourInfoArrayList.get(i).getTourLocation(), tourInfoArrayList.get(i).getDataContent(), tourDataList.get(i).getPosx(), tourDataList.get(i).getPosy(), tourInfoArrayList.get(i).getHomepage(), null, null, null, null, tourInfoArrayList.get(i).getUrl());
            Log.i("투어정보", SplashActivity.dbHelper.getResult());
        }
    }

    private void comeonImage(String dataSid) {
        StrictMode.enableDefaults();
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
}
