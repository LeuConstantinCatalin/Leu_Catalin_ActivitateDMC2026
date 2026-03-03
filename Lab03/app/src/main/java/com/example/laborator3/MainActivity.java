package com.example.laborator3;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void logAll(String method) {
        Log.e(TAG, method + " -> Log.e");
        Log.w(TAG, method + " -> Log.w");
        Log.d(TAG, method + " -> Log.d");
        Log.i(TAG, method + " -> Log.i");
        Log.v(TAG, method + " -> Log.v");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAll("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logAll("onResume");
    }

    @Override
    protected void onPause() {
        logAll("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        logAll("onStop");
        super.onStop();
    }
}