package org.androidtown.jeonjuro2019_v2;

import android.os.Parcel;
import android.os.Parcelable;

public class TourInfo implements Parcelable {
    private String url;
    public String tourName;
    public String tourLocation;
    public String dataContent;
    public String homepage;
    private String posx;
    private String posy;

    public TourInfo(String url, String tourName, String tourLocation, String dataContent, String homepage) {
        this.url = url;
        this.tourLocation = tourLocation;
        this.tourName = tourName;
        this.dataContent = dataContent;
        this.homepage = homepage;
    }

    public TourInfo(String tourName, String url, String posx, String posy){
        this.tourName = tourName;
        this.url = url;
        this.posx = posx;
        this.posy = posy;
    }

    public TourInfo(Parcel in) {
        this.tourName = in.readString();
        this.url = in.readString();
        this.posx = in.readString();
        this.posy = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tourName);
        dest.writeString(this.url);
        dest.writeString(this.posx);
        dest.writeString(this.posy);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public TourInfo createFromParcel(Parcel in) {
            return new TourInfo(in);
        }

        @Override
        public TourInfo[] newArray(int size) {
            // TODO Auto-generated method stub
            return new TourInfo[size];
        }

    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTourName() {
        return tourName;
    }

    public String getTourLocation() {
        return tourLocation;
    }

    public String getDataContent() {
        return dataContent;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getPosx() {
        return posx;
    }

    public String getPosy(){
        return posy;
    }

}

