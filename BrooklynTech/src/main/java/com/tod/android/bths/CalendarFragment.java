package com.tod.android.bths;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarFragment extends Fragment {
int Year;
int Month;
    TextView cal;
String calwebsite;
String MonthTextLong;
String MonthTextShort;
String YearText;
String atext;
SimpleDateFormat month_short;
Document caldoc;
Calendar c;
Thread calthread;
SimpleDateFormat month_long;
Elements calparagraphs;

@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View v = inflater.inflate(R.layout.calendar_fragment_layout, container, false);
		c = Calendar.getInstance();
        month_long = new SimpleDateFormat("MMMM");
        MonthTextLong=month_long.format(c.getTime());
    month_short = new SimpleDateFormat("MM");
    MonthTextShort=month_short.format(c.getTime());
    calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?";
        Year = c.get(Calendar.YEAR); 
        YearText = Integer.toString(Year);
        cal = (TextView)v.findViewById(R.id.calendartext);


		Spinner months = (Spinner) v.findViewById(R.id.months_spinner);
        ArrayAdapter<CharSequence> monthadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Months, R.layout.picker_row);
              months.setAdapter(monthadapter);
              months.setOnItemSelectedListener(new OnItemSelectedListener(){
            	  @Override
            	  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int monthplace, long id) {
            	      Month = monthplace;
            	  }
            	  public void onNothingSelected(AdapterView<?> parent) {
            	    }
              });
              months.setSelection(monthadapter.getPosition(MonthTextLong));



        Spinner years = (Spinner) v.findViewById(R.id.years_spinner);
        ArrayAdapter<CharSequence> yearadapter = ArrayAdapter.createFromResource(getActivity(),
             R.array.Years, R.layout.picker_row);
        years.setAdapter(yearadapter);
        years.setOnItemSelectedListener(new OnItemSelectedListener(){
      	  @Override
      	  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int yearplace, long id) {
      	      if (yearplace == 0){
      	    	  Year = 2012;
      	      }if (yearplace == 1){
      	    	  Year = 2013;
      	      }if (yearplace == 2){
      	    	  Year = 2014;
      	      }
      	  }

      	  public void onNothingSelected(AdapterView<?> parent) {
      	  }
      		  });
       years.setSelection(yearadapter.getPosition(YearText));

        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
        
           public void onClick(View v) {
        	   //if there is an internet connection
               calwebsite = "http://www.bths.edu/apps/events/list_pff.jsp?sd=&y="+ Year +"&m="+ Month +"&id=0";
               Thread calthread2 = new Thread() {
                   @Override
                   public void run() {
                       try {
                           caldoc = Jsoup.connect(calwebsite)
                                   .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                                   .get();
                           caldoc.select("img").remove();
                           caldoc.select("b").first().remove();
                           //final Elements paragraphs = doc.select("p").prepend("\\n\\n");
                           caldoc.select("td").first().remove();
                           calparagraphs = caldoc.select("center");
                           getActivity().runOnUiThread(new Runnable() {
                               public void run() {
                                   cal.setText(Html.fromHtml(calparagraphs.toString()));
                               }
                           });
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               };
               calthread2.start();
            }
           

        });
    calthread = new Thread() {
        @Override
        public void run() {
            try {
                caldoc = Jsoup.connect(calwebsite)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.19 Safari/537.36")
                        .get();
                caldoc.select("img").remove();
                caldoc.select("b").first().remove();
                //final Elements paragraphs = doc.select("p").prepend("\\n\\n");
                caldoc.select("td").first().remove();
                //final Elements paragraphs = doc.select("p").prepend("\\n\\n");
                calparagraphs = caldoc.select("center");
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        cal.setText(Html.fromHtml(calparagraphs.toString()));
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    calthread.start();
return v;
	}
}
