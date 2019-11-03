package org.androidtown.jeonjuro2018;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hd.viewcapture.CaptureManager;
import com.hd.viewcapture.ViewCapture;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;


public class ScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    boolean initem = false;
    private static final int MY_PERMISSION_STORAGE = 1111;
    int i = 0, j = 0;
    boolean instartId = false, instartNo = false, instopCnt = false, intripTime = false;
    String startId = null, startNo = null, stopCnt = null,
            tripTime = null;
    boolean instopStandard = false, instopKname = false;
    String stopStandard = null, stopKname = null;
    TextView totalTime;
    String[] temp = new String[2];
    ImageButton bus_btn;
    ImageButton walk_btn;
    ImageButton share_btn;
    ImageButton camera_btn;
    ImageButton add_btn; //일정 추가 버튼
    int total = 0;
    int y = 0, p = 0;
    TextView standard;
    private CaptureManager.OnSaveResultListener listener;
    ScrollView scroll;

    private ListView m_oListView = null;

    String[] dataTitle = {"1", "2", "3", "4", "5"};
    String[] dataBus = {"6", "7", "8", "9", "10"};
    String[] dataBusStopNum = {"도보이용", "도보이용", "도보이용", "도보이용"};
    String[] dataBusTime = {"o", "o", "o", "o"};
    ArrayList<Place> DataList;
    ArrayList<ItemData> oData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        setCustomActionbar();
        init();
        DataList = getIntent().getParcelableArrayListExtra("data");
        String[][] arr = new String[DataList.size()][3];

        int nDatCnt = 0;
        oData = new ArrayList<>();


        for (int i = 0; i < DataList.size(); i++) {
            int l = DataList.get(i).getType();
            arr[i][0] = DataList.get(i).getName();
            dataTitle[i] = DataList.get(i).getName();
            arr[i][1] = DataList.get(i).getPosx();
            arr[i][2] = DataList.get(i).getPosy();
    //        Log.i("정보: ",DataList.get(i).getType()+ " " +arr[i][0] + " "+ arr[i][1]+" " + arr[i][2]+"");
        }
        StrictMode.enableDefaults();
        y = 0;
        total = 0;
        bus(arr[0][1], arr[0][2]);//1
        bus(arr[1][1], arr[1][2]);//

        bus(arr[1][1], arr[1][2]);//근접정류장
        bus(arr[2][1], arr[2][2]);

        bus(arr[2][1], arr[2][2]);
        bus(arr[3][1], arr[3][2]);

        bus(arr[3][1], arr[3][2]);
        bus(arr[4][1], arr[4][2]);
        totalTime.setText(total + "분 ");

        if (total == 0)
            standard.setText("도보 기준");
        else
            standard.setText("버스 기준");

        for (int i = 0; i < 4; ++i) {
            ItemData oItem = new ItemData();
            oItem.setListTitle(dataTitle[nDatCnt]);
            oItem.setListTitle2(dataTitle[nDatCnt + 1]);
            oItem.setListBusTime(dataBusTime[nDatCnt]);
            oItem.setListBusStopNum(dataBusStopNum[nDatCnt]);
            oItem.setListBus(dataBus[nDatCnt]);
            oItem.setListBus2(dataBus[nDatCnt + 1]);
            oData.add(oItem);
            nDatCnt++;
        }
        m_oListView = (ListView) findViewById(R.id.listView);
        ListAdapter oAdapter = new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

        share_btn.setOnClickListener(this);
        camera_btn.setOnClickListener(this);
    }

    public void init() {
        walk_btn = (ImageButton)findViewById(R.id.walkBtn);
        share_btn = (ImageButton) findViewById(R.id.shareBtn);
        camera_btn = (ImageButton) findViewById(R.id.cameraBtn);
        add_btn = (ImageButton) findViewById(R.id.addBtn);
        totalTime = (TextView) findViewById(R.id.txt2);
        standard = (TextView) findViewById(R.id.txt3);

        scroll = (ScrollView) findViewById(R.id.scheduleView);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.walkBtn:
                Intent busIntent = new Intent(ScheduleActivity.this, BusTotalResult.class);
                busIntent.putParcelableArrayListExtra("data",DataList);
                startActivity(busIntent);
                break;
            case R.id.shareBtn:
                shareKakao();
                Toast.makeText(this, "공유기능", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cameraBtn:
                checkPermission();
                questionCapture();
                break;
            case R.id.addBtn:
                Intent intent = new Intent(ScheduleActivity.this, addDialog.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("oData", (ArrayList<? extends Parcelable>) oData);
                bundle.putString("totalTime", totalTime.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                break;

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

    //캡쳐하기전 다시 확인
    void questionCapture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("일정 캡쳐하실래요?");
        builder.setMessage("확인을 누르시면 바로 캡쳐됩니다.");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "일정캡쳐", Toast.LENGTH_SHORT).show();
                        ViewCapture.with(scroll).asJPG().setOnSaveResultListener(listener).save();
                        //scroll capture 기능
                    }
                });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //empty
            }
        });
        builder.show();
    }

    //카카오 공유기능
    public void shareKakao() {
        try {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder KakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            KakaoBuilder.addText("카카오링크 테스트입니다");

            kakaoLink.sendMessage(KakaoBuilder, this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되어 있습니다. 해당 권한을 허용해주세요.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        //  Toast.makeText(MainActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..

                break;
        }
    }

    private void bus(String posx, String posy) {
        //   posx = "127.09333049999998"; posy ="35.8009943";
        try {
            String rl = "http://openapi.jeonju.go.kr/jeonjubus/openApi/traffic/bus_stop_approach_common.do?ServiceKey=";
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D&arg1=";
            String data = posx + "&arg2=";
            String data2 = posy + "&arg3=5";
            URL url = new URL(rl + key + data + data2);//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("stopStandardid")) {
                            instopStandard = true;
                        }
                        if (parser.getName().equals("stopKname")) {
                            instopKname = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (instopStandard) { //isTitle이 true일 때 태그의 내용을 저장.
                            stopStandard = parser.getText();
                            instopStandard = false;
                        }
                        if (instopKname) {
                            stopKname = parser.getText();
                            instopKname = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            dataBus[p] = stopKname;
                            //     bustest.setText(bustest.getText() + "정류장ID" + homepage + "  " + dataContent +"\n");
                            temp[j] = stopStandard;
                            j++;//1//2
                            i++;//1
                            p++;
                        }
                        break;
                }
                parserEvent = parser.next();
                if (i == 1) {
                    i = 0;
                    if (j == 2) {
                        j = 0;
                        p--;
                        bus2(temp[0], temp[1]);
                    }
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    public void bus2(String stand, String stand2) {
        try {
            // String stand = "305100703";
            //String stand2 = "305102014";
            String rl = "http://openapi.jeonju.go.kr/jeonjubus/openApi/traffic/fromto_bus_search_transfer_detail_common.do?ServiceKey=";
            String key = "ScrjsS29GxaRJI8NXJCbrR%2FZMklimX6gTqyIBSWjMy7zt3w3HbzAgsL7%2BLFN6avz3jq%2BkA4YaW49yCNARnKvUQ%3D%3D&sStandardId=";
            String data = stand + "&eStandardId=";
            String data2 = stand2 + "&maxCnt=26";

            URL url = new URL(rl + key + data + data2);//검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();


            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("startId")) { //title 만나면 내용을 받을수 있게 하자
                            instartId = true;
                        }
                        if (parser.getName().equals("startNo")) { //title 만나면 내용을 받을수 있게 하자
                            instartNo = true;
                        }
                        if (parser.getName().equals("stopCnt")) {
                            instopCnt = true;
                        }
                        if (parser.getName().equals("tripTime")) {
                            intripTime = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (instartId) { //isTitle이 true일 때 태그의 내용을 저장.
                            startId = parser.getText();
                            instartId = false;
                        }
                        if (instartNo) {
                            startNo = parser.getText();
                            instartNo = false;
                        }
                        if (instopCnt) {
                            stopCnt = parser.getText();
                            instopCnt = false;
                        }
                        if (intripTime) {
                            tripTime = parser.getText();
                            intripTime = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("list")) {
                            if (Integer.parseInt(stopCnt) > 16) {
                                int stop = Integer.parseInt(stopCnt) / 2;
                                stopCnt = "" + stop;
                            } else if (Integer.parseInt(stopCnt) > 40) {
                                int stop = Integer.parseInt(stopCnt) / 4;
                                stopCnt = "" + stop;
                            }
                            dataBusTime[y] = tripTime + "분";
                            dataBusStopNum[y] = startNo + "번 탑승 > " + stopCnt + "정거장";
                            y++;
                            total += Integer.parseInt(tripTime);
                            if (startNo == null)
                                dataBusStopNum[y] = "도보 이용";
                        }
                        break;
                }
                parserEvent = parser.next();
                if (initem) {
                    initem = false;
                    break;
                }
            }
        } catch (Exception e) {
        }
    }
}