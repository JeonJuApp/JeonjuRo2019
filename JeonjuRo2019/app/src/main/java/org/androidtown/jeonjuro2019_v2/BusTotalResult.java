package org.androidtown.jeonjuro2019_v2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.viewcapture.CaptureManager;
import com.hd.viewcapture.ViewCapture;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class BusTotalResult extends AppCompatActivity implements View.OnClickListener {
    String routedetail = "";
    private static final int MY_PERMISSION_STORAGE = 1111;
    private ODsayService odsayService;
    private JSONObject jsonObject;
    private CaptureManager.OnSaveResultListener listener;
    int routeSize = 0;
    int dobo = 0;
    ScrollView scroll;
    int cntService;
    ArrayList<Place> dataList;
    //루트 정보 있음
    ArrayList<String> dataTitle = new ArrayList<>();
    //각 루트의 소요시간, 정거장수, 버스 번호
    ArrayList<String> busResult = new ArrayList<>();

    String[][] location = new String[5][2];
    TextView stationTitle1, stationTitle2, stationTitle3, stationTitle4;
    TextView stationCnt1, stationCnt2, stationCnt3, stationCnt4;
    TextView stationBus1, stationBus2, stationBus3, stationBus4;
    TextView stationTime1, stationTime2, stationTime3, stationTime4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_totalroute);
        setCustomActionbar();
        callviewId();

        dataList = getIntent().getParcelableArrayListExtra("data");
        Log.i("정보: ", dataList.size() + "");
        for (int i = 0; i < dataList.size(); i++) {
            dataTitle.add(dataList.get(i).getName()); //경로 시작점
            //dataTitle.add(dataList.get(i).getName()); //경로 도착점
            location[i][0] = dataList.get(i).getPosx();
            location[i][1] = dataList.get(i).getPosy();
            Log.i("정보: ", location[i][0] + " " + location[i][1]);
        }
        init();

    }

    public void allocateData() {
        //busResult = time, cnt, bus 정거장, 몇번, 몇분 소요
        stationTitle1.setText(dataTitle.get(0) + " - " + dataTitle.get(1));
        stationTitle2.setText(dataTitle.get(1) + " - " + dataTitle.get(2));
        stationTitle3.setText(dataTitle.get(2) + " - " + dataTitle.get(3));
        stationTitle4.setText(dataTitle.get(3) + " - " + dataTitle.get(4));


        stationTime1.setText(busResult.get(0));
        stationCnt1.setText(busResult.get(1));
        stationBus1.setText(busResult.get(2));

        stationTime2.setText(busResult.get(3));
        stationCnt2.setText(busResult.get(4));
        stationBus2.setText(busResult.get(5));

        stationTime3.setText(busResult.get(6));
        stationCnt3.setText(busResult.get(7));
        stationBus3.setText(busResult.get(8));

        stationTime4.setText(busResult.get(9));
        stationCnt4.setText(busResult.get(10));
        stationBus4.setText(busResult.get(11));


    }

    private void callviewId() {
        stationTitle1 = (TextView) findViewById(R.id.stationTitle1);
        stationTitle2 = (TextView) findViewById(R.id.stationTitle2);
        stationTitle3 = (TextView) findViewById(R.id.stationTitle3);
        stationTitle4 = (TextView) findViewById(R.id.stationTitle4);

        stationCnt1 = (TextView) findViewById(R.id.stationCnt1);
        stationCnt2 = (TextView) findViewById(R.id.stationCnt2);
        stationCnt3 = (TextView) findViewById(R.id.stationCnt3);
        stationCnt4 = (TextView) findViewById(R.id.stationCnt4);

        stationBus1 = (TextView) findViewById(R.id.stationBus1);
        stationBus2 = (TextView) findViewById(R.id.stationBus2);
        stationBus3 = (TextView) findViewById(R.id.stationBus3);
        stationBus4 = (TextView) findViewById(R.id.stationBus4);

        stationTime1 = (TextView) findViewById(R.id.stationTime1);
        stationTime2 = (TextView) findViewById(R.id.stationTime2);
        stationTime3 = (TextView) findViewById(R.id.stationTime3);
        stationTime4 = (TextView) findViewById(R.id.stationTime4);

        scroll = (ScrollView) findViewById(R.id.scrollview);
    }

    private void init() {
        odsayService = ODsayService.init(BusTotalResult.this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(500000);
        odsayService.setConnectionTimeout(500000);

        for (cntService = 0; cntService < 4; cntService++) { //0 1     1  2    2 3   3 4
            odsayService.requestSearchPubTransPath(location[cntService][0], location[cntService][1], location[cntService + 1][0], location[cntService + 1][1], "0", "0", "0", onResultCallbackListener);
        }
        //    bt_api_call.setOnClickListener(onClickListener);
        //    rg_object_type.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private void transportation(JSONObject jsonObject) {
        try {
            JSONObject result = jsonObject.getJSONObject("result");
            JSONArray pathArray = result.getJSONArray("path");

            // pathArray 안의 경로 갯수
            int pathArraycount = pathArray.length();

            for (int a = 0; a < pathArraycount; a++) {
                JSONObject pathArrayDetailOBJ = pathArray.getJSONObject(a);
                // 경로 타입 1 지하철 2 버스 3도보  (전주는 2 3만 존재)
                int pathType = pathArrayDetailOBJ.getInt("pathType");

                if (pathType == 2) {
                    JSONObject infoOBJ = pathArrayDetailOBJ.getJSONObject("info");
                    int totalTime = infoOBJ.getInt("totalTime"); // 소요시간
                    busResult.add(Integer.toString(totalTime) + "분 소요");
                    Log.i("정보 : 토탈", totalTime + "");
                    // 세부경로 디테일
                    JSONArray subPathArray = pathArrayDetailOBJ.getJSONArray("subPath");
                    int subPathArraycount = subPathArray.length();
                    // 반환 데이터 스트링으로

                    int busID = 0;
                    JSONObject subPathOBJ = subPathArray.getJSONObject(0);


                    for (int b = 0; b < subPathArraycount; b++) {
                        subPathOBJ = subPathArray.getJSONObject(b);
                        int Type = subPathOBJ.getInt("trafficType"); // 이동방법

                        // 만약 버스라면,
                        if (Type == 2) {
                            String startName = subPathOBJ.getString("startName"); // 출발지
                            routedetail += startName + " 에서 ";
                            String endName = subPathOBJ.getString("endName"); // 도착지
                            String stationCount = subPathOBJ.getString("stationCount"); //거치는 정류장 수
                            routedetail += endName;
                            routedetail += "  총 " + stationCount + "정류장  ";
                            busResult.add(stationCount + "정거장");

                            // 버스 정보 가져옴 (정보가 많으므로 array로 가져오기)
                            JSONArray laneObj = subPathOBJ.getJSONArray("lane");

                            String busNo = laneObj.getJSONObject(0).getString("busNo"); // 버스번호정보
                            String busroute = " [" + busNo + "] 번 탑승 ";

                            busResult.add(busNo.substring(0, busNo.indexOf("(")) + "번 탑승");
                            routedetail += busroute;
                            busID = laneObj.getJSONObject(0).getInt("busID"); // 버스정류장 id

                            //버스 세부내역 가져옴
                            JSONArray stationsObj = subPathOBJ.getJSONObject("passStopList").getJSONArray("stations");
                            int stationsObjObjCount = stationsObj.length();

                            routedetail += totalTime + "분 소요 )\n";
                            //  totalTime += cur.sectionTime;
                        }
                    }
                    routedetail += "총" + Integer.toString(totalTime) + "분 소요\n ";
                } // 세부경로 종료

                Log.i("정보", routedetail + "");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            jsonObject = oDsayData.getJson();
            transportation(jsonObject);
            allocateData();
            if (cntService == 3) {
                allocateData();
            }
            // tv_data.setText(jsonObject.toString());
        }

        @Override
        public void onError(int i, String errorMessage, API api) {
            // tv_data.setText("API : " + api.name() + "\n" + errorMessage);
            //busResult = time, cnt, bus
            allocateData();

            busResult.add("700m 이내");
            busResult.add("0정거장");
            busResult.add("도보 이용");
            //  출,도착지 700m이내. 도보이용해주세요.
        }
    };

    private static class PathInfo {
        int pathType;
        int distance, sectionTime;

        public PathInfo(int pathType, int distance, int sectionTime) {
            this.pathType = pathType;
            this.distance = distance;
            this.sectionTime = sectionTime;
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shareButton:
                checkPermission();
                fileshare();
                Toast.makeText(this, "공유가능", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cameraButton:
                checkPermission();
                questionCapture();
                break;
            case R.id.addButton:
                ItemData itemData = new ItemData("", busResult, dataTitle);
                Log.i("정보4", busResult+"");
                Intent intent = new Intent(BusTotalResult.this, addDialog.class);
                Bundle myBundle = new Bundle();
                myBundle.putParcelable("data", itemData);
                intent.putExtras(myBundle);
                Log.i("정보1", itemData.getBusstop_title()+"");
                startActivity(intent);
                break;

        }
    }
    private void fileshare() {
        ViewCapture.with(scroll).asJPG().setDirectoryName("jeonju").setFileName("temp").setOnSaveResultListener(listener).save();
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider",
                new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/jeonju/" + "temp.jpg")));
        Intent chooser = Intent.createChooser(intent, "공유하기");
        this.startActivity(chooser);
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
}