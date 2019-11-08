package org.androidtown.jeonjuro2018;

import android.content.ClipData;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class ItemData implements Parcelable {
    private String id;
    private String title;
    private ArrayList<String> busResult = new ArrayList<>();
    private ArrayList<String> busstop_title = new ArrayList<>();


    public ItemData() {
    }
    public ItemData(String title, ArrayList<String> busResult,  ArrayList<String> busstop_title){
        this.title = title;
        this.busResult = busResult;
        this.busstop_title = busstop_title;
    }

    public ItemData(Parcel in) {
        id = in.readString();
        title = in.readString();
        busResult = in.readArrayList(null);
        busstop_title = in.readArrayList(null);

    }

    public static final Creator<ItemData> CREATOR = new Creator<ItemData>() {
        @Override
        public ItemData createFromParcel(Parcel source) {
            return new ItemData(source);
        }

        @Override
        public ItemData[] newArray(int size) {
            return new ItemData[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getBusResult() {
        return busResult;
    }

    public ArrayList<String> getBusstop_title() {
        return busstop_title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBusResult(ArrayList<String> busResult) {
        this.busResult = busResult;
    }

    public void setBusstop_title(ArrayList<String> busstop_title) {
        this.busstop_title = busstop_title;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeList(busResult);
        dest.writeList(busstop_title);
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}