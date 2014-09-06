package com.tod.android.bths;

import android.app.ActionBar;
import android.app.Activity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.MenuItem;
import android.view.View;

import android.widget.ListView;
import android.widget.ProgressBar;


import com.jakewharton.scalpel.ScalpelFrameLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Margolin on 11/23/13.
 */
public class FinalStaff extends SwipeBackActivity {
    String subdeplink;
    String item;
    ListView staffmemberlist;
    String subdepartment;
    Document staffdoc;
    ProgressBar progress;
    Async task;
    Elements staff;
    String website;
    private SwipeBackLayout mSwipeBackLayout;
    ScalpelFrameLayout scalpelView;
    ArrayList<String> d = new ArrayList<String>();
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
        setContentView(R.layout.staff_fragment_layout);
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        progress = (ProgressBar)findViewById(R.id.progressBar1);
        subdeplink = bundle.getString("Link").replaceFirst("m","apps")
                .replace("departments", "pages").replace("show_department", "index").replace("/window.location='/m/pages", "").replace("'","").replaceAll("\n","");
        subdepartment = bundle.getString("SubDep");
        staffmemberlist = (ListView)findViewById(R.id.lvDepartments);
        getActionBar().setTitle(subdepartment);
        if (subdeplink.contains("none")){
            website = "http://bths.edu/apps/staff/";
        }else{
            website = subdeplink +"&termREC_ID=&pREC_ID=staff";
        }
        task = new Async();
        task.execute();
        if (devmode){
        scalpelView = (ScalpelFrameLayout)findViewById(R.id.scalpel);
        scalpelView.setLayerInteractionEnabled(true);
        scalpelView.setDrawViews(true);
        }
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

    }

    private class Async extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                staffdoc = Jsoup.connect(website)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                        .get();
                //final Elements paragraphs = doc.select("p").prepend("\\n\\n");
                staff = staffdoc.select("td > a");

                for (Element person : staff) {
                    item = person.text().trim();
                    if (!item.contains("Send")){
                        d.add(person.text());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            staffmemberlist.setVisibility(View.GONE);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);
            staffmemberlist.setVisibility(View.VISIBLE);
            MySimpleArrayAdapter customAdapter = new MySimpleArrayAdapter(getBaseContext() , d, staffdoc);
            staffmemberlist.setAdapter(customAdapter);
    }
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                FinalStaff.this.finish();
                return super.onOptionsItemSelected(item);
        }
    }

}
