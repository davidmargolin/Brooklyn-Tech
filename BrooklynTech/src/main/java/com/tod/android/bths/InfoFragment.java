package com.tod.android.bths;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class InfoFragment extends Fragment {

TextView mbell_schedule;
TextView mimportant_places;
TextView mannouncements;

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
		View v = inflater.inflate(R.layout.info_fragment_layout, container, false);
        mbell_schedule = (TextView) v.findViewById(R.id.bell_schedule);
        mbell_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), BellSchedule.class);
                startActivity(intent);
            }
        });
        mimportant_places = (TextView) v.findViewById(R.id.important_places);
        mimportant_places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ImportantPlaces.class);
                startActivity(intent);
            }
        });
        mannouncements = (TextView) v.findViewById(R.id.announcements);
        mannouncements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), Announcements.class);
                startActivity(intent);
            }
        });



return v;
	}
}
