package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import java.util.ArrayList;

public class scheduleMain extends Fragment {
    RecyclerView mRecyclerView;
    scheduleAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    ItemData route_info;
    ArrayList<ItemData>fileList;

    public scheduleMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_schedule_main, container, false);
        fileList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        View.OnClickListener mListener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int position = mRecyclerView.getChildAdapterPosition(v);
                ItemData itemData = fileList.get(position);

                Log.d("TAG", "인텐트 전");
                Intent intent = new Intent(getActivity(), ShowSchedule.class);
                Bundle myBundle = new Bundle();
                myBundle.putParcelable("data", itemData);
                intent.putExtras(myBundle);
                startActivity(intent);
            }
        };
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference plansRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("plans");
        View.OnLongClickListener mLongListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = mRecyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setTitle("일정 삭제");
                alertDialogBuilder.setMessage("일정을 삭제하시겠습니까?")
                        .setMessage("삭제")
                        .setCancelable(false)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                plansRef.child(fileList.get(position).getId()).removeValue();
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
            ;
        };
        mAdapter = new scheduleAdapter(getContext(), fileList, mListener, mLongListener);
        mRecyclerView.setAdapter(mAdapter);

        plansRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                route_info = dataSnapshot.getValue(ItemData.class);
                route_info.setId(dataSnapshot.getKey());
                fileList.add(route_info);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                int position;
                ItemData itemData = dataSnapshot.getValue(ItemData.class);
                itemData.setId(dataSnapshot.getKey());
                for(position=0; position<fileList.size();position++){
                    if(fileList.get(position).getId().equals(itemData.getId()))
                        break;
                }
                fileList.remove(position);
                fileList.add(position, itemData);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                int position;
                for(position=0; position<fileList.size();position++){
                    if(dataSnapshot.getKey().equals(fileList.get(position).getId())){
                        break;
                    }
                }
                fileList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
class scheduleAdapter extends RecyclerView.Adapter<scheduleAdapter.ViewHolder>{
    Context mContext;
    ArrayList<ItemData> items;
    View.OnClickListener mListener;
    View.OnLongClickListener mLongListener;

    public scheduleAdapter(Context context, ArrayList<ItemData> items, View.OnClickListener mListener, View.OnLongClickListener mLongListener){
        this.mContext = context;
        this.items = items;
        this.mListener = mListener;
        this.mLongListener = mLongListener;
    }
    @NonNull
    @Override
    public scheduleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull scheduleAdapter.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.textView.setText(items.get(position).getTitle());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.scheduleTitle);
            linearLayout = view.findViewById(R.id.linear);

            if(mListener != null)
                itemView.setOnClickListener(mListener);
            if(mLongListener != null)
                itemView.setOnLongClickListener(mLongListener);
        }
    }
}