package com.tod.android.bths;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.scalpel.ScalpelFrameLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Locale;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by Margolin on 10/27/13.
 */
public class ArticleViewer extends SwipeBackActivity implements TextToSpeech.OnInitListener{
    TextView article; // full article
    RelativeLayout browserview;
    Async task;
    String set;
    String title; // title
    String description; // description
    String website; // website
    Elements links;
    Document doc;// website doc
    Elements paragraphs;
    ScalpelFrameLayout scalpelView;
    private SwipeBackLayout mSwipeBackLayout;
    TextToSpeech tts; //tts implementation
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
        setContentView(R.layout.article_viewer_layout);
        tts = new TextToSpeech(this,this);
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        title = bundle.getString("Title");
        getActionBar().setTitle(title);
        description = bundle.getString("Desc");
        website = bundle.getString("Link");
        if (devmode){
            scalpelView = (ScalpelFrameLayout)findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }
        article = (TextView)findViewById(R.id.article);
        task = new Async();
        task.execute();
        browserview = (RelativeLayout)findViewById(R.id.browserview);
        browserview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intronet = new Intent(Intent.ACTION_VIEW);
                intronet.setData(Uri.parse(website));
                startActivity(intronet);
            }
        });


    }
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.US);
        }
    }
    @Override
    public void onDestroy() {
    try{
        tts.stop();
        tts.shutdown();
    }catch(Exception e){
        e.printStackTrace();
    }


        super.onDestroy();
    }
    public void onStop () {
        try{
        tts.shutdown();
        }catch(Exception e){
            e.printStackTrace();
        }
        super.onStop();
    }
    public void onListen(){
        if (tts.isSpeaking()){
            tts.stop();
        }else{
            tts.speak(article.getText().toString(), TextToSpeech.QUEUE_ADD,null );
        }
    }
    private class Async extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                doc = Jsoup.connect(website)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                        .get();
                doc.setBaseUri("http://www.bths.edu");
                for( Element element : doc.select("img") )
                {
                    element.remove();
                }
                for( Element element : doc.select("tbody") )
                {
                    element.remove();
                }
//                        paragraphs = doc.select("div#content p:gt(2)").prepend("\\n\\n");
                paragraphs = doc.select("div.sectionContent>*");
                set =  paragraphs.toString().replaceAll("\\\\n", "\n").trim();
                final String URL = "http://www.bths.edu";

                links = doc.select("a");
                for (Element i: links){
                    String checkUrl = i.attr("href");
                    String newUrl;
                    //So I've done this and now, I don't get an error. But nothing gets replacesd
                    if (checkUrl.startsWith("/")){
                        newUrl = URL + checkUrl;
                        set = set.replace("<a href=\""+checkUrl, "<a href=\"" + newUrl);
                    }
                    if (checkUrl.startsWith("http://")){
                        newUrl = checkUrl;
                        set = set.replace("<a href=\""+checkUrl, "<a href=\"" + newUrl);
                    }
                    if (checkUrl.startsWith("www")){
                        newUrl = "http://" + checkUrl;
                        set = set.replace("<a href=\""+checkUrl, "<a href=\"" + newUrl);
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            article.setText("Loading...");
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            article.setText(Html.fromHtml(set.replaceAll("color=\"#000000\"","").replaceAll("color: rgb(34, 34, 34); ", "")));
            article.setMovementMethod(LinkMovementMethod.getInstance());


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.article_actions, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.action_listen:
                onListen();
                return true;
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, website);
                Intent chooser = Intent.createChooser(intent, "Share");
                startActivity(chooser);
                return true;
            default:
                ArticleViewer.this.finish();
                return super.onOptionsItemSelected(item);
        }
    }
}
