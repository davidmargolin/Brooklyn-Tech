package com.tod.android.bths;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;


public class WebViewActivity extends Activity {
    WebView webview;
    EditText name;
    EditText email;
    EditText subject;
    EditText body;
    String url;
    AlertDialog.Builder dlgAlert;
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
        setContentView(R.layout.webview);
        Bundle bundle = getIntent().getExtras();
        getActionBar().setTitle(bundle.getString("TITLE"));
        url = bundle.getString("LINK");
        name = (EditText)findViewById(R.id.editText);
        email = (EditText)findViewById(R.id.editText2);
        dlgAlert= new AlertDialog.Builder(this);
        subject= (EditText)findViewById(R.id.editText3);
        body = (EditText)findViewById(R.id.editText4);
        webview = (WebView)findViewById(R.id.web);
        webview.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.55 Safari/537.36");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setInitialScale(1);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.loadUrl(url);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
/*        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_refresh).setVisible(!drawerOpen && test);*/
        MenuItem send = menu.findItem(R.id.action_send);
        if(webview.getVisibility()==View.VISIBLE)
        {
            send.setVisible(false);
        }
        else
        {
            send.setVisible(true);
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.mailmain, menu);
    return true;}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_send:
                send();
                invalidateOptionsMenu();
                ActionBar actionBar = getActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                return true;
            default:
                WebViewActivity.this.finish();
                return true;
        }
    }
        public void send(){
            if (name.getText().toString().length()<1){
                dlgAlert.setMessage("Please enter a name.");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.create().show();
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

            }else if (email.getText().length()<7|| !email.getText().toString().contains("@")||!email.getText().toString().contains(".")){
                dlgAlert.setMessage("Email Incorrect.");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.create().show();
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }              else  if (subject.getText().toString().length()<1){
                dlgAlert.setMessage("No Subject Text.");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.create().show();
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }               else if (body.getText().toString().length()<1){
                dlgAlert.setMessage("No Body Text.");
                dlgAlert.setTitle("Error");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.create().show();
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }else{
                webview.setVisibility(View.VISIBLE);
                webview.loadUrl("javascript: document.getElementById('emailForm_name').value ='"+name.getText()+"'");
                webview.loadUrl("javascript: document.getElementById('emailForm_email').value ='"+email.getText()+"'");
                webview.loadUrl("javascript: document.getElementById('emailForm_subject').value ='"+subject.getText()+"'");
                webview.loadUrl("javascript: document.getElementById('emailForm_message').value ='"+body.getText()+"'");
                dlgAlert.setMessage("Please enter a verification code and click submit.");
                dlgAlert.setTitle("Verification");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.create().show();
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
            }
        }
    @Override
    public void onBackPressed() {
        if (webview.getVisibility()== View.VISIBLE){
            webview.setVisibility(View.INVISIBLE);
            webview.loadUrl(url);
            invalidateOptionsMenu();
        }else{
            WebViewActivity.this.finish();
        }
    }
        }
