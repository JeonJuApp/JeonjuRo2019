package org.androidtown.jeonjuro2019;

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
import androidx.core.content.FileProvider;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hd.viewcapture.CaptureManager;
import com.hd.viewcapture.ViewCapture;

import java.io.File;

public class ShowSchedule extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_PERMISSION_STORAGE = 1111;
    TextView stationTitle1, stationTitle2, stationTitle3, stationTitle4;
    TextView stationCnt1, stationCnt2, stationCnt3, stationCnt4;
    TextView stationBus1, stationBus2, stationBus3, stationBus4;
    TextView stationTime1, stationTime2, stationTime3, stationTime4;
    ScrollView scroll;
    private CaptureManager.OnSaveResultListener listener;
    ItemData itemData;
    DatabaseReference mRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setCustomActionbar();
        Log.d("TAG", "인텐트 후");
        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        itemData = myBundle.getParcelable("data");

        mRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("plans");

        setContentView(R.layout.activity_show_schedule);
        callviewId();
        Log.i("정보3", itemData.getBusstop_title().get(0) + "");

        stationTitle1.setText(itemData.getBusstop_title().get(0) + " - " + itemData.getBusstop_title().get(1));
        stationTitle2.setText(itemData.getBusstop_title().get(1) + " - " + itemData.getBusstop_title().get(2));
        stationTitle3.setText(itemData.getBusstop_title().get(2) + " - " + itemData.getBusstop_title().get(3));
        stationTitle4.setText(itemData.getBusstop_title().get(3) + " - " + itemData.getBusstop_title().get(4));

        int size = itemData.getBusResult().size();

        stationTime1.setText(itemData.getBusResult().get(0));
        stationCnt1.setText(itemData.getBusResult().get(1));
        stationBus1.setText(itemData.getBusResult().get(2));

        stationTime2.setText(itemData.getBusResult().get(3));
        stationCnt2.setText(itemData.getBusResult().get(4));
        stationBus2.setText(itemData.getBusResult().get(5));

        stationTime3.setText(itemData.getBusResult().get(6));
        stationCnt3.setText(itemData.getBusResult().get(7));
        stationBus3.setText(itemData.getBusResult().get(8));

        if (size == 12) {
            stationTime4.setText(itemData.getBusResult().get(9));
            stationCnt4.setText(itemData.getBusResult().get(10));
            stationBus4.setText(itemData.getBusResult().get(11));
        }
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
                // File deleteFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/jeonju/" + "temp.jpg");
                // deleteFile.delete();
                break;
            case R.id.cameraButton:
                checkPermission();
                questionCapture();
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
