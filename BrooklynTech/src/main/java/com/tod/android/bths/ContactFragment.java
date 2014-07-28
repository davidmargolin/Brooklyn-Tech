package com.tod.android.bths;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.scalpel.ScalpelFrameLayout;

public class ContactFragment extends Fragment {
	int position;
	String email;
	String body;
	String subject;
    ScalpelFrameLayout scalpelView;
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
		View v = inflater.inflate(R.layout.contact_fragment_layout, container, false);
		getActivity().getActionBar().setTitle("Contact");
		 final EditText text1 = (EditText) v.findViewById(R.id.editText1);
         final EditText text2 = (EditText) v.findViewById(R.id.editText2);
		Spinner people = (Spinner) v.findViewById(R.id.people_spinner);
        ArrayAdapter<CharSequence> peopleadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.People, R.layout.picker_row);
              people.setAdapter(peopleadapter);
              people.setOnItemSelectedListener(new OnItemSelectedListener(){
            	  @Override
            	  public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int place, long id) {
            	      position = place;
            	      if (position == 0){
            	    	email = "rasher@schools.nyc.gov";
            	      }
            	      if (position == 1){
            	    	  email = "sullivan2@schools.nyc.gov";
            	      }
            	      if (position == 2){
            	    	  email = "phoftyz@schools.nyc.gov";
            	      }
            	      if (position == 3){
            	    	  email = "gpaulson@schools.nyc.gov";
            	      }
            	      if (position == 4){
            	    	  email = "mohara@schools.nyc.gov";
            	      }
            	      if (position == 5){
            	    	  email = "apalmer2@schools.nyc.gov";
            	      }
            	      if (position == 6){
            	    	  email = "lmarquez2@schools.nyc.gov";
            	      }
            	  }
            	  public void onNothingSelected(AdapterView<?> parent) {
            	  }
              });

              final TextView notsent = (TextView) v.findViewById(R.id.senttext);
              people.setSelection(0);
              
              final Button button = (Button) v.findViewById(R.id.sendbutton);
              button.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View v) {
                     
                      body = text1.getText().toString();
                      subject = text2.getText().toString();
                      StringBuilder builder = new StringBuilder("mailto:" + Uri.encode(email));
                      if (subject != null) {
                          builder.append("?subject=").append(Uri.encode(Uri.encode(subject)));
                          if (body != null) {
                              builder.append("&body=" + Uri.encode(Uri.encode(body)));
                          }
                      }
                      String uri = builder.toString();
                      Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
                      PackageManager packageManager = getActivity().getPackageManager();
                      List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                      if (activities.size() > 0){
                    	  startActivity(Intent.createChooser(intent, "Send email via:"));
                      }
                      if (activities.size() <= 0){
                    	  notsent.setText("Do you have an email client installed?");  
                      }
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
