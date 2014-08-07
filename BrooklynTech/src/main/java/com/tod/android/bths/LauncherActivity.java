package com.tod.android.bths;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Margolin on 1/3/14.
 */
public class LauncherActivity extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, NavActivity.class);
        startActivity(intent);
        finish();
    }
}
