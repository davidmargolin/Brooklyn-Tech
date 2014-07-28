package com.tod.android.bths;


import android.app.Fragment;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleRss2Parser;
import at.theengine.android.simple_rss2_android.SimpleRss2ParserCallback;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;




public class NewsFragment extends Fragment implements OnRefreshListener {

    private Context mContext;
    private String etFeedUrl;
    ProgressBar bar;
    private ListView lvFeedItems;
    SimpleRss2Parser parser;
    LoadFeedASYNC task;
    TextView error;
    private PullToRefreshLayout mPullToRefreshLayout;
    private SimpleRss2ParserCallback mCallback;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String theme = sharedPreferences.getString("themepref", "LIGHT");
        if (theme.contains("DARK")){
            getActivity().setTheme(R.style.MyTheme);
        }else{
            getActivity().setTheme(R.style.LightTheme);
        }
        Boolean devmode = sharedPreferences.getBoolean("devmode", false);
        View v = inflater.inflate(R.layout.news_fragment_layout, container, false);
        mPullToRefreshLayout = (PullToRefreshLayout) v.findViewById(R.id.ptr_layout);

        // Now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(getActivity())
                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set the OnRefreshListener
                .listener(this)
                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);
        mContext = v.getContext();
        etFeedUrl = getArguments().getString("link");
        setHasOptionsMenu(true);
        error = (TextView)v.findViewById(R.id.error);
        bar = (ProgressBar)v.findViewById(R.id.progressBar1);
        lvFeedItems = (ListView) v.findViewById(R.id.lvFeedItems);
        /*if (devmode){
            scalpelView = (ScalpelFrameLayout)findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }*/
        task = new LoadFeedASYNC();
        task.execute(etFeedUrl);
//        Parsing.start();
        return v;
    }
    private class LoadFeedASYNC extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... urls) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                parser = new SimpleRss2Parser(etFeedUrl, getCallback());
                parser.parseAsync();
                return null;
            }
            @Override
            protected void onPreExecute() {
                lvFeedItems.setVisibility(View.GONE);
                bar.setVisibility(View.VISIBLE);
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                mPullToRefreshLayout.setRefreshComplete();
                if (bar.isShown()){
                    bar.setVisibility(View.GONE);
                }
            }

        }

    private SimpleRss2ParserCallback getCallback() {
        if (mCallback == null) {
            mCallback = new SimpleRss2ParserCallback() {
                @Override
                public void onFeedParsed(List<RSSItem> items) {
                    for (RSSItem item : items) {
                        Log.d("SimpleRss2ParserDemo", item.getTitle());
                    }
                    lvFeedItems.setAdapter(
                            new MyListAdapter(mContext, R.layout.list_item, (ArrayList<RSSItem>) items)
                    );

                    lvFeedItems.setVisibility(View.VISIBLE);
                    lvFeedItems.scrollTo(0,0);

                }

                @Override
                public void onError(Exception ex) {
                    Toast.makeText(mContext, "Can't connect to the internet :(", Toast.LENGTH_SHORT).show();
                    lvFeedItems.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                    error.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPullToRefreshLayout.setRefreshing(true);
                            task = new LoadFeedASYNC();
                            task.execute(etFeedUrl);
                            error.setVisibility(View.GONE);
                        }
                    });
                }
            };
        }

        return mCallback;
    }

    @Override
    public void onRefreshStarted(View view) {
        /**
         * Simulate Refresh with 4 seconds sleep
         */
        task = new LoadFeedASYNC();
        task.execute(etFeedUrl);

    }
    private class MyListAdapter extends ArrayAdapter<RSSItem> {

        private ArrayList<RSSItem> items;
        private Context ctx;
        private int layout;

        public MyListAdapter(Context context, int layout, ArrayList<RSSItem> items) {
            super(context, layout, items);
            this.items = items;
            this.ctx = context;
            this.layout = layout;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(layout, null);
            }

            final RSSItem o = items.get(position);

            if (o != null) {
                TextView tvPubDate = ((TextView) v.findViewById(R.id.tvPubDate));
                TextView tvTitle = ((TextView) v.findViewById(R.id.tvTitle));
                TextView tvDescription = ((TextView) v.findViewById(R.id.tvDescription));
                TextView tvLnk = ((TextView) v.findViewById(R.id.tvLnk));

                if (tvPubDate != null) {
                    String dateWord = o.getDate().replace("+0000", "");
                    tvPubDate.setText(dateWord.substring(0, 16));
                }

                if (tvTitle != null) {
                    String text = o.getTitle().replaceAll("\\<.*?\\>", "");
                    tvTitle.setText(text);
                }

                if (tvDescription != null) {
                    //String desc = o.getDescription().replaceAll("\\<.*?\\>", "");
                    tvDescription.setText(o.getDescription());
                }

                if (tvLnk != null) {
                    lvFeedItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final RSSItem o = items.get(position);
                            String title = o.getTitle().replaceAll("\\<.*?\\>", "");
                            String link = o.getLink().toString();
                            String desc = o.getDescription();
                            Intent intent = new Intent(getActivity(), ArticleViewer.class);
                            intent.putExtra("Link", link);
                            intent.putExtra("Title", title);
                            intent.putExtra("Desc", desc);
                            startActivity(intent);

                            //Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(o.getLink().toExternalForm()));
                            //startActivity(viewIntent);
                        }
                    });
                }

            }
            return v;
        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.news, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
                task = new LoadFeedASYNC();
                task.execute(etFeedUrl);
                bar.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}