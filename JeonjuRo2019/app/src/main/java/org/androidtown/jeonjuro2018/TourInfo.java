package org.androidtown.jeonjuro2018;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;

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

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourLocation() {
        return tourLocation;
    }

    public void setTourLocation(String tourLocation) {
        this.tourLocation = tourLocation;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getPosx() {
        return posx;
    }
    public void setPosx(String posx){
        this.posx = posx;
    }

    public String getPosy(){
        return posy;
    }
    public void setPosy(String posy){
        this.posy = posy;
    }
}

