package org.androidtown.jeonjuro2018;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.StringTokenizer;

public class CustomActivity extends Fragment implements View.OnClickListener {
    Button checkBtn;
    Button oneday, twoday, threeday, fourday;
    Button traveler_friend, traveler_lover, traveler_family, traveler_alone;
    Button goal_picture, goal_eating, goal_activity;
    Button tendency_diligent, tendency_nodili;
    AppCompatDialog progressDialog;
    String gogoresult="";

    public CustomActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_custom, container, false);
        oneday = view.findViewById(R.id.oneday);
        twoday = view.findViewById(R.id.twoday);
        threeday = view.findViewById(R.id.threeday);
        fourday = view.findViewById(R.id.fourday);

        traveler_friend = view.findViewById(R.id.traveler_friend);
        traveler_lover = view.findViewById(R.id.traveler_lover);
        traveler_family = view.findViewById(R.id.traveler_family);
        traveler_alone = view.findViewById(R.id.traveler_alone);

        goal_picture = view.findViewById(R.id.goal_picture);
        goal_eating = view.findViewById(R.id.goal_eating);
        goal_activity = view.findViewById(R.id.goal_activity);

        tendency_diligent = view.findViewById(R.id.tendency_diligent);
        tendency_nodili = view.findViewById(R.id.tendency_nodili);

        checkBtn = (Button)view.findViewById(R.id.checkBtn); //조사하기 버튼

        checkBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!check())
                    Toast.makeText(getActivity(), "모든 항목을 클릭해주세요", Toast.LENGTH_LONG).show();
                else {
                    startProgress();
                    StringTokenizer stringTokenizer = new StringTokenizer(clickname, " ");
                    int countTokens = stringTokenizer.countTokens();
                    int[] aa = new int[countTokens]; //5
                    int t = 0;

                    /*클릭된거 array 위치 알려주는 코드 - 추후 코스 짤수있는 코드 */
                    for (int j = 0; j < 4; j++) {
                        for (int i = 0; i < 4; i++) {
                            if (travel[j][i] == 1) {
                                if(travel[2][0] ==1&& travel[3][0]==1){
                                    gogoresult = "A";
                                }
                                else if(travel[2][0] ==1&& travel[3][1]==1){
                                    gogoresult = "B";
                                }
                                else if(travel[2][1] ==1&& travel[3][0]==1){
                                    gogoresult = "C";
                                }
                                else if(travel[2][1] ==1&& travel[3][1]==1){
                                    gogoresult = "D";
                                }
                                else if(travel[2][2] ==1&& travel[3][0]==1){
                                    gogoresult = "E";
                                }
                                else if(travel[2][2] ==1&& travel[3][1]==1){
                                    gogoresult = "F";
                                }
                            }
                        }
                    }
                }
            }
        });

        oneday.setOnClickListener(this);
        twoday.setOnClickListener(this);
        threeday.setOnClickListener(this);
        fourday.setOnClickListener(this);

        traveler_friend.setOnClickListener(this);
        traveler_lover.setOnClickListener(this);
        traveler_family.setOnClickListener(this);
        traveler_alone.setOnClickListener(this);

        goal_picture.setOnClickListener(this);
        goal_eating.setOnClickListener(this);
        goal_activity.setOnClickListener(this);

        tendency_diligent.setOnClickListener(this);
        tendency_nodili.setOnClickListener(this);
        return view;
    }

    int[][] travel = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    Button temp;
    int id_num, list;
    String buttonName;

    @Override
    public void onClick(View view) {
        buttonName = view.getResources().getResourceEntryName(view.getId());
        switch (view.getId()) {
            case R.id.oneday:
                temp = oneday;
                id_num = 1;
                break;
            case R.id.twoday:
                temp = twoday;
                id_num = 2;
                break;
            case R.id.threeday:
                temp = threeday;
                id_num = 3;
                break;
            case R.id.fourday:
                temp = fourday;
                id_num = 4;
                break;
            case R.id.traveler_friend:
                temp = traveler_friend;
                id_num = 1;
                break;
            case R.id.traveler_lover:
                temp = traveler_lover;
                id_num = 2;
                break;
            case R.id.traveler_family:
                temp = traveler_family;
                id_num = 3;
                break;
            case R.id.traveler_alone:
                temp = traveler_alone;
                id_num = 4;
                break;
            case R.id.goal_picture:
                temp = goal_picture;
                id_num = 1;
                break;
            case R.id.goal_eating:
                temp = goal_eating;
                id_num = 2;
                break;
            case R.id.goal_activity:
                temp = goal_activity;
                id_num = 3;
                break;
            case R.id.tendency_diligent:
                temp = tendency_diligent;
                id_num = 1;
                break;
            case R.id.tendency_nodili:
                temp = tendency_nodili;
                id_num = 2;
                break;
        }
        click(temp, id_num);
    }

    int[] sum = {0, 0, 0, 0};
    int size;

    /*항목마다 size 지정 함수*/
    public void distingush() {
        if (buttonName.contains("day")) {
            list = 0;
            size = 4;
        } else if (buttonName.contains("traveler")) {
            list = 1;
            size = 4;
        } else if (buttonName.contains("goal")) {
            list = 2;
            size = 3;
        } else if (buttonName.contains("tendency")) {
            list = 3;
            size = 2;
        }
    }

    String clickname = "";
    boolean cnt = false;
    boolean first = false, second = false, third = false, four =false;
    Button clicked_button1 = temp;
    Button clicked_button2 = temp;
    Button clicked_button3 = temp;
    Button clicked_button4 = temp;

    /*selected 해주기, 클릭된거 clickname에 저장하는 함수*/
    public void click(Button temp, int num) {
        distingush();
        num--;

        if (travel[list][num] == 1) {
            temp.setSelected(false);
            travel[list][num] = 0;
            sum[list] = 0;
            clickname = clickname.replace(list + temp.getText().toString() + " ", "");
        } else {//나머지 항목은 한개만 선택 가능하도록
            for (int i = 0; i < size; i++) {
                if (travel[list][i] > 0) {
                    if (list == 0) {
                        if (first == true) {
                            clicked_button1.setSelected(false);
                            clickname = clickname.replace(list + clicked_button1.getText().toString() + " ", "");
                            temp.setSelected(true);
                            travel[list][num]++;
                            clicked_button1 = temp;
                        }
                    } else if (list == 1) {
                        if (second == true) {
                            clicked_button2.setSelected(false);
                            clickname = clickname.replace(list + clicked_button2.getText().toString() + " ", "");
                            temp.setSelected(true);
                            clicked_button2 = temp;
                            travel[list][num]++;
                        }
                    } else if(list ==2){
                        if(four ==true){
                            clicked_button4.setSelected(false);
                            clickname = clickname.replace(list + clicked_button4.getText().toString() + " ", "");
                            temp.setSelected(true);
                            clicked_button4 = temp;
                            travel[list][num]++;
                        }
                    } else if (list == 3) {
                        if (third == true) {
                            clicked_button3.setSelected(false);
                            clickname = clickname.replace(list + clicked_button3.getText().toString() + " ", "");
                            temp.setSelected(true);
                            clicked_button3 = temp;
                            travel[list][num]++;
                        }
                    }
                    travel[list][i] = 0;
                    clickname = clickname.concat(list + temp.getText().toString() + " ");

                    break;
                }
                if (travel[list][i] == 0) {
                    sum[list]++;
                    if (sum[list] == size) {
                        travel[list][num]++;
                        temp.setSelected(true);
                        if (list == 0) {
                            first = true;
                            clicked_button1 = temp;
                        } else if (list == 1) {
                            second = true;
                            clicked_button2 = temp;
                        } else if (list == 2) {
                            four = true;
                            clicked_button4 = temp;
                        }else if (list == 3) {
                            third = true;
                            clicked_button3 = temp;
                        }
                        clickname = clickname.concat(list + temp.getText().toString() + " ");
                    }

                }

            }
        }
    }

    /*여행일수, 동행인, 목적, 성향 하나라도 체크안한거 있는지 확인하는 함수*/
    public boolean check() {
        int temp_size = 4;
        int total = 0;
        for (int i = 0; i < 4; i++) {
            if (i > 1)
                temp_size--;
            for (int j = 0; j < temp_size; j++) {
                if (travel[i][j] == 1)
                    total++;
            }
            if (total == 0) {
                return false;
            }
            total = 0;
        }
        return true;
    }

    /*onClick= search 조사하기 눌렀을때 작동하는 함수 ,  최종 클릭된거 search함수*/


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
                Intent intent = new Intent(getActivity(), Otherway.class);
                intent.putExtra("Type", gogoresult);
                startActivity(intent);
            }
        }, 2500);
    }
}