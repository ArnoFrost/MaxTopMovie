package com.arno.myapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class SettingActivityFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {


    public SettingActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_type_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_language_key)));
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
//            查找列表首选项并显示他们的值
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            //对于其他首选项，将摘要设置为值的简单字符串表示形式。
            preference.setSummary(stringValue);
        }
        return true;
    }

    /*
    * 监听每次prefrence的更新变化，在设置更改前显示初始值
    * */
    private void bindPreferenceSummaryToValue(Preference preference) {
//        为prefrence设置监听值的变化的listener
        preference.setOnPreferenceChangeListener(this);

//      使用偏好设置立即触发侦听器当前值

        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }
}
