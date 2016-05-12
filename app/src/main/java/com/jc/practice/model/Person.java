package com.jc.practice.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("filepath")
    @Expose
    private String filepath;
    @SerializedName("dt")
    @Expose
    private String dt;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * @param fname The fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * @return The lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * @param lname The lname
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * @return The age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age The age
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return The filepath
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     * @param filepath The filepath
     */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * @return The dt
     */
    public String getDt() {
        return dt;
    }

    /**
     * @param dt The dt
     */
    public void setDt(String dt) {
        this.dt = dt;
    }
}
