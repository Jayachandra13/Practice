package com.jc.practice.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FOTL on 4/15/2016.
 */
public class Employee implements Parcelable {
    int id;
    String name;
    String designation;
    int age;
    boolean isMale;
    String[] projects;
    Address address;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setIsMale(boolean isMale) {
        this.isMale = isMale;
    }

    public String[] getProjects() {
        return projects;
    }

    public void setProjects(String[] projects) {
        this.projects = projects;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.designation);
        dest.writeInt(this.age);
        dest.writeByte(isMale ? (byte) 1 : (byte) 0);
        dest.writeStringArray(this.projects);
        dest.writeParcelable(this.address, flags);
    }

    public Employee() {
    }

    protected Employee(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.designation = in.readString();
        this.age = in.readInt();
        this.isMale = in.readByte() != 0;
        this.projects = in.createStringArray();
        this.address = in.readParcelable(Address.class.getClassLoader());
    }

    public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
}
