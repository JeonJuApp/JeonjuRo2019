package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class scheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<scheduleInfo> scheduleInfoArrayList;
    String titleName;

    public scheduleAdapter(Context context, ArrayList<scheduleInfo> items) {
        this.mContext = context;
        this.scheduleInfoArrayList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_recycler_view, parent, false);
        return new MyViewHolder(v);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final scheduleInfo data = scheduleInfoArrayList.get(position);
        titleName = data.getTitle();
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (!titleName.equals("등록된 일정이 없습니다."))
            ((MyViewHolder) holder).subTextView.setText("일정을 보시려면 눌러주세요");
        ((MyViewHolder) holder).textView.setText(titleName);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!titleName.equals("등록된 일정이 없습니다.")) {
                    Intent intent = new Intent(mContext, ShowActivity.class);
                    mContext.startActivity(intent);
                }else
                    return;
            }
        });
    }

    @Override
    public int getItemCount() {
        return scheduleInfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView, subTextView;
        LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.scheduleTitle);
            subTextView = (TextView) view.findViewById(R.id.sub);
            linearLayout = view.findViewById(R.id.linear);
        }
    }
}
