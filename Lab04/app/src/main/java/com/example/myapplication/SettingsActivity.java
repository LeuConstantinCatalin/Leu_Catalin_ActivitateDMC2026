package com.example.myapplication;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText etTextSize;
    private Spinner spinnerColor;
    private Button btnSave;

    private static final String PREFS_NAME = "settings_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etTextSize = findViewById(R.id.etTextSize);
        spinnerColor = findViewById(R.id.spinnerColor);
        btnSave = findViewById(R.id.btnSaveSettings);

        String[] colors = {"Black", "Red", "Blue", "Green"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                colors
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColor.setAdapter(adapter);

        loadSettings();

        btnSave.setOnClickListener(v -> saveSettings());
    }

    private void saveSettings() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int textSize = Integer.parseInt(etTextSize.getText().toString());
        String color = spinnerColor.getSelectedItem().toString();

        editor.putInt("text_size", textSize);
        editor.putString("text_color", color);

        editor.apply();
        finish();
    }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        int size = prefs.getInt("text_size", 16);
        String color = prefs.getString("text_color", "Black");

        etTextSize.setText(String.valueOf(size));

        String[] colors = {"Black", "Red", "Blue", "Green"};
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(color)) {
                spinnerColor.setSelection(i);
                break;
            }
        }
    }
}