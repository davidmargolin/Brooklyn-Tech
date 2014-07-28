package com.tod.android.bths;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.scalpel.ScalpelFrameLayout;
import com.wt.calendarcard.CalendarCard;
import com.wt.calendarcard.CalendarCardPager;
import com.wt.calendarcard.CardGridItem;
import com.wt.calendarcard.OnCellItemClick;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Margolin on 12/30/13.
 */
public class calfragtest extends Fragment {
    private CalendarCard mCalendarCard;
    private TextView mTextView;
    Calendar cal;
    Elements calparagraphs;
    int day;
    Document caldoc;
    String calwebsite;
    TextView infotext;
    Button next;
    Button previous;
    ScrollView card;
    Thread calthread2;
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
        mTextView = (TextView)v.findViewById(R.id.textView1);
        infotext = (TextView)v.findViewById(R.id.textView2);
        card = (ScrollView)v.findViewById(R.id.scrollView);
        next = (Button)mCalendarCard.findViewById(R.id.next);
        previous = (Button)mCalendarCard.findViewById(R.id.previous);

        mCalendarCard.setDateDisplay(cal);
        //cal.set(2013, 2,5);
        calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?sd=&y="+ mCalendarCard.getDateDisplay().get(Calendar.YEAR) +"&m="+ mCalendarCard.getDateDisplay().get(Calendar.MONTH) +"&id=0";
        calthread2 = new Thread() {
            @Override
            public void run() {
                try {
            caldoc = Jsoup.connect(calwebsite)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
            }
        };
        calthread2.start();
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal= Calendar.getInstance();
                cal.set(mCalendarCard.getDateDisplay().get(Calendar.YEAR), mCalendarCard.getDateDisplay().get(Calendar.MONTH) - 1, 1);
                mCalendarCard.setDateDisplay(cal);
                mCalendarCard.notifyChanges();
                card.setVisibility(View.INVISIBLE);
                calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?sd=&y="+ mCalendarCard.getDateDisplay().get(Calendar.YEAR) +"&m="+ mCalendarCard.getDateDisplay().get(Calendar.MONTH) +"&id=0";
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            caldoc = Jsoup.connect(calwebsite)
                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                                    .get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal= Calendar.getInstance();
                cal.set(mCalendarCard.getDateDisplay().get(Calendar.YEAR),mCalendarCard.getDateDisplay().get(Calendar.MONTH)+1,1);
                mCalendarCard.setDateDisplay(cal);
                mCalendarCard.notifyChanges();
                card.setVisibility(View.INVISIBLE);
                calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?sd=&y="+ mCalendarCard.getDateDisplay().get(Calendar.YEAR) +"&m="+ mCalendarCard.getDateDisplay().get(Calendar.MONTH) +"&id=0";
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            caldoc = Jsoup.connect(calwebsite)
                                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                                    .get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        mCalendarCard.setOnCellItemClick(new OnCellItemClick() {
            @Override
            public void onCellClick(View v, CardGridItem item) {
                day = item.getDayOfMonth();

                try{
                calparagraphs = caldoc.select("td:contains(" + " " + day + ", " + mCalendarCard.getDateDisplay().get(Calendar.YEAR) + ")>ul>li");
                infotext.setText(Html.fromHtml(calparagraphs.toString()));
                }catch(NullPointerException e){
                   e.printStackTrace();
                        Toast.makeText(getActivity().getBaseContext(), "Couldn't connect to network", Toast.LENGTH_SHORT).show();
                    infotext.setText("Network Error");
                }
                if (infotext.getText().length()<2){
                    infotext.setText("No events found");
                }


                card.setVisibility(View.VISIBLE);
                mTextView.setText( new SimpleDateFormat("MMMM").format(cal.getTime()) + " " + day + ", " + mCalendarCard.getDateDisplay().get(Calendar.YEAR));


            }
        });

        if (devmode){
            scalpelView = (ScalpelFrameLayout)v.findViewById(R.id.scalpel);
            scalpelView.setLayerInteractionEnabled(true);
            scalpelView.setDrawViews(true);
        }

        return v;
    }

}
