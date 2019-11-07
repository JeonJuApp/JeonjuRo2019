package org.androidtown.jeonjuro2018;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Otherway extends AppCompatActivity {

    private ListView listView = null;
    private ListAdapter listViewAdapter = null;
    AppCompatDialog progressDialog;
    int randomnum;
    String type;
    String loginID;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    FirebaseUser user;
    public FirebaseAuth mAuth; //Firebase로 로그인한 사용자 정보 알기 위해

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otherway);
        setCustomActionbar();

        mAuth = FirebaseAuth.getInstance(); // 인증
        user = mAuth.getCurrentUser(); //현재 로그인한 유저

        String tr = user.getEmail();
        int get = tr.indexOf("@");
        tr = tr.substring(0, get);

        Button button = (Button)findViewById(R.id.button);
        Button button2 = (Button)findViewById(R.id.button2);
        TextView textView = (TextView)findViewById(R.id.textView);
        TextView textView3 = (TextView)findViewById(R.id.textView3);

        Random rand = new Random();
        randomnum = rand.nextInt(100)+1;

        type = getIntent().getStringExtra("Type");
        textView.setText(type +"타입");

        if(type.equals("A")){
            textView3.setText("부지런한 " + tr +"님은 관람하고 사진찍기를 좋아하는 여행 스타일을 가지고 계시는군요! 그런 " + tr +"님께 아래와 같은 여행경로를 추천드립니다!");

        }
        else if (type.equals("B")) {
            textView3.setText("여유로운 여행을 즐기는 " + tr +"님은 관람하고 사진찍기를 좋아하시는 군요! 그런 " + tr +"님께 아래와 같은 여행경로를 추천드립니다!");

        }
        else if (type.equals("C")) {
            textView3.setText("부지런한 " + tr + "님은 맛집을 중심으로 여행 다니는 것을 좋아하시는군요! 그런 " + tr +"님께 아래와 같은 여행경로를 추천드립니다!");

        }
        else if (type.equals("D")) {
            textView3.setText("여유로운 여행을 즐기는 " + tr + "님은 맛집들을 찾아다니는 것을 좋아하시는군요! 그런 " + tr +"님께 아래와 같은 여행경로를 추천드립니다!");
        }
        else if (type.equals("E")) {
            textView3.setText("부지런한 " + tr + "님은 여러 액티비티를 즐기는 여행을 원하시는군요! 그런 " + tr +"님께 아래와 같은 여행경로를 추천드립니다!");

        }
        else {
            textView3.setText("여유로운 여행을 즐기는 " + tr + "님은 액티비티를 좋아하시는군요! 그런 " + tr +"님께 아래와 같은 여행경로를 추천드립니다!");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT);

        actionBar.setCustomView(mCustomView, params);
    }

    /*로딩바 켜기*/
    public void progressON(Activity activity, String message) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();

        }
        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });
        TextView progress_text = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            progress_text.setText(message);
        }
    }

    /*로딩바 설정*/
    public void progressSET(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }
        TextView progress_text = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            progress_text.setText(message);
        }
    }

    /*로딩바 끄기*/
    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    /*로딩바 화면*/
    private void startProgress() {
        progressON(this,  "Loading...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent myintent = new Intent(Otherway.this,Result.class);
                myintent.putExtra("type", type);
                myintent.putExtra("random", randomnum);
                startActivity(myintent);
                finish();
                progressOFF();
            }
        }, 2100);
    }
}