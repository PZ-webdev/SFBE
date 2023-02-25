package com.pzwebdev.sfbe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SettingsFragment extends AppCompatActivity {

    SharedPreferences preferences;
    TextInputEditText userName, userEmail, branchName, branchNumber;
    String stateUserName, stateUserEmail, stateBranchName, stateBranchNumber;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_fragment);

        String SHARED_PREFERENCES = "SETTINGS";
        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);

        saveButton = findViewById(R.id.button_save_settings);
        userName = findViewById(R.id.textInputEditText_user_name);
        userEmail = findViewById(R.id.textInputEditText_user_email);
        branchName = findViewById(R.id.textInputEditText_branch_name);
        branchNumber = findViewById(R.id.textInputEditText_branch_number);


        stateUserName = preferences.getString("userName", "");
        stateUserEmail = preferences.getString("userEmail", "");
        stateBranchName = preferences.getString("branchName", "");
        stateBranchNumber = preferences.getString("branchNumber", "");

        userName.setText(stateUserName);
        userEmail.setText(stateUserEmail);
        branchName.setText(stateBranchName);
        branchNumber.setText(stateBranchNumber);

        saveButton.setOnClickListener(view -> {
            String name = userName.getText().toString();
            String email = userEmail.getText().toString();
            String nameBranch = branchName.getText().toString();
            String numberBranch = branchNumber.getText().toString();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("userName", name);
            editor.putString("userEmail", email);
            editor.putString("branchName", nameBranch);
            editor.putString("branchNumber", numberBranch);
            editor.commit();

            SettingsFragment.this.finish();
        });
    }

}