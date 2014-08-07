package com.tod.android.bths;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Margolin on 10/31/13.
 */
public class Announcements extends SwipeBackActivity {
    Document depdoc;
    Elements departments;
    ListView departmentlist;
    ProgressBar progress;
    SwipeBackLayout mSwipeBackLayout;
    Async task;
    ArrayList<String> a = new ArrayList<String>();
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
        setContentView(R.layout.staff_fragment_layout);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Announcements");
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        departmentlist= (ListView)findViewById(R.id.lvDepartments);
        progress = (ProgressBar)findViewById(R.id.progressBar1);
        task = new Async();
        task.execute();

    }
    private class Async extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                depdoc = Jsoup.connect("http://www.bths.edu/dailyannounce.jsp")
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                        .get();
                //final Elements paragraphs = doc.select("p").prepend("\\n\\n");
                departments = depdoc.select("p>a");
                for (Element department : departments) {
                    a.add(department.text());
                }
                Collections.reverse(a);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            departmentlist.setVisibility(View.GONE);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);
            departmentlist.setVisibility(View.VISIBLE);
            try{
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(getBaseContext(),R.layout.singletextcard, a);
                departmentlist.setAdapter(arrayAdapter);
                departmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String deplink= depdoc.select("p>a:contains("+ a.get(position) +")").first().attr("abs:href");
                        Intent intent = new Intent(getApplicationContext(), FinalAnnouncements.class);
                        intent.putExtra("Link", deplink);
                        intent.putExtra("Dep", a.get(position));
                        startActivity(intent);
                    }
                });
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                Announcements.this.finish();
                return super.onOptionsItemSelected(item);
        }
    }
}
