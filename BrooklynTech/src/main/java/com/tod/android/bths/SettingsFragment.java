package com.tod.android.bths;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;

import android.content.DialogInterface;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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


        return view;
    }
/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_layout, container, false);

        getActivity().getActionBar().setTitle("Settings");
        themeswitcher = (Switch)v.findViewById(R.id.themeswitch);
        dlgAlert= new AlertDialog.Builder(getActivity());
        about = (LinearLayout)v.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.about_dialog);
                    dialog.setTitle("About");
                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
        });
        versionnumber = (TextView)v.findViewById(R.id.version);
        PackageManager manager = getActivity().getApplicationContext().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }
        versionnumber.setText("Version - " + versionName + "_ALPHA");
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        Boolean isit =sharedPreferences.getBoolean("LIGHT", false);
        if (isit){
            themeswitcher.setChecked(true);
        }else{
            themeswitcher.setChecked(false);
        }
        themeswitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedPreferences.edit();


                theme = isChecked;
                editor.putBoolean("LIGHT", theme);
                editor.putBoolean("THEMESWITCH", true);
                editor.commit();
                getActivity().recreate();
            }
        });
                return v;*/

    }
