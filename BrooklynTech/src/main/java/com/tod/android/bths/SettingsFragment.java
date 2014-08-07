package com.tod.android.bths;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.machinarius.preferencefragment.PreferenceFragment;


/**
 * Created by Margolin on 12/22/13.
 */
public class SettingsFragment extends PreferenceFragment {
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);
        Preference prefereces=findPreference("about");
        Preference themeswitcheroo=findPreference("themepref");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        themeswitcheroo.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("THEMESWITCH", true);
                editor.commit();
                getActivity().recreate();
                return true;
            }
        });
        prefereces.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.about_dialog);
                dialog.setTitle("About");
                dialog.show();
                return true;
            }


        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String theme = sharedPreferences.getString("themepref", "LIGHT");
        if (theme.contains("DARK")){
            getActivity().setTheme(R.style.MyTheme);
        }else{
            getActivity().setTheme(R.style.LightTheme);
        }
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ListView lv = (ListView) view.findViewById(android.R.id.list);
        lv.setPadding(16,16,16,16);
        lv.setClipToPadding(false);
        return view;
    }

    }
