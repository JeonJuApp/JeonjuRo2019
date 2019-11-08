package org.androidtown.jeonjuro2019;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    ArrayList<Place> place;
    Context context;
    boolean isVisible = false;


    public GridViewAdapter(ArrayList<Place> place, Context context) {
        this.place = place;
        this.context = context;
    }

    @Override
    public  int getCount(){
        return place.size();
    }

    @Override
    public Object getItem(int position) {
        return place.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = new GridItem(context);

        ((GridItem)convertView).setData(place.get(position), context);

        return convertView;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void addPlace(Place one) {
        place.add(one);
        refresh();
    }

    public void change(boolean flag) {
        isVisible = flag;
    }

    public void modifyPlace(String name, String imageno, int position) {
        Place modify = place.get(position);
        modify.setName(name);
        modify.setImgno(imageno);
        refresh();
    }
}
