package com.jco.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.jco.taskmaster.MainActivity;
import com.jco.taskmaster.R;

public class SettingsPage extends AppCompatActivity {

    private static final String TAG = "UserProfileActivity";
    public static final String USERNAME_SET = "";
    SharedPreferences preferences;
    AuthUser authUser;

    Button loginButton;
    Button logoutButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        loginButton = findViewById(R.id.UserProfileActivityLoginButton);
        logoutButton = findViewById(R.id.UserProfileActivityLogoutButton);
        signUpButton = findViewById(R.id.UserProfileActivitySignUpButton);

        setUpUsernameEditText();
        setUpSaveButton();

        setupSignUpButton();
        setupLoginButton();
        setupLogoutButton();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Determine if user is logged in, show auth buttons as appropriate
        Amplify.Auth.getCurrentUser(
                success -> {
                    Log.i(TAG, "User authenticated with username: " + success.getUsername());
                    authUser = success;
                    runOnUiThread(this::renderButtons);
                },
                failure -> {
                    Log.i(TAG, "There is no current authenticated user");
                    authUser = null;
                    runOnUiThread(this::renderButtons);
                }
        );
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

    void setupSignUpButton() {
        signUpButton.setOnClickListener(v -> {
            Intent goToSignUpActivityIntent = new Intent(SettingsPage.this, SignUpActivity.class);
            startActivity(goToSignUpActivityIntent);
        });
    }

    void setupLoginButton() {
        loginButton.setOnClickListener(v -> {
            Intent goToLoginActivityIntent = new Intent(SettingsPage.this, LoginActivity.class);
            startActivity(goToLoginActivityIntent);
        });
    }

    void setupLogoutButton() {
        logoutButton.setOnClickListener(v -> {
            //Cognito Logout Logic
            AuthSignOutOptions signOutOptions = AuthSignOutOptions.builder()
                    .globalSignOut(true)
                    .build();

            Amplify.Auth.signOut(signOutOptions,
                    signOutResult -> {
                        if(signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                            Log.i(TAG, "Global sign out successful!");

                            Intent goToMainActivity = new Intent(SettingsPage.this, MainActivity.class);
                            startActivity(goToMainActivity);
                        } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                            Log.i(TAG, "Partial sign out successful!");
                        } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                            Log.i(TAG, "Logout failed: " + signOutResult.toString());
                        }
                    }
            );
        });
    }

    void renderButtons() {
        if(authUser == null) {
            logoutButton.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            signUpButton.setVisibility(View.VISIBLE);
        } else {
            logoutButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.INVISIBLE);
            signUpButton.setVisibility(View.INVISIBLE);
        }
    }
}