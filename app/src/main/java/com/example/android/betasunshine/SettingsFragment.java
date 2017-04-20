package com.example.android.betasunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

/**
 * Created by dakaku on 20/4/17.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_weather);

        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();
        Log.v("Preference count", "value " + count);
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                String prefValue = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, prefValue);
            }
        }
    }

    private void setPreferenceSummary(Preference p, Object prefValue) {
        String prefValueString = prefValue.toString();
        String prefKey = p.getKey();

        if (p instanceof ListPreference) {
            ListPreference listpreference = (ListPreference) p;
            int prefIndex = listpreference.findIndexOfValue(prefValueString);
            if (prefIndex >= 0) {
                p.setSummary(listpreference.getEntries()[prefIndex]);
            }
        } else {
            p.setSummary(prefValueString);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference p = findPreference(key);
        if (null != p) {
            if (!(p instanceof CheckBoxPreference)) {
                String prefValue = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, prefValue);
            }
        }
    }
}
