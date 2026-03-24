package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 100;
    private static final int REQUEST_CODE_EDIT = 200;

    private TextView tvTitle;
    private ListView lvCalculator;
    private Button btnAddCalculator;

    private List<Calculator> calculatorList;
    private CalculatorAdapter calculatorAdapter;

    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        lvCalculator = findViewById(R.id.lvCalculatorList);
        btnAddCalculator = findViewById(R.id.btnAddCalculator);

        if (savedInstanceState != null) {
            calculatorList = savedInstanceState.getParcelableArrayList("calculator_list");
        }

        if (calculatorList == null) {
            calculatorList = new ArrayList<>();
        }

        calculatorAdapter = new CalculatorAdapter(this, calculatorList);
        lvCalculator.setAdapter(calculatorAdapter);

        btnAddCalculator.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCalculatorActivity.class);
            intent.putExtra("is_edit", false);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        lvCalculator.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Calculator calculator = calculatorList.get(position);

            Intent intent = new Intent(MainActivity.this, AddCalculatorActivity.class);
            intent.putExtra("is_edit", true);
            intent.putExtra("calculator_object", calculator);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        });

        lvCalculator.setOnItemLongClickListener((parent, view, position, id) -> {
            Calculator calculator = calculatorList.get(position);

            calculatorList.remove(position);
            calculatorAdapter.notifyDataSetChanged();

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

        if (resultCode != RESULT_OK || data == null) {
            return;
        }

        Calculator calculator = data.getParcelableExtra("calculator_object");
        if (calculator == null) {
            return;
        }

        if (requestCode == REQUEST_CODE_ADD) {
            calculatorList.add(calculator);
            calculatorAdapter.notifyDataSetChanged();

            Toast.makeText(
                    this,
                    "Ai adaugat: " + calculator.toSummary(),
                    Toast.LENGTH_SHORT
            ).show();
        } else if (requestCode == REQUEST_CODE_EDIT) {
            if (selectedPosition >= 0 && selectedPosition < calculatorList.size()) {
                calculatorList.set(selectedPosition, calculator);
                calculatorAdapter.notifyDataSetChanged();

                Toast.makeText(
                        this,
                        "Ai modificat: " + calculator.toSummary(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("calculator_list", new ArrayList<>(calculatorList));
    }
}