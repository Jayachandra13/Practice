package com.jc.practice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jc.practice.arcmenu.ArcMenu;
import com.jc.practice.arcmenu.RayMenu;

public class ArcMenuActivity extends AppCompatActivity {
    int[] ITEM_DRAWABLES = {R.drawable.composer_camera, R.drawable.composer_music,
            R.drawable.composer_place, R.drawable.composer_sleep, R.drawable.composer_thought, R.drawable.composer_with};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);
        ArcMenu arcMenu2 = (ArcMenu) findViewById(R.id.arc_menu_2);

        initArcMenu(arcMenu, ITEM_DRAWABLES);
        initArcMenu(arcMenu2, ITEM_DRAWABLES);
        RayMenu rayMenu = (RayMenu) findViewById(R.id.ray_menu);
        final int itemCount = ITEM_DRAWABLES.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(ITEM_DRAWABLES[i]);

            final int position = i;
            assert rayMenu != null;
            rayMenu.addItem(item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ArcMenuActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });// Add a menu item
        }
    }
    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(this);
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ArcMenuActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
