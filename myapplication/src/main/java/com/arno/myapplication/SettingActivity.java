package com.arno.myapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements Preference.OnPreferenceChangeListener {
    private AlertDialog alertDialog;
    private String TYPE = "popular";
    static final String STORE_NAME = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        TODO Prefrence待解决
//        addPreferencesFromResource(R.xml.pref_general);

//        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)));
// 单选提示框


    }

    public void showSingleAlertDialog(View view) {
        final String[] items = {"按热度排序", "按评分排序"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("选择排序方式");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int index) {
                Toast.makeText(SettingActivity.this, items[index], Toast.LENGTH_SHORT).show();
                TYPE = items[index];
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //TODO 业务逻辑代码


                SharedPreferences settings = getSharedPreferences(STORE_NAME, MODE_PRIVATE);
//                SharedPreferences settings = getPreferences(1);
//                SharedPreferences prefs = PreferenceManager.getPreferences(getActivity());
                SharedPreferences.Editor editor = settings.edit();

                editor.putString("sourceType", TYPE);

                editor.commit();
                // 关闭提示框
                alertDialog.dismiss();
            }
        });
        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO 业务逻辑代码

                // 关闭提示框
                alertDialog.dismiss();
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }
//    public void saveToSharedPreference(View v){
//        sp = this.getSharedPreferences("config.txt", Context.MODE_PRIVATE);
//        //得到sharedpreference的编辑器
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("title","www.sharejs.com");
//        editor.putBoolean("success",true);
//        //提交数据
//        editor.commit();
//        Toast.makeText(this,"保存到sharedpreference成功",Toast.LENGTH_SHORT).show();
//
//        //读取SharePreference的内容
//        String tel = sp.getString("title","没有获取到值");
//        Toast.makeText(this,tel,Toast.LENGTH_SHORT).show();
//    }


}
