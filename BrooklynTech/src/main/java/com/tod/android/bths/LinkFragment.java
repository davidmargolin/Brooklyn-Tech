package com.tod.android.bths;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jakewharton.scalpel.ScalpelFrameLayout;


public class LinkFragment extends Fragment {
    LinearLayout Skedula;
    LinearLayout Moodle;
    LinearLayout Bktechemail;
    LinearLayout Naviance;
    ScalpelFrameLayout scalpelView;
    LinearLayout UTexas;
    LinearLayout Daedalus;
    String daed = "https://students-brooklyntechhs.theschoolsystem.net/login.rb";
    String sked ="https://pupilpath.skedula.com/";
    String utex = "https://quest.cns.utexas.edu/student";
    String nav = "https://connection.naviance.com/family-connection/auth/login/?hsid=brooklyntech";
    String btem = "http://mail.bths.edu/";
    String mood = "http://moodle2.bths.edu/";
    Boolean browserview;
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
        browserview = sharedPreferences.getBoolean("openinbrowser",false);
		View v = inflater.inflate(R.layout.link_fragment_layout, container, false);
        Skedula = (LinearLayout)v.findViewById(R.id.Skedula);
        Skedula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (browserview){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sked));
                startActivity(browserIntent);
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), BrowserView.class);
                    intent.putExtra("LINK", sked);
                    intent.putExtra("TITLE", "Skedula");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }
        });
        Moodle = (LinearLayout)v.findViewById(R.id.Moodle);
        Moodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (browserview){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mood));
                    startActivity(browserIntent);
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), BrowserView.class);
                    intent.putExtra("LINK", mood);
                    intent.putExtra("TITLE", "Moodle");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }
        });
        Bktechemail = (LinearLayout)v.findViewById(R.id.Email);
        Bktechemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (browserview){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(btem));
                    startActivity(browserIntent);
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), BrowserView.class);
                    intent.putExtra("LINK", btem);
                    intent.putExtra("TITLE", "BTHS E-Mail");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }
        });
        Naviance = (LinearLayout)v.findViewById(R.id.Naviance);
        Naviance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (browserview){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(nav));
                    startActivity(browserIntent);
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), BrowserView.class);
                    intent.putExtra("LINK", nav);
                    intent.putExtra("TITLE", "Naviance");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }
        });
        UTexas = (LinearLayout)v.findViewById(R.id.Utexas);
        UTexas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (browserview){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(utex));
                    startActivity(browserIntent);
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), BrowserView.class);
                    intent.putExtra("LINK", utex);
                    intent.putExtra("TITLE", "UTexas");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }
        });
        Daedalus = (LinearLayout)v.findViewById(R.id.Daedalus);
        Daedalus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (browserview){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(daed));
                    startActivity(browserIntent);
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), BrowserView.class);
                    intent.putExtra("LINK", daed);
                    intent.putExtra("TITLE", "Daedalus");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }
        });



return v;
	}
}