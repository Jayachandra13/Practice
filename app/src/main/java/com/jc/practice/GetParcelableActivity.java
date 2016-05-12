package com.jc.practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jc.practice.model.Employee;
import com.jc.practice.model.RealmJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class GetParcelableActivity extends AppCompatActivity {
    private Realm myRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_parcelable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getDataFromParcelableIntent();
        getDataFromRealm();
    }

    public void getDataFromParcelableIntent() {
        Employee employee = getIntent().getParcelableExtra("employee");
        String emp = employee.getId() + "\n" + employee.getName() + "\n" + employee.getDesignation() + "\n" + employee.getAge() + "\n" + employee.getProjects().toString() + "\n" + employee.getAddress().getdNo() + "\n" + employee.getAddress().getStreet() + "\n" + employee.getAddress().getLandMark() + "\n" + employee.getAddress().getCity() + "\n" + employee.getAddress().getPinCode() + "\n" + employee.getAddress().getPhoneNumber().getMobileNo1() + "\n" + employee.getAddress().getPhoneNumber().getMobileNo2() + "\n" + employee.getAddress().getPhoneNumber().getLanLine();
        Log.v("empParcelable", "" + emp);
    }
    public void getDataFromRealm(){
        myRealm = Realm.getInstance(new RealmConfiguration.Builder(GetParcelableActivity.this).name("myRealm.realm").deleteRealmIfMigrationNeeded().build());
        RealmResults<RealmJson> realmResults = myRealm.where(RealmJson.class).findAll();
       //General method from JSON to POJO
       //List<Employee> employeeList=new ArrayList<>();
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        for (RealmJson realm: realmResults){
            jsonArray.put(realm.getJsonString());
        }
        try {
            jsonObject.put("employees",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v("employees",""+jsonObject);
        /*for (RealmJson realm: realmResults){
            String srt_json=realm.getJsonString();
            Log.v("realm_srt_json",""+srt_json+"\n");
            *//*JSONObject jsonObject = null;
            try {
                jsonObject=new JSONObject(srt_json);
            } catch (JSONException e) {
                e.printStackTrace();
            }*//*
            *//*try {
                Employee employee=new Gson().fromJson(srt_json,Employee.class);
                employeeList.add(employee);
            } catch (Exception e) {
                e.printStackTrace();
            }*//*
        }*/
        /*Gson gson=new Gson();
        Type typeToken=new TypeToken<List<RealmJson>>(){}.getType();*/
        //List<RealmJson> employeeList1=gson.fromJson(realmResults.toString(), typeToken);
        //Log.v("employeeList1",""+employeeList1);



    }
}
