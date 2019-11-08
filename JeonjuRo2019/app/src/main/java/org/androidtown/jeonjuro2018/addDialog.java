package org.androidtown.jeonjuro2018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class addDialog extends AppCompatActivity {
    Button setBtn;
    EditText routeName;
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference().child("users").
            child(mUser.getUid()).child("plans");

    ItemData bundle_itemData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dialog);

        setBtn = (Button) findViewById(R.id.setBtn);
        routeName = (EditText) findViewById(R.id.routeName);

        Intent myIntent = getIntent();
        Bundle myBundle = myIntent.getExtras();
        bundle_itemData = myBundle.getParcelable("data");

    }
    @Override
    protected void onStart(){
        super.onStart();

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "경로 저장에 실패했습니다.", Toast.LENGTH_LONG).show();
            }
        });
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = routeName.getText().toString();
                Log.d("Tag", title);
                Log.i("정보2", bundle_itemData.getBusstop_title()+"");
                Log.i("정보2_위치", bundle_itemData.getBusResult()+"");

                ItemData itemData = new ItemData(title, bundle_itemData.getBusResult(), bundle_itemData.getBusstop_title());
                mRootRef.push().setValue(itemData);
                Intent intent = new Intent(addDialog.this, scheduleMain.class);
                Bundle myBundle = new Bundle();
                myBundle.putParcelable("data", itemData);
                intent.putExtras(myBundle);
                startActivity(intent);
                finish();
            }
        });
    }

}


