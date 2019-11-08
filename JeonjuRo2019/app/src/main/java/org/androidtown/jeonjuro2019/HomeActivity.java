package org.androidtown.jeonjuro2019;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class HomeActivity extends Fragment implements  View.OnClickListener {
    AppCompatDialog progressDialog;

    public HomeActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);
        RelativeLayout tourBtn = (RelativeLayout) view.findViewById(R.id.tourBtn);
        RelativeLayout eatingBtn = (RelativeLayout) view.findViewById(R.id.eatingBtn);
        RelativeLayout accomoBtn = (RelativeLayout) view.findViewById(R.id.accomoBtn);
        RelativeLayout introBtn = (RelativeLayout) view.findViewById(R.id.introBtn);
        tourBtn.setOnClickListener(this);
        eatingBtn.setOnClickListener(this);
        accomoBtn.setOnClickListener(this);
        introBtn.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tourBtn:
                startProgress();
                startActivity(new Intent(getContext(), TourMain.class));
                break;
            case R.id.eatingBtn:
                startProgress();
                startActivity(new Intent(getContext(), RestMain.class));
                break;
            case R.id.accomoBtn:
                startProgress();
                startActivity(new Intent(getContext(), AccomoMain.class));
                break;
            case R.id.introBtn:
                startActivity(new Intent(getContext(), IntroActivity.class));
                break;
        }
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
        progressON(getActivity(), "Loading...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressOFF();
            }
        }, 2100);
    }
}