package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.jco.taskmaster.R;

public class SettingsPage extends AppCompatActivity {

    public static final String USERNAME_SET = "definedUserName";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        preferences = PreferenceManager.getDefaultSharedPreferencesName(this);
    }

    void setUpSaveButton(){
        ((Button)findViewById(R.id.ActivitySaveUsernameButton)).setOnClickListener(v->{
            SharedPreferences.Editor preferenceEditor = preferences.edit();
        });
    }
}