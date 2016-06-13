package com.jc.practice;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.jc.practice.adapter.LoaderRcvAdapter;
import com.jc.practice.model.ContactDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyLoaderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.rcvLoader)
    RecyclerView rcvLoader;
    ArrayList<ContactDetails> contactsDetailsList;
    LoaderRcvAdapter loaderRcvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_loader);
        ButterKnife.bind(this);
        rcvLoader.hasFixedSize();
        rcvLoader.setLayoutManager(new LinearLayoutManager(MyLoaderActivity.this));
        new MyAsyncTask().execute();
        //getSupportLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(MyLoaderActivity.this, ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            contactsDetailsList = new ArrayList<>();
            loaderRcvAdapter = new LoaderRcvAdapter(MyLoaderActivity.this, contactsDetailsList);
            rcvLoader.setAdapter(loaderRcvAdapter);
        }

        @Override
        protected Void doInBackground(Void... params) {
            getContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Collections.sort(contactsDetailsList, new Comparator<ContactDetails>() {
                @Override
                public int compare(ContactDetails lhs, ContactDetails rhs) {
                    return lhs.getName().compareToIgnoreCase(rhs.getName());
                }
            });
            loaderRcvAdapter.notifyDataSetChanged();
        }
    }

    public void getContacts() {
        Cursor mCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                ContactDetails contactDetails = new ContactDetails();
                String mContact_Id = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
                String mName = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)).trim();
                contactDetails.setName(mName);
                String has_phone_no = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (has_phone_no.equals("1")) {
                    Cursor mPhoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{mContact_Id}, null);
                    if (mPhoneCursor.getCount() > 0) {
                        ArrayList<String> nos = new ArrayList<>();
                        while (mPhoneCursor.moveToNext()) {
                            String mNo = mPhoneCursor.getString(mPhoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim().replaceAll("[ ,-]", "").replace("+91", "");
                            if (!nos.contains(mNo)) {
                                nos.add(mNo);
                            }
                        }
                        contactDetails.setNo(nos);
                    }
                    mPhoneCursor.close();
                }
                Cursor mEmailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[]{mContact_Id}, null);
                if (mEmailCursor.getCount() > 0) {
                    ArrayList<String> emails = new ArrayList<>();
                    while (mEmailCursor.moveToNext()) {
                        String mEmail = mEmailCursor.getString(mEmailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)).trim();
                        if (!emails.contains(mEmail)) {
                            emails.add(mEmail);
                        }
                    }
                    contactDetails.setEmail(emails);
                }
                mEmailCursor.close();
                Uri personUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(mContact_Id));
                Uri photoUri = Uri.withAppendedPath(personUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                contactDetails.setUri(photoUri);
                contactsDetailsList.add(contactDetails);
            }
        }
        mCursor.close();
        /*// Print in LOG
        Collections.sort(contactsDetailsList, new Comparator<ContactDetails>() {
            @Override
            public int compare(ContactDetails lhs, ContactDetails rhs) {
                return lhs.getName().compareTo(rhs.getName());
            }
        });
        for (int i = 0; i < contactsDetailsList.size(); i++) {
            String name = contactsDetailsList.get(i).getName();
            String no = "NA";
            if (contactsDetailsList.get(i).getNo() != null) {
                no = contactsDetailsList.get(i).getNo().toString();
            }
            String email = "NA";
            if (contactsDetailsList.get(i).getEmail() != null) {
                email = contactsDetailsList.get(i).getEmail().toString();
            }
            String uri = "NA";
            if (contactsDetailsList.get(i).getUri() != null) {
                uri = contactsDetailsList.get(i).getUri().toString();
            }

            Log.v("res:" + i, name + "#" + no + "#" + email + "#" + uri);
        }*/
    }
}
