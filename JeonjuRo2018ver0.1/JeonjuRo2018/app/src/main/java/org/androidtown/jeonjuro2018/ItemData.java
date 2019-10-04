package org.androidtown.jeonjuro2018;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemData implements Parcelable {
    public String listTitle;
    public String listTitle2;
    public String listBus;
    public String listBus2;
    public String listBusStopNum;
    public String listBusTime;

    public ItemData(){
        listTitle="";
        listTitle2="";
        listBus="";
        listBus2="";
        listBusStopNum="";
        listBusTime="";
    }

    public ItemData(Parcel in){
        listTitle=in.readString();
        listTitle2=in.readString();
        listBus=in.readString();
        listBus2=in.readString();
        listBusStopNum=in.readString();
        listBusTime=in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(listTitle);
        dest.writeString(listTitle2);
        dest.writeString(listBus);
        dest.writeString(listBus2);
        dest.writeString(listBusStopNum);
        dest.writeString(listBusTime);
    }

    public String getListBus() {
        return listBus;
    }

    public String getListBus2() {
        return listBus2;
    }

    public String getListBusStopNum() {
        return listBusStopNum;
    }

    public String getListBusTime() {
        return listBusTime;
    }

    public String getListTitle() {
        return listTitle;
    }

    public String getListTitle2() {
        return listTitle2;
    }

    public void setListBus(String listBus) {
        this.listBus = listBus;
    }

    public void setListBus2(String listBus2) {
        this.listBus2 = listBus2;
    }

    public void setListBusStopNum(String listBusStopNum) {
        this.listBusStopNum = listBusStopNum;
    }

    public void setListBusTime(String listBusTime) {
        this.listBusTime = listBusTime;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public void setListTitle2(String listTitle2) {
        this.listTitle2 = listTitle2;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}