package com.jc.practice.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FOTL on 4/15/2016.
 */
public class Address implements Parcelable {
    String dNo;
    String street;
    String landMark;
    String city;
    String pinCode;
    PhoneNumber phoneNumber;

    public String getdNo() {
        return dNo;
    }

    public void setdNo(String dNo) {
        this.dNo = dNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dNo);
        dest.writeString(this.street);
        dest.writeString(this.landMark);
        dest.writeString(this.city);
        dest.writeString(this.pinCode);
        dest.writeParcelable(this.phoneNumber, flags);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.dNo = in.readString();
        this.street = in.readString();
        this.landMark = in.readString();
        this.city = in.readString();
        this.pinCode = in.readString();
        this.phoneNumber = in.readParcelable(PhoneNumber.class.getClassLoader());
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
