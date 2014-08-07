package com.tod.android.bths;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.wt.calendarcard.CalendarCard;
import com.wt.calendarcard.CardGridItem;
import com.wt.calendarcard.OnCellItemClick;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Margolin on 12/30/13.
 */
public class CalendarFragment extends Fragment {
    private CalendarCard mCalendarCard;
    Calendar cal;
    Elements calparagraphs;
    int day;
    Document caldoc;
    String calwebsite;
    Button next;
    Button previous;
    Async task;
    ScalpelFrameLayout scalpelView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String theme = sharedPreferences.getString("themepref", "LIGHT");
        if (theme.contains("DARK")){
            getActivity().setTheme(R.style.MyTheme);
        }else{
            getActivity().setTheme(R.style.LightTheme);
        }
        Boolean devmode = sharedPreferences.getBoolean("devmode", false);
        View v = inflater.inflate(R.layout.calendarlayout, container, false);
        mCalendarCard = (CalendarCard)v.findViewById(R.id.calendarCard1);
        cal=Calendar.getInstance();
        next = (Button)mCalendarCard.findViewById(R.id.next);
        previous = (Button)mCalendarCard.findViewById(R.id.previous);

        mCalendarCard.setDateDisplay(cal);
        //cal.set(2013, 2,5);
        calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?sd=&y="+ mCalendarCard.getDateDisplay().get(Calendar.YEAR) +"&m="+ mCalendarCard.getDateDisplay().get(Calendar.MONTH) +"&id=0";
        task = new Async();
        task.execute();



        if (devmode){
            scalpelView = (ScalpelFrameLayout)v.findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }

        return v;
    }
    class Async extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                caldoc = Jsoup.connect(calwebsite)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal= Calendar.getInstance();
                    cal.set(mCalendarCard.getDateDisplay().get(Calendar.YEAR), mCalendarCard.getDateDisplay().get(Calendar.MONTH) - 1, 1);
                    mCalendarCard.setDateDisplay(cal);
                    mCalendarCard.notifyChanges();
                    calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?sd=&y="+ mCalendarCard.getDateDisplay().get(Calendar.YEAR) +"&m="+ mCalendarCard.getDateDisplay().get(Calendar.MONTH) +"&id=0";
                    if (!task.isCancelled()) {
                        task.cancel(true);
                    }
                    task = new Async();
                    task.execute();
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal= Calendar.getInstance();
                    cal.set(mCalendarCard.getDateDisplay().get(Calendar.YEAR),mCalendarCard.getDateDisplay().get(Calendar.MONTH)+1,1);
                    mCalendarCard.setDateDisplay(cal);
                    mCalendarCard.notifyChanges();
                    calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?sd=&y="+ mCalendarCard.getDateDisplay().get(Calendar.YEAR) +"&m="+ mCalendarCard.getDateDisplay().get(Calendar.MONTH) +"&id=0";
                    if (!task.isCancelled()) {
                        task.cancel(true);
                    }
                    task = new Async();
                    task.execute();
                }
            });
            mCalendarCard.setOnCellItemClick(new OnCellItemClick() {
                @Override
                public void onCellClick(View v, CardGridItem item) {
                    day = item.getDayOfMonth();
                    String displaydate = "";
                    String displayevent = "";
                    Boolean error = false;
                    try{
                        calparagraphs = caldoc.select("td:contains(" + " " + day + ", " + mCalendarCard.getDateDisplay().get(Calendar.YEAR) + ")>ul>li");
                        displaydate = new SimpleDateFormat("MMMM").format(cal.getTime()) + " " + day + ", " + mCalendarCard.getDateDisplay().get(Calendar.YEAR);
                        displayevent = Html.fromHtml(calparagraphs.toString()).toString().replaceFirst("\n","");


                    }catch(NullPointerException e){
                        e.printStackTrace();
                        error = true;
                    }
                    if (!error) {
                        if (displayevent.length() > 2) {
                            String displayinfo = displaydate + "\n" + displayevent;
                            Toast.makeText(getActivity().getBaseContext(), displayinfo, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity().getBaseContext(), "No events found", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getActivity().getBaseContext(), "Couldn't connect to network", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }

    }
}
