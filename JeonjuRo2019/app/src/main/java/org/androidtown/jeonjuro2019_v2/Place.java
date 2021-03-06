package org.androidtown.jeonjuro2019_v2;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable{
    private  int type; //1 - 맛집 , 2 - 숙소 , 3 - 문화재
    private  String name;
    private String imgno;
    private String posx;
    private String posy;

    public Place (int type,String name, String imgno, String posx, String posy) {
        this.type = type;
        this.name = name;
        this.imgno = imgno;
        this.posx = posx;
        this.posy = posy;
    }

    public Place(Parcel in) {
        this.type = in.readInt();
        this.name = in.readString();
        this.imgno = in.readString();
        this.posx = in.readString();
        this.posy = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeString(this.imgno);
        dest.writeString(this.posx);
        dest.writeString(this.posy);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            // TODO Auto-generated method stub
            return new Place[size];
        }

    };

    public int getType() {return type;}

    public String getPosx() {return posx;}

    public String getPosy() {return posy;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagno() {
        return imgno;
    }

    public void setImgno(String imgno) {
        this.imgno = imgno;
    }
}