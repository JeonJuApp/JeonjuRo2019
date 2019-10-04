package org.androidtown.jeonjuro2018;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by starf on 2018-11-08.
 */

public class FoodAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context mContext;
    ArrayList<FoodInfo> foodInfoArrayList;

    public FoodAdapter(Context context, ArrayList<FoodInfo> items, View.OnClickListener mListener) {
        this.mContext = context;
        this.foodInfoArrayList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new FoodAdapter.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final FoodInfo data = foodInfoArrayList.get(position);
        FoodAdapter.MyViewHolder myViewHolder = (FoodAdapter.MyViewHolder) holder;

        Glide.with(mContext).load(data.getStoreImg()).asBitmap().fitCenter().into(((MyViewHolder) holder).foodPicture);
        myViewHolder.foodAddr.setText(foodInfoArrayList.get(position).storeAddr);
        myViewHolder.foodStore.setText(foodInfoArrayList.get(position).storeName);
        myViewHolder.foodMenu.setText(foodInfoArrayList.get(position).Menu);

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mContext, LocationDialog.class);
                intent.putExtra("name", data.getStoreName());
                intent.putExtra("location", data.getStoreAddr());
                intent.putExtra("des", "메인 메뉴 : " + data.getMenu() + "\n" +"오픈 시간 : " + data.getopenTime()+ "\n" +"개점일 : " + data.getstoreOpen());
                intent.putExtra("url", data.getStoreImg());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodInfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView foodPicture;
        TextView foodAddr;
        TextView foodStore;
        TextView foodMenu;
        LinearLayout linearLayout;

        MyViewHolder(View view) {
            super(view);
            foodPicture = view.findViewById(R.id.food_picture);
            foodStore = view.findViewById(R.id.store_name);
            foodMenu = view.findViewById(R.id.store_menu);
            foodAddr = view.findViewById(R.id.store_addr);
            linearLayout = view.findViewById(R.id.linear);
        }
    }

}
