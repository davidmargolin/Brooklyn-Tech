package com.tod.android.bths;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
 * Created by Margolin on 6/24/2014.
 */
public class FinalAnnouncements extends SwipeBackActivity {
    String department;
    Document subdepdoc;
    Elements subdepartments;
    ProgressBar progress;
    Async task;
    String deplink;
    SwipeBackLayout mSwipeBackLayout;
    ListView subdepartmentlist;
    ArrayList<String> b = new ArrayList<String>();
    ArrayList<String> c = new ArrayList<String>();
    Boolean browserview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        browserview = sharedPreferences.getBoolean("openinbrowser",false);
        String theme = sharedPreferences.getString("themepref", "LIGHT");
        if (theme.contains("DARK")){
            setTheme(R.style.MyTheme);
        }else{
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.staff_fragment_layout);
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        department = bundle.getString("Dep");
        deplink = bundle.getString("Link");
        progress = (ProgressBar)findViewById(R.id.progressBar1);
        subdepartmentlist = (ListView)findViewById(R.id.lvDepartments);
        getActionBar().setTitle(department);
        task = new Async();
        task.execute();
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }

    private class Async extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                subdepdoc = Jsoup.connect(deplink)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                        .get();
                //final Elements paragraphs = doc.select("p").prepend("\\n\\n");
                subdepartments = subdepdoc.select("li>a:contains(daily)");
                for (Element department : subdepartments) {
                    b.add(department.text().replaceAll("_"," ").replaceFirst("-"," "));
                    c.add(department.text());
                }
                Collections.reverse(b);
                Collections.reverse(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            subdepartmentlist.setVisibility(View.GONE);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);
            subdepartmentlist.setVisibility(View.VISIBLE);
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(getBaseContext(),R.layout.singletextcard, b);
            subdepartmentlist.setAdapter(arrayAdapter);

            subdepartmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String LinkTo= subdepdoc.select("li>a:contains("+ c.get(position) +")").first().attr("abs:href");
                    if (browserview){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/gview?embedded=true&url="+LinkTo));
                        startActivity(browserIntent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), AnnouncementViewer.class);
                        intent.putExtra("LinkTo", LinkTo);
                        intent.putExtra("Dep", b.get(position));
                        startActivity(intent);
                    }
                }
            });

        }
    }
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                FinalAnnouncements.this.finish();
                return super.onOptionsItemSelected(item);
        }
    }
}
