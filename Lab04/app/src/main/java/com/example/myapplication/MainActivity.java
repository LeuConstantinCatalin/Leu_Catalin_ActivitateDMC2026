package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 100;

    private TextView tvTitle;
    private ListView lvCalculator;
    private Button btnAddCalculator;
    private List<Calculator> calculatorList;
    private ArrayAdapter<Calculator> calculatorArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        lvCalculator = findViewById(R.id.lvCalculatorList);
        btnAddCalculator = findViewById(R.id.btnAddCalculator);

        if (savedInstanceState != null) {
            calculatorList = (List<Calculator>) savedInstanceState.getSerializable("calculator_list");
        }

        if (calculatorList == null) {
            calculatorList = new ArrayList<>();
        }

        calculatorArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                calculatorList
        );

        lvCalculator.setAdapter(calculatorArrayAdapter);

        btnAddCalculator.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCalculatorActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        lvCalculator.setOnItemClickListener((parent, view, position, id) -> {
            Calculator calculator = calculatorList.get(position);

            Toast.makeText(
                    MainActivity.this,
                    "Ai selectat: " + calculator.toSummary(),
                    Toast.LENGTH_SHORT
            ).show();
        });

        lvCalculator.setOnItemLongClickListener((parent, view, position, id) -> {
            Calculator calculator = calculatorList.get(position);

            calculatorList.remove(position);
            calculatorArrayAdapter.notifyDataSetChanged();

            Toast.makeText(
                    MainActivity.this,
                    "Ai sters: " + calculator.toSummary(),
                    Toast.LENGTH_SHORT
            ).show();

            return true;
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
                    calculatorList.add(calculator);
                    calculatorArrayAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("calculator_list", (java.io.Serializable) calculatorList);
    }
}