package org.androidtown.jeonjuro2018;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusTotalResult extends AppCompatActivity {
    String routedetail = "";
    private ODsayService odsayService;
    private JSONObject jsonObject;
    int routeSize = 0;
    int dobo = 0;
    int cntService;
    ArrayList<Place> dataList;
    String[] dataTitle =new String[5];
    String[][] location = new String[5][2];
    TextView stationTitle1, stationTitle2, stationTitle3, stationTitle4;
    TextView stationCnt1, stationCnt2, stationCnt3, stationCnt4;
    TextView stationBus1, stationBus2, stationBus3, stationBus4;
    TextView stationTime1,stationTime2, stationTime3, stationTime4;
    ArrayList<String> busResult = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_totalroute);
        setCustomActionbar();
        callviewId();

        dataList = getIntent().getParcelableArrayListExtra("data");
        Log.i("정보: ",dataList.size()+"");
        for (int i = 0; i < dataList.size(); i++) {
            dataTitle[i] = dataList.get(i).getName();
            location[i][0] = dataList.get(i).getPosx();
            location[i][1] = dataList.get(i).getPosy();
            Log.i("정보: ",location[i][0] + " "+ location[i][1]);
        }
        init();

    }
    public  void allocateData(){
        //busResult = time, cnt, bus 정거장, 몇번, 몇분 소요
        stationTitle1.setText(dataTitle[0] +" - " + dataTitle[1]);
        stationTitle2.setText(dataTitle[1] +" - " + dataTitle[2]);
        stationTitle3.setText(dataTitle[2] +" - " + dataTitle[3]);
        stationTitle4.setText(dataTitle[3] +" - " + dataTitle[4]);

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
    private  void callviewId(){
        stationTitle1 = (TextView)findViewById(R.id.stationTitle1);
        stationTitle2 = (TextView)findViewById(R.id.stationTitle2);
        stationTitle3 = (TextView)findViewById(R.id.stationTitle3);
        stationTitle4 = (TextView)findViewById(R.id.stationTitle4);

        stationCnt1 = (TextView)findViewById(R.id.stationCnt1);
        stationCnt2 = (TextView)findViewById(R.id.stationCnt2);
        stationCnt3 = (TextView)findViewById(R.id.stationCnt3);
        stationCnt4 = (TextView)findViewById(R.id.stationCnt4);

        stationBus1 = (TextView)findViewById(R.id.stationBus1);
        stationBus2 = (TextView)findViewById(R.id.stationBus2);
        stationBus3 = (TextView)findViewById(R.id.stationBus3);
        stationBus4 = (TextView)findViewById(R.id.stationBus4);

        stationTime1 = (TextView)findViewById(R.id.stationTime1);
        stationTime2 = (TextView)findViewById(R.id.stationTime2);
        stationTime3 = (TextView)findViewById(R.id.stationTime3);
        stationTime4 = (TextView)findViewById(R.id.stationTime4);
    }
    private void init() {
        odsayService = ODsayService.init(BusTotalResult.this, getString(R.string.odsay_key));
        odsayService.setReadTimeout(500000);
        odsayService.setConnectionTimeout(500000);

        for(cntService = 0; cntService<4; cntService++) { //0 1     1  2    2 3   3 4
            odsayService.requestSearchPubTransPath(location[cntService][0], location[cntService][1], location[cntService+1][0], location[cntService+1][1], "0", "0", "0", onResultCallbackListener);
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

            for(int a = 0; a<pathArraycount; a++) {
                JSONObject pathArrayDetailOBJ = pathArray.getJSONObject(a);
                // 경로 타입 1 지하철 2 버스 3도보  (전주는 2 3만 존재)
                int pathType = pathArrayDetailOBJ.getInt("pathType");

                if( pathType == 2){
                    JSONObject infoOBJ = pathArrayDetailOBJ.getJSONObject("info");
                    int totalWalk = infoOBJ.getInt("totalWalk"); // 총 도보 이동거리
                    int payment = infoOBJ.getInt("payment"); // 요금
                    int totalTime = infoOBJ.getInt("totalTime"); // 소요시간
                    busResult.add(Integer.toString(totalTime)+"분 소요");
                    Log.i("정보 : 토탈", totalTime+"");
                    String mapObj = infoOBJ.getString("mapObj"); // 경로 디테일 조회 아이디
                    String firstStartStation = infoOBJ.getString("firstStartStation"); // 출발 정거장
                    String lastEndStation = infoOBJ.getString("lastEndStation"); // 도착 정거장

                    // 세부경로 디테일
                    JSONArray subPathArray = pathArrayDetailOBJ.getJSONArray("subPath");
                    int subPathArraycount = subPathArray.length();
                    // 반환 데이터 스트링으로

                    int busID=0;
                    JSONObject subPathOBJ = subPathArray.getJSONObject(0);
                    PathInfo prev, cur;
                    prev = cur = null;
                    prev = new PathInfo(subPathOBJ.getInt("trafficType"), subPathOBJ.getInt("distance"), subPathOBJ.getInt("sectionTime"));

                    for(int b = 0; b<subPathArraycount; b++){
                        subPathOBJ = subPathArray.getJSONObject(b);
                        int Type = subPathOBJ.getInt("trafficType"); // 이동방법
                        cur = new PathInfo(subPathOBJ.getInt("trafficType"), subPathOBJ.getInt("distance"), subPathOBJ.getInt("sectionTime"));

                        if((prev.pathType != cur.pathType) || dobo ==0) {
                            dobo++;
                            switch (Type) {
                                case 2:
                                    routedetail += "버스 ";
                                    break;
                                default:
                                    routedetail += "도보 ";
                                    if (cur.distance == 0 && cur.sectionTime == 0) {
                                        routedetail += "\n환승\n";
                                    } else {
                                        routedetail += "\n( " + cur.distance + "m 이동. ";
                                        routedetail += cur.sectionTime + "분 소요 )\n";
                                    }

                                    totalTime += cur.sectionTime;
                                    break;
                            }
                            routeSize++;
                            // 만약 버스라면,
                            if (Type == 2) {
                                String startName = subPathOBJ.getString("startName"); // 출발지
                                routedetail += startName + " 에서 ";
                                String endName = subPathOBJ.getString("endName"); // 도착지
                                String stationCount = subPathOBJ.getString("stationCount"); //거치는 정류장 수
                                routedetail += endName;
                                routedetail += "  총 " + stationCount + "정류장  ";
                                busResult.add(stationCount+"정거장");

                                // 버스 정보 가져옴 (정보가 많으므로 array로 가져오기)
                                JSONArray laneObj = subPathOBJ.getJSONArray("lane");

                                String busNo = laneObj.getJSONObject(0).getString("busNo"); // 버스번호정보
                                String busroute = " [" + busNo + "] 번 탑승 ";

                                busResult.add(busNo.substring(0,busNo.indexOf("("))+"번 탑승");
                                routedetail += busroute;
                                busID = laneObj.getJSONObject(0).getInt("busID"); // 버스정류장 id

                                //버스 세부내역 가져옴
                                JSONArray stationsObj = subPathOBJ.getJSONObject("passStopList").getJSONArray("stations");
                                int stationsObjObjCount = stationsObj.length();


                                for (int c = 0; c < stationsObjObjCount; c++) {
                                    routedetail += "거칠 곳\n";
                                    int stationIndex = stationsObj.getJSONObject(c).getInt("index");
                                    String stationName = stationsObj.getJSONObject(c).getString("stationName");
                                    routedetail += stationIndex + " " + stationName + "\n";
                                }
                             //   int distance = subPathOBJ.getInt("distance"); // 이동길이
                                routedetail += "\n( " + cur.distance + "m 이동. ";
                               // int sectionTime = subPathOBJ.getInt("sectionTime"); // 이동시간
                                routedetail += cur.sectionTime + "분 소요 )\n";
                                totalTime += cur.sectionTime;
                            }
                        }else{
                            if(prev.pathType ==3){
                                cur.sectionTime += prev.sectionTime;
                                cur.distance += prev.distance;
                            }
                        }

                        prev = cur;
                        cur = null;
                    } // 세부경로 종료
                    routedetail += "총" + Integer.toString(totalTime) + "분 소요\n " ;

                    Log.i("정보", routedetail+"");
                    break;
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        @Override
        public void onSuccess(ODsayData oDsayData, API api) {
            jsonObject = oDsayData.getJson();
            transportation(jsonObject);
            allocateData();
            if(cntService ==3){
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
}
