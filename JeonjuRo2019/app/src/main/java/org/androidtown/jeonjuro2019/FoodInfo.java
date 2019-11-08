package org.androidtown.jeonjuro2019;

import android.os.Parcel;
import android.os.Parcelable;

public class FoodInfo implements Parcelable {
    public String storeImg;
    public String storeName;
    public String storeAddr;
    public String Menu;
    public String storeOpen;
    public String holiday;
    public String openTime;
    public String posx;
    public String posy;

    public FoodInfo(String storeImg, String storeName, String storeAddr, String Menu, String storeOpen, String holiday, String openTime) {
        this.storeImg = storeImg;
        this.storeAddr = storeAddr;
        this.Menu = Menu;
        this.storeName = storeName;
        this.storeOpen = storeOpen;
        this.holiday = holiday;
        this.openTime = openTime;
    }

    public FoodInfo(String storeName, String storeImg, String posx, String posy){
        this.storeName = storeName;
        this.storeImg = storeImg;
        this.posx = posx;
        this.posy = posy;
    }

    public FoodInfo(Parcel in) {
        this.storeName = in.readString();
        this.storeImg = in.readString();
        this.posx = in.readString();
        this.posy = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.storeName);
        dest.writeString(this.storeImg);
        dest.writeString(this.posx);
        dest.writeString(this.posy);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public FoodInfo createFromParcel(Parcel in) {
            return new FoodInfo(in);
        }

        @Override
        public FoodInfo[] newArray(int size) {
            // TODO Auto-generated method stub
            return new FoodInfo[size];
        }

    };

    public String getStoreImg() {return storeImg;}

    public void setStoreImg() {this.storeImg = storeImg;}

    public String getStoreName() {return storeName;}

    public void setStoreName() {this.storeName = storeName;}

    public String getStoreAddr() {return storeAddr;}

    public void setStoreAddr() {this.storeAddr = storeAddr;}

    public String getMenu() {return Menu;}

    public void setMenu() {this.Menu = Menu;}

    public String getstoreOpen() {return storeOpen;}

    public void setstoreOpen() {this.storeOpen = storeOpen;}

    public String getholiday() {return storeOpen;}

    public void setholiday() {this.holiday = holiday;}

    public String getopenTime() {return openTime;}

    public void setopenTime() {this.openTime = openTime;}

    public String getPosx(){
        return posx;
    }
    public void setPosx(){
        this.posx = posx;
    }
    public String getPosy(){
        return posy;
    }
    public void setPosy(){
        this.posy=posy;
    }
}