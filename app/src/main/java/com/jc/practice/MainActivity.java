package com.jc.practice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.kingfisherphuoc.quickactiondialog.AlignmentFlag;
import com.kingfisherphuoc.quickactiondialog.QuickActionDialogFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btnParcelable)
    Button btnParcelable;
    @Bind(R.id.btnEndLessRcv)
    Button btnEndLessRcv;
    @Bind(R.id.btnEndLessRcvWithVolley)
    Button btnEndLessRcvWithVolley;
    @Bind(R.id.btnRetrofit)
    Button btnRetrofit;
    @Bind(R.id.btnQuickActionDialog)
    Button btnQuickActionDialog;
    @Bind(R.id.btnDragnDroop)
    Button btnDragnDroop;
    MySampleDialogFragment mySampleDialogFragment;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @OnClick({R.id.btnParcelable, R.id.btnEndLessRcv, R.id.btnEndLessRcvWithVolley, R.id.btnRetrofit, R.id.btnQuickActionDialog,R.id.btnDragnDroop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnParcelable:
                startActivity(new Intent(getApplicationContext(), ParcelableActivity.class));
                break;
            case R.id.btnEndLessRcv:
                startActivity(new Intent(getApplicationContext(), EndLessRcvActivity.class));
                break;
            case R.id.btnEndLessRcvWithVolley:
                startActivity(new Intent(getApplicationContext(), EndLessRcvWithVolley.class));
                break;
            case R.id.btnRetrofit:
                startActivity(new Intent(getApplicationContext(), RetrofitActivity.class));
                break;
            case R.id.btnQuickActionDialog:
                mySampleDialogFragment = new MySampleDialogFragment();
                mySampleDialogFragment.setAnchorView(btnQuickActionDialog);
                mySampleDialogFragment.setAligmentFlags(AlignmentFlag.ALIGN_ANCHOR_VIEW_LEFT | AlignmentFlag.ALIGN_ANCHOR_VIEW_BOTTOM);
                mySampleDialogFragment.show(getSupportFragmentManager(), null);
                break;
            case R.id.btnDragnDroop:
                startActivity(new Intent(MainActivity.this,DragNDroop.class));
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://practice.jc.com/main"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jc.practice/http/practice.jc.com/main")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://practice.jc.com/main"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.jc.practice/http/practice.jc.com/main")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public static class MySampleDialogFragment extends QuickActionDialogFragment {

        @Override
        protected int getArrowImageViewId() {
            return R.id.ivArrow;
        }

        @Override
        protected int getLayout() {
            return R.layout.dialog_sample_view;
        }

        @Override
        protected boolean isStatusBarVisible() {
            return true;
        }


        @Override
        protected boolean isCanceledOnTouchOutside() {
            return true;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.findViewById(R.id.btnSample).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Button inside Dialog!!", Toast.LENGTH_SHORT).show();
                }
            });


            return view;

        }
    }
}
