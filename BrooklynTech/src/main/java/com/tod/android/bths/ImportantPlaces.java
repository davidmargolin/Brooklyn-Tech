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


public class ImportantPlaces extends SwipeBackActivity {
    private SwipeBackLayout mSwipeBackLayout;
    ScalpelFrameLayout scalpelView;
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
        setContentView(R.layout.important_places);
        getActionBar().setTitle("Important Places");
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        if (devmode){
            scalpelView = (ScalpelFrameLayout)findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        ImportantPlaces.this.finish();
        return true;

    }
}