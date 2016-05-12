package com.jc.practice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jc.practice.model.Address;
import com.jc.practice.model.Employee;
import com.jc.practice.model.PhoneNumber;
import com.jc.practice.model.RealmJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ParcelableActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etId)
    TextInputEditText etId;
    @Bind(R.id.etName)
    TextInputEditText etName;
    @Bind(R.id.etDesignation)
    TextInputEditText etDesignation;
    @Bind(R.id.etAge)
    TextInputEditText etAge;
    @Bind(R.id.rbMale)
    RadioButton rbMale;
    @Bind(R.id.rbFemale)
    RadioButton rbFemale;
    @Bind(R.id.rgGender)
    RadioGroup rgGender;
    @Bind(R.id.etProjects)
    TextInputEditText etProjects;
    @Bind(R.id.etDno)
    TextInputEditText etDno;
    @Bind(R.id.etStreet)
    TextInputEditText etStreet;
    @Bind(R.id.etLandMark)
    TextInputEditText etLandMark;
    @Bind(R.id.etCity)
    TextInputEditText etCity;
    @Bind(R.id.etPIN)
    TextInputEditText etPIN;
    @Bind(R.id.etMobile1)
    TextInputEditText etMobile1;
    @Bind(R.id.etMobile2)
    TextInputEditText etMobile2;
    @Bind(R.id.etLanLine)
    TextInputEditText etLanLine;
    @Bind(R.id.btnSave)
    Button btnSave;
    String id, name, designation, age, projects, dno, street, landMark, city, pinCode, mobile1, mobile2, lanLine = null;
    boolean isMale = true;
    Realm myRealm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        myRealm = Realm.getInstance(new RealmConfiguration.Builder(ParcelableActivity.this).name("myRealm.realm").deleteRealmIfMigrationNeeded().build());
        etLanLine.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    saveData();
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btnSave)
    public void onClick() {
        saveData();
    }

    public void saveData() {
        id = etId.getText().toString();
        name = etName.getText().toString();
        designation = etDesignation.getText().toString();
        age = etAge.getText().toString();
        projects = etProjects.getText().toString();
        dno = etDno.getText().toString();
        street = etStreet.getText().toString();
        landMark = etLandMark.getText().toString();
        city = etCity.getText().toString();
        pinCode = etPIN.getText().toString();
        mobile1 = etMobile1.getText().toString();
        mobile2 = etMobile2.getText().toString();
        lanLine = etLanLine.getText().toString();
        int rbId = rgGender.getCheckedRadioButtonId();
        if (rbId == R.id.rbMale) {
            isMale = true;
        } else {
            isMale = false;
        }
        List<Employee> list = new ArrayList<>();
        Employee employee = new Employee();
        if (validateInput()) {
            employee.setId(Integer.parseInt(id));
            employee.setName(name);
            employee.setDesignation(designation);
            employee.setAge(Integer.parseInt(age));
            employee.setIsMale(isMale);
            employee.setProjects(projects.split(","));
            Address address = new Address();
            address.setdNo(dno);
            address.setStreet(street);
            address.setLandMark(landMark);
            address.setCity(city);
            address.setPinCode(pinCode);
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setMobileNo1(mobile1);
            phoneNumber.setMobileNo2(mobile2);
            phoneNumber.setLanLine(lanLine);
            address.setPhoneNumber(phoneNumber);
            employee.setAddress(address);
            list.add(employee);
            String str_json = new Gson().toJson(list);
            Log.v("str_json", "" + str_json);
            myRealm.beginTransaction();
            RealmJson realmJson = myRealm.createObject(RealmJson.class);
            realmJson.setJsonString(str_json);
            myRealm.commitTransaction();
            Intent intent = new Intent(ParcelableActivity.this, GetParcelableActivity.class);
            intent.putExtra("employee", employee);
//            intent.putParcelableArrayListExtra("emList",list);
            startActivity(intent);
        }
    }

    boolean validateInput() {
        if (id.isEmpty()) {
            etId.requestFocus();
            etId.setError("Enter Id");
            return false;
        }
        if (name.isEmpty()) {
            etName.requestFocus();
            etName.setError("Enter Name");
            return false;
        }
        if (designation.isEmpty()) {
            etDesignation.requestFocus();
            etDesignation.setError("Enter Designation");
            return false;
        }
        if (age.isEmpty()) {
            etAge.requestFocus();
            etAge.setError("Enter Age");
            return false;
        }
        if (projects.isEmpty()) {
            etProjects.requestFocus();
            etProjects.setError("Enter Projects");
            return false;
        }
        if (dno.isEmpty()) {
            etDno.requestFocus();
            etDno.setError("Enter Dno");
            return false;
        }
        if (street.isEmpty()) {
            etStreet.requestFocus();
            etStreet.setError("Enter Street");
            return false;
        }
        if (landMark.isEmpty()) {
            etLandMark.requestFocus();
            etLandMark.setError("Enter land mark");
            return false;
        }
        if (city.isEmpty()) {
            etCity.requestFocus();
            etCity.setError("Enter City");
            return false;
        }
        if (pinCode.isEmpty()) {
            etPIN.requestFocus();
            etPIN.setError("Enter PIN");
            return false;
        }
        if (mobile1.isEmpty()) {
            etMobile1.requestFocus();
            etMobile1.setError("Enter PIN");
            return false;
        }
        if (mobile2.isEmpty()) {
            etMobile2.requestFocus();
            etMobile2.setError("Enter PIN");
            return false;
        }
        if (lanLine.isEmpty()) {
            etLanLine.requestFocus();
            etLanLine.setError("Enter PIN");
            return false;
        }
        return true;
    }
}
