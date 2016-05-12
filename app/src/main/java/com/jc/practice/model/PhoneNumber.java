package com.jc.practice.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FOTL on 4/15/2016.
 */
public class PhoneNumber implements Parcelable {
    String mobileNo1;
    String mobileNo2;
    String lanLine;

    public String getMobileNo1() {
        return mobileNo1;
    }

    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }

    public String getLanLine() {
        return lanLine;
    }

    public void setLanLine(String lanLine) {
        this.lanLine = lanLine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mobileNo1);
        dest.writeString(this.mobileNo2);
        dest.writeString(this.lanLine);
    }

    public PhoneNumber() {
    }

    protected PhoneNumber(Parcel in) {
        this.mobileNo1 = in.readString();
        this.mobileNo2 = in.readString();
        this.lanLine = in.readString();
    }

    public static final Parcelable.Creator<PhoneNumber> CREATOR = new Parcelable.Creator<PhoneNumber>() {
        @Override
        public PhoneNumber createFromParcel(Parcel source) {
            return new PhoneNumber(source);
        }

        @Override
        public PhoneNumber[] newArray(int size) {
            return new PhoneNumber[size];
        }
    };
}
