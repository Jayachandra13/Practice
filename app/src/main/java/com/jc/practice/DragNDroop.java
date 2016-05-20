package com.jc.practice;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DragNDroop extends AppCompatActivity {

    @Bind(R.id.imgA)
    ImageView imgA;
    @Bind(R.id.imgB)
    ImageView imgB;
    @Bind(R.id.imgC)
    ImageView imgC;
    @Bind(R.id.llA)
    LinearLayout llA;
    @Bind(R.id.llB)
    LinearLayout llB;
    private int _xDelta;
    private int _yDelta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_ndroop);
        ButterKnife.bind(this);
        llA.setOnDragListener(myOnDragListener);
        llB.setOnDragListener(myOnDragListener);
        imgA.setOnLongClickListener(myOnLongClickListener);
        imgB.setOnLongClickListener(myOnLongClickListener);
        imgC.setOnLongClickListener(myOnLongClickListener);
        Log.v("onCreate","onCreate");

        /*imgA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        LinearLayout.LayoutParams lParams = (LinearLayout.LayoutParams) v.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)v.getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        v.setLayoutParams(layoutParams);
                        break;
                }
                llA.invalidate();
                return true;
            }
        });*/
    }

    View.OnLongClickListener myOnLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
           // v.setVisibility(View.INVISIBLE);
            return true;
        }

    };

    View.OnDragListener myOnDragListener = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {

            String area;
            if (v == llA) {
                area = "area1";
            } else if (v == llB) {
                area = "area2";
            } else {
                area = "unknown";
            }

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    LinearLayout oldParent = (LinearLayout) view.getParent();
                    oldParent.removeView(view);
                    LinearLayout newParent = (LinearLayout) v;
                    newParent.addView(view);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }
            return true;
        }

    };
}

