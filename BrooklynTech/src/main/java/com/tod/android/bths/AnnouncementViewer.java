package com.tod.android.bths;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.webkit.WebView;

import com.jakewharton.scalpel.ScalpelFrameLayout;

/**
 * Created by Margolin on 6/25/2014.
 */
public class AnnouncementViewer extends Activity {
    ScalpelFrameLayout scalpelView;
    String date;
    String LinkTo;

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
        setContentView(R.layout.simple_webview);
        Bundle bundle = getIntent().getExtras();
        date = bundle.getString("Dep");
        LinkTo= bundle.getString("LinkTo");
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(date);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (devmode){
            scalpelView = (ScalpelFrameLayout)findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }

        WebView pdf = (WebView)findViewById(R.id.pdfview);
        pdf.getSettings().setJavaScriptEnabled(true);
        pdf.loadUrl("https://docs.google.com/gview?embedded=true&url="+LinkTo);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        AnnouncementViewer.this.finish();
        return true;

    }
}
