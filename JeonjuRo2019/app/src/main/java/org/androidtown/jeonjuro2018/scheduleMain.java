package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import java.util.ArrayList;

public class scheduleMain extends Fragment {
    private Gson gson;
    RecyclerView scheduleRecyclerView;
    RecyclerView.LayoutManager scheduleLayoutManager;
    ArrayList<scheduleInfo> scheduleInfoArrayList;

    public scheduleMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_schedule_main, container, false);
        scheduleRecyclerView = view.findViewById(R.id.recycler_view);
        scheduleRecyclerView.setHasFixedSize(true);
        scheduleLayoutManager = new LinearLayoutManager(getActivity());
        scheduleRecyclerView.setLayoutManager(scheduleLayoutManager);
        onSearchData();
        return view;
    }


    protected void onSearchData() {
        scheduleInfoArrayList = new ArrayList<>();
        SharedPreferences sp = this.getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        String strRoute = sp.getString("routeItem", "");
        Gson gson = new Gson();
        RouteItem routeItem = gson.fromJson(strRoute, RouteItem.class);

        if(!strRoute.equals("")) {
            String title = routeItem.getTitle();
            scheduleInfoArrayList.add(new scheduleInfo(title));
            scheduleAdapter scheduleAdapter = new scheduleAdapter(getActivity(), scheduleInfoArrayList);
            scheduleRecyclerView.setAdapter(scheduleAdapter);
        }else{
            scheduleInfoArrayList.add(new scheduleInfo("등록된 일정이 없습니다."));
            scheduleAdapter scheduleAdapter = new scheduleAdapter(getActivity(), scheduleInfoArrayList);
            scheduleRecyclerView.setAdapter(scheduleAdapter);

        }
    }
}