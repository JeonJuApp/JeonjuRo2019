package org.androidtown.jeonjuro2019;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.net.URL;
import java.util.ArrayList;

public class Result extends AppCompatActivity {
    ArrayList<Place> data = new ArrayList<>();

    ArrayList<FoodInfo> foodDataList;
    boolean storeName = false, storeImg = false ,b_posx = false , b_posy = false;
    String storeNm = null, ImgURL = null, posx = null, posy = null;

    ArrayList<TourInfo> accomoDataList;
    boolean indataSid = false, indataTitle = false, inPosx = false, inPosy = false, infileImg = false, infileUrl = false;
    String dataSid = null, dataTitle = null, fileUrl = null;

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

        foodDataList = new ArrayList<>();
        accomoDataList = new ArrayList<>();
        tourDataList = new ArrayList<>();

        type = getIntent().getStringExtra("type");
        randnum = getIntent().getExtras().getInt("random");

        gridView = (GridView)findViewById(R.id.grid);
        adapter = new GridViewAdapter(data,this);

        int i = 1;
        int t = 1;

        StrictMode.enableDefaults();

        //음식 파싱 시작
        try {
            String rl = "http://openapi.jeonju.go.kr/rest/jeonjufood/getWhiteRiceList?authApiKey=";
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D";
            String data = "&keyword=%EC%88%98%EB%9D%BC%EC%98%A8";
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
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("posx")){
                            b_posx = true;
                        }
                        if (parser.getName().equals("posy")){
                            b_posy = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_posx) {
                            posx = parser.getText();
                            b_posx = false;
                        }
                        if (b_posy) {
                            posy = parser.getText();
                            b_posy = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodDataList.add(new FoodInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("posx")){
                            b_posx = true;
                        }
                        if (parser.getName().equals("posy")){
                            b_posy = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            //Toast.makeText(getApplicationContext(),storeNm,Toast.LENGTH_SHORT).show();
                            storeName = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_posx) {
                            posx = parser.getText();
                            b_posx = false;
                        }
                        if (b_posy) {
                            posy = parser.getText();
                            b_posy = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodDataList.add(new FoodInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("posx")){
                            b_posx = true;
                        }
                        if (parser.getName().equals("posy")){
                            b_posy = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_posx) {
                            posx = parser.getText();
                            b_posx = false;
                        }
                        if (b_posy) {
                            posy = parser.getText();
                            b_posy = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodDataList.add(new FoodInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("posx")){
                            b_posx = true;
                        }
                        if (parser.getName().equals("posy")){
                            b_posy = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_posx) {
                            posx = parser.getText();
                            b_posx = false;
                        }
                        if (b_posy) {
                            posy = parser.getText();
                            b_posy = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodDataList.add(new FoodInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("posx")){
                            b_posx = true;
                        }
                        if (parser.getName().equals("posy")){
                            b_posy = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_posx) {
                            posx = parser.getText();
                            b_posx = false;
                        }
                        if (b_posy) {
                            posy = parser.getText();
                            b_posy = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodDataList.add(new FoodInfo(storeNm,ImgURL,posx,posy));
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
                        if (parser.getName().equals("mainImgUrl")) {
                            storeImg = true;
                        }
                        if (parser.getName().equals("posx")){
                            b_posx = true;
                        }
                        if (parser.getName().equals("posy")){
                            b_posy = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (storeName) { //isTitle이 true일 때 태그의 내용을 저장.
                            storeNm = parser.getText();
                            storeName = false;
                        }
                        if (storeImg) { //isTitle이 true일 때 태그의 내용을 저장.
                            ImgURL = parser.getText();
                            storeImg = false;
                        }
                        if (b_posx) {
                            posx = parser.getText();
                            b_posx = false;
                        }
                        if (b_posy) {
                            posy = parser.getText();
                            b_posy = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            foodDataList.add(new FoodInfo(storeNm,ImgURL,posx,posy));
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            ;
        }

        accomoDataList.add(new TourInfo("은행로86.2","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/2dc57345-3f23-47d7-842c-712ca4807a78.jpg.png","127.15269327163696","35.812323383019965"));
        accomoDataList.add(new TourInfo("동락원","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/49d8abf0-4acc-4686-b390-bc5ec08582f1.jpg.png","127.152801","35.817674"));
        accomoDataList.add(new TourInfo("군수집","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/9e211ac3-e254-4f39-b9fd-329987e131ef.jpg.png","127.1515905","35.8121144"));
        accomoDataList.add(new TourInfo("덕만재","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/1c2ef052-b147-4053-83c8-22be9dcfed0d.jpg.png","127.15219706296921","35.81854829001175"));
        accomoDataList.add(new TourInfo("호텔 한성","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/aa9aa625-9aa9-4958-bdc5-9faba8c872da.jpg.png","127.14481509999996","35.8198384"));
        accomoDataList.add(new TourInfo("별빛쉼터","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/b8eab158-9399-481a-a7fa-cc128c69f67e.jpg.png","127.1499216","35.8122687"));
        accomoDataList.add(new TourInfo("다들한옥체험","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/4920ffcd-a2f8-4f80-8d41-2e0c7d212929.jpg.png","127.15258061885834","35.81643422538531"));
        accomoDataList.add(new TourInfo("담소","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/5942d9ae-ab2c-4b4a-b1c9-1c4e6262d0ab.jpg.png","127.153546","35.812101"));
        accomoDataList.add(new TourInfo("이택구사랑채","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/f0380c92-ebcd-4f10-8193-9699e008277d.jpg.png","127.153434","35.816238"));
        accomoDataList.add(new TourInfo("나리다솜","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/49d8abf0-4acc-4686-b390-bc5ec08582f1.jpg.png","127.15063120000002","35.8199779"));

        tourDataList.add(new TourInfo("남고사지", "http://encykorea.aks.ac.kr/Contents/GetImage?id=b96c2aec-02ff-4cf4-b0ad-e560d5dae826&w=260&h=260&fit=w&clip=1","127.15898340000001" , "35.7996931"));
        tourDataList.add(new TourInfo("지행당","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/46a6d665-9430-4eab-9ca4-e62673a50e2a.jpg.png","127.15486420000002","35.8692253"));
        tourDataList.add(new TourInfo("전주부 지도","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/8b47c9ab-3aef-4965-a24a-649c0746c972.gif.png","127.12927760000002","35.844182"));
        tourDataList.add(new TourInfo("황강서원","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/e50ec3e8-c7f0-4fde-8c18-96c6aca0c7eb.jpg.png","127.10323459999995","35.829055"));
        tourDataList.add(new TourInfo("전주 경기전","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/7b432098-2e1a-4311-b256-5f7ab4cbec4e.jpg.png","127.15019259999997","35.815133"));
        tourDataList.add(new TourInfo("동고사","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/6e924765-249a-4aa9-af64-e941b0f59523.jpg.png","127.16620599999999","35.8103537"));
        tourDataList.add(new TourInfo("인후동 석불입상","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/8f0ea228-fec2-47d4-a728-12d1b389b753.jpg.png","127.15972650000003","35.8275578"));
        tourDataList.add(new TourInfo("승암사 소장 불서","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/906f515b-d4b5-48ca-9137-290458b4f078.jpg.png","127.09333049999998","35.8009943"));
        tourDataList.add(new TourInfo("반곡서원","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/ce850a40-f213-43eb-ada0-e7326eddcc20.jpg.png","127.15622370000006","35.8101921"));
        tourDataList.add(new TourInfo("예종대왕 태실 및 비","http://tour.jeonju.go.kr/planweb/upload/9be517a74f72e96b014f820463970068/inine/content/preview/54877dcb-c1c3-4fc9-a855-81d0c3881ec4.jpg.png","127.14975690000006","35.8153146"));


        gridView.setAdapter(adapter);
        if (randnum >= 2)
            randnum += 2;
        else
            randnum = 25;
        int save = 26;
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
                Intent myintent = new Intent(Result.this,Result2.class);
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
                Intent myintent = new Intent(Result.this,BusTotalResult.class);
                myintent.putParcelableArrayListExtra("data",data);
                startActivity(myintent);
                finish();
            }
        });

    }   private void setCustomActionbar() {
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