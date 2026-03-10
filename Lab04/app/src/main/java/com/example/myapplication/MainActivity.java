package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 100;

    private TextView tvTitle;
    private TextView tvResult;
    private Button btnAddCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvResult = findViewById(R.id.tvResult);
        btnAddCalculator = findViewById(R.id.btnAddCalculator);

        btnAddCalculator.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCalculatorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK && data != null) {
            Bundle bundle = data.getBundleExtra("bundle_calculator");
            if (bundle != null) {
                Calculator calculator = (Calculator) bundle.getSerializable("calculator_object");
                if (calculator != null) {
                    tvResult.setText(calculator.toString());
                }
            }
        }
    }
}