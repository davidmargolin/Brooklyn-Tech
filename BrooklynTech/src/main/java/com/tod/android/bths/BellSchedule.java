package com.tod.android.bths;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.jakewharton.scalpel.ScalpelFrameLayout;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


public class BellSchedule extends SwipeBackActivity {
    ScalpelFrameLayout scalpelView;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString("themepref", "LIGHT");
        if (theme.contains("DARK")){
            setTheme(R.style.MyTheme);
        }else{
            setTheme(R.style.LightTheme);
        }
        Boolean devmode = sharedPreferences.getBoolean("devmode", false);
        setContentView(R.layout.bell_schedule);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (devmode){
            scalpelView = (ScalpelFrameLayout)findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        BellSchedule.this.finish();
        return true;

    }
    }
