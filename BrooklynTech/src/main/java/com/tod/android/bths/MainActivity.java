package com.tod.android.bths;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.scalpel.ScalpelFrameLayout;


public class MainActivity extends Activity {
    private static long back_pressed;

    // Within which the entire activity is enclosed
    DrawerLayout mDrawerLayout;

    // ListView represents Navigation Drawer
    LinearLayout mDrawerItem1;
    LinearLayout mDrawerItem2;
    LinearLayout mDrawerItem3;
    LinearLayout mDrawerItem4;
    LinearLayout mDrawerItem5;
    TextView contenttext;
    LinearLayout mDrawerItem6;
    LinearLayout ChosenLayout;
    LinearLayout mDrawerItem9;
/*    View divider1;
    View divider2;
    View divider3;
    View divider4;
    View divider5;
    View divider6;
    View divider7;
    View divider8;
    View divider9;*/
    LinearLayout news;
    LinearLayout mDrawerItem8;
    ScrollView mDrawerList;
    int drawertogglestyle;
    Boolean newfragopen=true;

    // ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
    ActionBarDrawerToggle mDrawerToggle;
    // Title of the action bar
    String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString("themepref", "LIGHT");
        String page = sharedPreferences.getString("pagepref", "news");
        Boolean themeswitch = sharedPreferences.getBoolean("THEMESWITCH", false);
        if (theme.contains("DARK")){
            setTheme(R.style.MyTheme);
        }else{
            setTheme(R.style.LightTheme);
        }
        drawertogglestyle = R.drawable.ic_drawer;
        setContentView(R.layout.activity_main);
        getActionBar().setTitle(Html.fromHtml("Brooklyn<b>Tech</b> "));
        // Initializing instance variables
        mTitle = (String) getTitle();
        mDrawerList = (ScrollView) findViewById(R.id.drawer_list);
        ChosenLayout = news;
        // Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerItem1 = (LinearLayout) findViewById(R.id.item1);
        mDrawerItem2 = (LinearLayout) findViewById(R.id.item2);
        mDrawerItem3 = (LinearLayout) findViewById(R.id.item3);
        mDrawerItem4 = (LinearLayout) findViewById(R.id.item4);
        mDrawerItem5 = (LinearLayout) findViewById(R.id.item5);
        contenttext = (TextView) findViewById(R.id.contenttitle);
        mDrawerItem6 = (LinearLayout) findViewById(R.id.item6);
        mDrawerItem8 = (LinearLayout) findViewById(R.id.item8);
        mDrawerItem9 = (LinearLayout) findViewById(R.id.settings);
        news = (LinearLayout)findViewById(R.id.newsitem);
/*        divider1 = findViewById(R.id.divide1);
        divider2 = findViewById(R.id.divide2);
        divider3 = findViewById(R.id.divide3);
        divider4 = findViewById(R.id.divide4);
        divider5 = findViewById(R.id.divide5);
        divider6 = findViewById(R.id.divide6);
        divider7 = findViewById(R.id.divide7);
        divider8 = findViewById(R.id.divide8);
        divider9 = findViewById(R.id.divide9);*/
        // Getting reference to the ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                drawertogglestyle,
                R.string.drawer_open,
                R.string.drawer_close) {
            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                //getActionBar().setSubtitle(mTitle);
                invalidateOptionsMenu();
                getActionBar().setTitle(Html.fromHtml("Brooklyn<b>Tech</b>"));
                contenttext.setText(mTitle);
                mDrawerItem1.setVisibility(View.GONE);
                mDrawerItem5.setVisibility(View.GONE);
                mDrawerItem8.setVisibility(View.GONE);
                mDrawerItem2.setVisibility(View.VISIBLE);
                mDrawerItem3.setVisibility(View.VISIBLE);
                mDrawerItem4.setVisibility(View.VISIBLE);
                mDrawerItem6.setVisibility(View.VISIBLE);
                mDrawerItem9.setVisibility(View.VISIBLE);

/*                divider9.setVisibility(View.VISIBLE);
                divider1.setVisibility(View.GONE);
                divider2.setVisibility(View.GONE);
                divider3.setVisibility(View.GONE);
                divider4.setVisibility(View.VISIBLE);
                divider5.setVisibility(View.VISIBLE);
                divider6.setVisibility(View.VISIBLE);
                divider7.setVisibility(View.VISIBLE);
                divider8.setVisibility(View.VISIBLE);*/

            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Select A Category");
                invalidateOptionsMenu();
            }

        };    // Setting DrawerToggle on DrawerLayout
        mDrawerLayout.setDrawerListener(mDrawerToggle);

		
		/*// Setting the adapter on mDrawerList
		mDrawerList.setAdapter(adapter);*/

        // Enabling Home button
        getActionBar().setHomeButtonEnabled(true);

        // Enabling Up navigation
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerItem1.setVisibility(View.GONE);
        mDrawerItem5.setVisibility(View.GONE);
        mDrawerItem8.setVisibility(View.GONE);
