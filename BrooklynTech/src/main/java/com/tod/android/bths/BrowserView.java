package com.tod.android.bths;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.jakewharton.scalpel.ScalpelFrameLayout;

/**
 * Created by Margolin on 1/12/14.
 */
public class BrowserView extends Activity{
    String url;
    WebView web;
    ProgressBar loadbar;
    ScalpelFrameLayout scalpelView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    String theme = sharedPreferences.getString("themepref", "LIGHT");
    Boolean devmode = sharedPreferences.getBoolean("devmode", false);
    if (theme.contains("DARK")){
        setTheme(R.style.MyTheme);
    }else{
        setTheme(R.style.LightTheme);
    }
    setContentView(R.layout.browserlayout);
        loadbar = (ProgressBar)findViewById(R.id.progressBar2);
    web = (WebView)findViewById(R.id.browserlayout);
        //progressBar=(ProgressBar)findViewById(R.id.progress);
    Bundle bundle = getIntent().getExtras();
    getActionBar().setTitle(bundle.getString("TITLE"));
    url = bundle.getString("LINK");
        web.setWebViewClient(new AppWebViewClients());
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setDisplayZoomControls(false);
        web.getSettings().setLoadWithOverviewMode(true);
        web.loadUrl(url);
        loadbar.setVisibility(View.VISIBLE);
        loadbar.setIndeterminate(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        if (devmode){
            scalpelView = (ScalpelFrameLayout)findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }
    }
    public class AppWebViewClients extends WebViewClient {

        public AppWebViewClients() {
            //this.progressBar=progressBar;
            //progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            loadbar.setVisibility(View.VISIBLE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            if (loadbar.isShown()) {
                loadbar.setVisibility(View.GONE);
            }
            super.onPageFinished(view, url);
            //progressBar.setVisibility(View.GONE);
        }
    }
    @Override
    public void onBackPressed() {
            BrowserView.this.finish();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.webmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.action_back:
                if (web.canGoBack()){
                web.goBack();
                    loadbar.setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.action_forward:
                if (web.canGoForward()){
                web.goForward();
                    loadbar.setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.action_reload:
                web.reload();
                loadbar.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_home:
                web.loadUrl(url);
                loadbar.setVisibility(View.VISIBLE);
                return true;
            case R.id.browser:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(web.getUrl()));
                startActivity(intent);
            default:
                BrowserView.this.finish();
                return super.onOptionsItemSelected(item);
        }
    }
    }

