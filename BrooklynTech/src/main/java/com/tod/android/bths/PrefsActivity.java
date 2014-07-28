package com.tod.android.bths;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

/**
 * Created by Margolin on 12/28/13.
 */
public class PrefsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();
    ActionBar actionBar = getActionBar();
    actionBar.setDisplayHomeAsUpEnabled(true);

}
    public boolean onOptionsItemSelected(MenuItem item){
        PrefsActivity.this.finish();
        return true;

}
}