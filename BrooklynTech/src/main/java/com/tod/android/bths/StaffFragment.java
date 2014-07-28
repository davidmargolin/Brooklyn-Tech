package com.tod.android.bths;

import android.app.Fragment;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class StaffFragment extends Fragment {

    Document depdoc;
    Elements departments;
    ListView departmentlist;
    ProgressBar progress;
    Async task;
    ArrayList<String> a = new ArrayList<String>();
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
		View v = inflater.inflate(R.layout.staff_fragment_layout, container, false);
        departmentlist= (ListView)v.findViewById(R.id.lvDepartments);
        progress = (ProgressBar)v.findViewById(R.id.progressBar1);
        task = new Async();
        task.execute();
return v;
	}
    private class Async extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                depdoc = Jsoup.connect("http://bths.edu/m/departments/")
                        .get();
                //final Elements paragraphs = doc.select("p").prepend("\\n\\n");
                departments = depdoc.select("legend");
                for (Element department : departments) {
                    a.add(department.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            progress.setVisibility(View.VISIBLE);
            departmentlist.setVisibility(View.GONE);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.setVisibility(View.GONE);
            departmentlist.setVisibility(View.VISIBLE);
            try{
            ArrayAdapter<String> arrayAdapter =
                    new ArrayAdapter<String>(getActivity(),R.layout.singletextcard, a);
            departmentlist.setAdapter(arrayAdapter);
            departmentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), StaffSubDeps.class);
                    intent.putExtra("Dep", a.get(position));
                    startActivity(intent);
                }
                });
            }catch(NullPointerException e){
                e.printStackTrace();
            }
}
        }
    }