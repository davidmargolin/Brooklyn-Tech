package com.tod.android.bths;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.analytics.tracking.android.EasyTracker;

/**
 * Created by Margolin on 8/5/2014.
 */
public class NavActivity extends FragmentActivity
        implements DrawerFragment.NavigationDrawerCallbacks {
    static long back_pressed;
    FragmentManager fragmentManager;
    static TextView contenttext;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private DrawerFragment mNavigationDrawerFragment;
    private static String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString("themepref", "LIGHT");
        String page = sharedPreferences.getString("pagepref", "news");
        if (theme.contains("DARK")){
            setTheme(R.style.MyTheme);
        }else{
            setTheme(R.style.LightTheme);
        }
        super.onCreate(savedInstanceState);
        fragmentManager =getSupportFragmentManager();
        setContentView(R.layout.nav_layout);
        contenttext = (TextView) findViewById(R.id.contenttitle);
        getActionBar().setTitle(Html.fromHtml("Brooklyn<b>Tech</b> "));
        mNavigationDrawerFragment = (DrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle().toString();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        switch (position) {
            case 0:
                // Creating a fragment object
                NewsViewPager aFragment = new NewsViewPager();
                mTitle = "General News";
                // Creating a Bundle object
                //Bundle data = new Bundle();
/*                Bundle data=new Bundle();
                data.putString("link", "http://rsscleaner.appspot.com/feed?feedUrl=http://bths.edu/apps/news/news_rss.jsp?id=0");
                data.putString("title", mTitle);
                aFragment.setArguments(data);*/
                // Setting the position to the fragment
                //rFragment.setArguments(data);
                // Getting reference to the FragmentManager
                fragmentManager = getSupportFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, aFragment);
                // Committing the transaction
                ft.commit();
                break;
            case 1:
                hidecontentbar(false);
                mTitle = "Links";
                LinkFragment bFragment = new LinkFragment();

/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", position);

					// Setting the position to the fragment
					rFragment.setArguments(data);*/
                // Getting reference to the FragmentManager
                fragmentManager = getSupportFragmentManager();

                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, bFragment);

                // Committing the transaction
                ft.commit();

                break;
            case 2:
                hidecontentbar(false);
                mTitle = "Staff Directory";
                StaffFragment cFragment = new StaffFragment();
/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", posit
					// Setting the position to the fragment
					rFragment.setArguments(data);*/

                // Getting reference to the FragmentManager
                fragmentManager = getSupportFragmentManager();

                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, cFragment);

                // Committing the transaction
                ft.commit();

                break;
            case 3:
                hidecontentbar(false);
                mTitle = "Calendar";
                CalendarFragment dFragment = new CalendarFragment();

/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", position);

					// Setting the position to the fragment
					rFragment.setArguments(data);*/
                // Getting reference to the FragmentManager
                fragmentManager = getSupportFragmentManager();

                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, dFragment);

                // Committing the transaction
                ft.commit();

                break;
            case 4:
                hidecontentbar(false);
                mTitle = "Other Information";
                InfoFragment fFragment = new InfoFragment();
/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", position);

					// Setting the position to the fragment
					rFragment.setArguments(data);*/

                // Getting reference to the FragmentManager
                fragmentManager = getSupportFragmentManager();

                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, fFragment);

                // Committing the transaction
                ft.commit();

                break;
            case 5:
                hidecontentbar(false);
                mTitle = "Settings";

                // Creating a fragment object
                SettingsFragment hFragment = new SettingsFragment();

                Bundle data4=new Bundle();
                data4.putString("title", mTitle);
                hFragment.setArguments(data4);
                // Getting reference to the FragmentManager
                fragmentManager = getSupportFragmentManager();

                // Creating a fragment transaction
                ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.container, hFragment);
// Closing the drawer
                ft.commit();
                break;
        }
    }

/*    void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);

                break;
        }
    }*/
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);  // Add this method.
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    public void restoreActionBar() {
        contenttext.setText(mTitle);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);

    }
    public static void hidecontentbar(Boolean hide){
        if (hide) {
            contenttext.setVisibility(View.GONE);
        }else{
            contenttext.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
            if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
            else
                Toast.makeText(getBaseContext(), "Press back again to exit!", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
            // or just go back to main activity
    }
}
