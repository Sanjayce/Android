package com.xl.android.content;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.xl.android.R;

public class PreferenceActivitys extends PreferenceActivity {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        PreferenceManager mPreferenceManager = getPreferenceManager();
        CheckBoxPreference mCheckBoxPreference = (CheckBoxPreference) mPreferenceManager.findPreference("checkbox");
        Toast.makeText(getApplicationContext(), "State:" + mCheckBoxPreference.isChecked(), Toast.LENGTH_SHORT).show();
    }
}
