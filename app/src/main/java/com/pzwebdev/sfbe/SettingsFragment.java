package com.pzwebdev.sfbe;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsFragment extends AppCompatActivity {

    SharedPreferences preferences;
    EditText editBranchName, editBranchNumber, editTextEmail;
    String stateName, stateEmail, stateNumber;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_fragment);

        String SHARED_PREFERENCES = "SETTINGS";
        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        saveButton = findViewById(R.id.saveButton);
        editBranchName = findViewById(R.id.editBranchName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editBranchNumber = findViewById(R.id.editBranchNumber);

        stateName = preferences.getString("name", "");
        stateEmail = preferences.getString("email", "");
        stateNumber = preferences.getString("number", "");

        editBranchName.setText(stateName);
        editTextEmail.setText(stateEmail);
        editBranchNumber.setText(stateNumber);

        saveButton.setOnClickListener(view -> {
            String name = editBranchName.getText().toString();
            String email = editTextEmail.getText().toString();
            String number = editBranchNumber.getText().toString();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name", name);
            editor.putString("email", email);
            editor.putString("number", number);
            editor.commit();

            SettingsFragment.this.finish();
        });
    }

}