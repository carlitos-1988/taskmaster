package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.jco.taskmaster.R;

public class SettingsPage extends AppCompatActivity {

    public static final String USERNAME_SET = "definedUserName";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        setUpUsernameEditText();
        setUpSaveButton();


    }

    void setUpUsernameEditText(){
        String username = preferences.getString(USERNAME_SET,null);
        ((EditText)findViewById(R.id.SettingsActivityEditInputTextUsername)).setText(USERNAME_SET);
    }

    void setUpSaveButton(){
        ((Button)findViewById(R.id.ActivitySaveUsernameButton )).setOnClickListener(v->{
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            EditText usernameEditText = (EditText) findViewById(R.id.SettingsActivityEditInputTextUsername);
            String username = usernameEditText.getText().toString();

            preferenceEditor.putString(USERNAME_SET, username);
            preferenceEditor.apply();

            Toast.makeText(this, "Username Updated", Toast.LENGTH_SHORT).show();
        });
    }
}