/*        divider1.setVisibility(View.GONE);
        divider2.setVisibility(View.GONE);
        divider3.setVisibility(View.GONE);*/
        news.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {
                if (mDrawerItem1.getVisibility() == View.GONE) {
                    mDrawerItem1.setVisibility(View.VISIBLE);
                    mDrawerItem5.setVisibility(View.VISIBLE);
                    mDrawerItem8.setVisibility(View.VISIBLE);
                    mDrawerItem2.setVisibility(View.GONE);
                    mDrawerItem3.setVisibility(View.GONE);
                    mDrawerItem4.setVisibility(View.GONE);
                    mDrawerItem6.setVisibility(View.GONE);
                    mDrawerItem9.setVisibility(View.GONE);
/*                    divider9.setVisibility(View.GONE);
                    divider1.setVisibility(View.VISIBLE);
                    divider2.setVisibility(View.VISIBLE);
                    divider3.setVisibility(View.VISIBLE);
                    divider4.setVisibility(View.GONE);
                    divider5.setVisibility(View.GONE);
                    divider6.setVisibility(View.GONE);
                    divider7.setVisibility(View.GONE);
                    divider8.setVisibility(View.GONE);*/

                } else {
                    mDrawerItem1.setVisibility(View.GONE);
                    mDrawerItem5.setVisibility(View.GONE);
                    mDrawerItem8.setVisibility(View.GONE);
                    mDrawerItem2.setVisibility(View.VISIBLE);
                    mDrawerItem3.setVisibility(View.VISIBLE);
                    mDrawerItem4.setVisibility(View.VISIBLE);
                    mDrawerItem6.setVisibility(View.VISIBLE);
                    mDrawerItem9.setVisibility(View.VISIBLE);
/*                    divider9.setVisibility(View.VISIBLE);
                    divider1.setVisibility(View.GONE);
                    divider2.setVisibility(View.GONE);
                    divider3.setVisibility(View.GONE);
                    divider4.setVisibility(View.VISIBLE);
                    divider5.setVisibility(View.VISIBLE);
                    divider6.setVisibility(View.VISIBLE);
                    divider7.setVisibility(View.VISIBLE);
                    divider8.setVisibility(View.VISIBLE);*/
                }
            }
        });
        mDrawerItem1.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {

                // Creating a fragment object
                NewsFragment rFragment = new NewsFragment();
                mTitle = "General News";
                // Creating a Bundle object
                //Bundle data = new Bundle();
                Bundle data=new Bundle();
                data.putString("link", "http://rsscleaner.appspot.com/feed?feedUrl=http://bths.edu/apps/news/news_rss.jsp?id=0");
                data.putString("title", mTitle);
                rFragment.setArguments(data);
                // Setting the position to the fragment
                //rFragment.setArguments(data);
                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);
                // Committing the transaction
                ft.commit();
                newfragopen = true;
                ChosenLayout = news;
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });

        mDrawerItem2.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {


                // Creating a fragment object
                LinkFragment rFragment = new LinkFragment();
					
/*					// Creating a Bundle object
					Bundle data = new Bundle();
					
					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", position);
					
					// Setting the position to the fragment
					rFragment.setArguments(data);*/
                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();
                mTitle = "Links";
                newfragopen = false;
                ChosenLayout = mDrawerItem2;
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });
        mDrawerItem3.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {


                // Creating a fragment object
                StaffFragment rFragment = new StaffFragment();
/*					// Creating a Bundle object
					Bundle data = new Bundle();
					
					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", posit
					// Setting the position to the fragment
					rFragment.setArguments(data);*/

                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();
                newfragopen = false;
                ChosenLayout=mDrawerItem3;
                mTitle = "Staff Directory";
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });
        mDrawerItem4.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {
                calfragtest rFragment = new calfragtest();

/*					// Creating a Bundle object
					Bundle data = new Bundle();

					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", position);

					// Setting the position to the fragment
					rFragment.setArguments(data);*/
                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();
                mTitle = "Calendar";
                newfragopen = false;
                ChosenLayout=mDrawerItem4;
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);

                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });
        mDrawerItem5.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {


                // Creating a fragment object
                NewsFragment rFragment = new NewsFragment();
                mTitle = "Athletics News";
                Bundle data=new Bundle();
                data.putString("link", "http://rsscleaner.appspot.com/feed?feedUrl=http://bths.edu/apps/news/news_rss.jsp?id=2");
                data.putString("title", mTitle);
                rFragment.setArguments(data);
                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();
                newfragopen = true;
                ChosenLayout=news;
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });
        mDrawerItem6.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {


                // Creating a fragment object
                InfoFragment rFragment = new InfoFragment();
/*					// Creating a Bundle object
					Bundle data = new Bundle();
					
					// Setting the index of the currently selected item of mDrawerList
					data.putInt("position", position);
					
					// Setting the position to the fragment
					rFragment.setArguments(data);*/

                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();
                newfragopen = false;
                ChosenLayout=mDrawerItem6;
                mTitle = "Other Information";
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });
        mDrawerItem8.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {
                mTitle = "Extra Curricular News";

                // Creating a fragment object
                NewsFragment rFragment = new NewsFragment();

                Bundle data=new Bundle();
                data.putString("link", "http://rsscleaner.appspot.com/feed?feedUrl=http://bths.edu/apps/news/news_rss.jsp?id=3");
                data.putString("title", mTitle);
                rFragment.setArguments(data);
                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);

                // Committing the transaction
                ft.commit();
                newfragopen = true;
                ChosenLayout=news;
                // Closing the drawer
                mDrawerLayout.closeDrawer(mDrawerList);


            }
        });
        mDrawerItem9.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View view) {
                mTitle = "Settings";

                // Creating a fragment object
                SettingsFragment rFragment = new SettingsFragment();

                Bundle data=new Bundle();
                data.putString("title", mTitle);
                rFragment.setArguments(data);
                // Getting reference to the FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // Creating a fragment transaction
                FragmentTransaction ft = fragmentManager.beginTransaction();

                // Adding a fragment to the fragment transaction
                ft.replace(R.id.content_frame, rFragment);
// Closing the drawer
                ft.commit();
                newfragopen = false;
                ChosenLayout=mDrawerItem9;
                mDrawerLayout.closeDrawer(mDrawerList);

                // Committing the transaction



            }
        });
        mDrawerItem1.performClick();
        if (page.equals("athletics")){
            mDrawerItem5.performClick();
        }if(page.equals("ec")){
            mDrawerItem8.performClick();
        }if (page.equals("links")){
            mDrawerItem2.performClick();
        }if (page.equals("staff")){
            mDrawerItem3.performClick();
        }if (page.equals("calendar")){
            mDrawerItem4.performClick();
        }if (page.equals("schedule")){
            if (themeswitch){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("THEMESWITCH", false);
                editor.commit();
                mDrawerItem9.performClick();
            }else{
            Intent intent = new Intent();
            intent.setClass(this, BellSchedule.class);
            startActivity(intent);
            }
        }if (page.equals("places")){
            if (themeswitch){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("THEMESWITCH", false);
                editor.commit();
                mDrawerItem9.performClick();
            }else{
            Intent intent = new Intent();
            intent.setClass(this, ImportantPlaces.class);
            startActivity(intent);
            }
        }
        if (themeswitch){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("THEMESWITCH", false);
            editor.commit();
            mDrawerItem9.performClick();
        }

        contenttext.setText(mTitle);
    }
    @Override
    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Press back again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    /**
     * Handling the touch event of app icon
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Called whenever we call invalidateOptionsMenu()
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        if (newfragopen){
        menu.findItem(R.id.action_refresh).setVisible(!drawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionmenu, menu);
        return true;
    }
}
