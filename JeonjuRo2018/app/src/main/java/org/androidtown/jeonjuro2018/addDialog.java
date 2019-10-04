package org.androidtown.jeonjuro2018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class addDialog extends AppCompatActivity {
    Button setBtn;
    EditText routeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);

        setBtn = (Button) findViewById(R.id.setBtn);
        routeName = (EditText) findViewById(R.id.routeName);
        Bundle bundle = getIntent().getExtras();
        final ArrayList<ItemData> dataArr = bundle.getParcelableArrayList("oData");
        final String totalTime = bundle.getString("totalTime");
        Log.d("값", totalTime);


        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = routeName.getText().toString();
                Log.d("Tag", title);
                onSaveData(title,totalTime,dataArr);
                startActivity(new Intent(addDialog.this, scheduleMain.class));
                finish();
            }
        });


    }

    protected void onSaveData(String title,String totalTime,ArrayList<ItemData>dataArr) {
        /*저장*/
        RouteItem routeItem = new RouteItem();
        routeItem.setRouteList(dataArr);
        routeItem.setTotalTime(totalTime);
        routeItem.setTitle(title);

        Gson gson = new GsonBuilder().create();
        String strRoute = gson.toJson(routeItem);

        SharedPreferences sp = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("routeItem", strRoute);
        editor.commit();
    }
}
