package org.androidtown.jeonjuro2019_v2;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<TourInfo> tourInfoArrayList;

    public MyAdapter(Context context, ArrayList<TourInfo> items) {
        this.mContext = context;
        this.tourInfoArrayList = items;

    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TourInfo data = tourInfoArrayList.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        Glide.with(mContext)
                .load(data.getUrl()).asBitmap()
                .fitCenter()
                .into(((MyViewHolder) holder).tourPicture);
        Log.i("숙박", "들어옴");
        Log.i("숙박", tourInfoArrayList.get(position).tourName+"");
        myViewHolder.tourName.setText(tourInfoArrayList.get(position).tourName);
        myViewHolder.tourLocation.setText(tourInfoArrayList.get(position).tourLocation);
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LocationDialog.class);
                intent.putExtra("name", data.getTourName());
                intent.putExtra("location", data.getTourLocation()+"\n" + data.getHomepage());
                intent.putExtra("url", data.getUrl());
                intent.putExtra("des", data.getDataContent());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return tourInfoArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView tourPicture;
        TextView tourName;
        TextView tourLocation;

        LinearLayout linearLayout;
        TextView tourContent;
        TextView homepage;

        public MyViewHolder(View view) {
            super(view);
            tourPicture = view.findViewById(R.id.tour_picture);
            tourName = view.findViewById(R.id.tour_name);
            tourLocation = view.findViewById(R.id.tour_location);
            linearLayout = view.findViewById(R.id.linear);
            tourContent = view.findViewById(R.id.tour_content);
            homepage = view.findViewById(R.id.tour_homepage);
        }
    }

}