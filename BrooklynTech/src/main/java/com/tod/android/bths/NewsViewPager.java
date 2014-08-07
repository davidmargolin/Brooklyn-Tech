package com.tod.android.bths;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Margolin on 8/5/2014.
 */
public class NewsViewPager extends Fragment{
    String mTitle;
    ViewPager viewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_layout, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.vpPager);

        //pagerTabStrip.setTabIndicatorColor(Color.RED);
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()) {
        });
        viewPager.setOffscreenPageLimit(2);
        return v;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private int NUM_ITEMS = 3;
        public MyPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);
        }
        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    // Creating a fragment object

                    NewsFragment rFragment = new NewsFragment();
                    // Creating a Bundle object
                    //Bundle data = new Bundle();
                    Bundle data=new Bundle();
                    data.putString("link", "http://rsscleaner.appspot.com/feed?feedUrl=http://bths.edu/apps/news/news_rss.jsp?id=0");
                    rFragment.setArguments(data);
                    return rFragment;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    // Creating a fragment object
                    NewsFragment sFragment = new NewsFragment();
                    // Creating a Bundle object
                    //Bundle data = new Bundle();
                    Bundle data2=new Bundle();

                    data2.putString("link", "http://rsscleaner.appspot.com/feed?feedUrl=http://bths.edu/apps/news/news_rss.jsp?id=2");
                    sFragment.setArguments(data2);
                    return sFragment;
                case 2: // Fragment # 1 - This will show SecondFragment
                    // Creating a fragment object
                    NewsFragment tFragment = new NewsFragment();
                    // Creating a Bundle object
                    //Bundle data = new Bundle();
                    Bundle data3=new Bundle();
                    data3.putString("link", "http://rsscleaner.appspot.com/feed?feedUrl=http://bths.edu/apps/news/news_rss.jsp?id=3");
                    tFragment.setArguments(data3);
                    return tFragment;
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    mTitle = "General";
                    break;
                case 1:
                    mTitle = "Athletics";
                    break;
                case 2:
                    mTitle = "Extra Curricular";
                    break;

            }
            return mTitle;
        }

    }
}