package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Date;
import java.io.FileOutputStream;

public class AddCalculatorActivity extends AppCompatActivity {
    private static final String FILE_NAME = "calculatoare.txt";
    private EditText etModel;
    private EditText etRam;
    private CheckBox cbGaming;
    private RadioButton rbWindows;
    private RadioButton rbLinux;
    private Spinner spinnerBrand;
    private RatingBar ratingBar;
    private Switch switchWarranty;
    private CalendarView cvWarrantyExpiryDate;
    private ToggleButton toggleBluetooth;
    private Button btnSave;
    private boolean isEditMode = false;
    private Calculator calculatorToEdit = null;

    private void saveCalculatorToFile(Calculator calculator) {
        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_APPEND)) {
            fos.write(calculator.toFileString().getBytes());
        } catch (Exception e) {
            Toast.makeText(this, "Eroare la salvarea in fisier", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calculator);

        etModel = findViewById(R.id.etModel);
        etRam = findViewById(R.id.etRam);
        cbGaming = findViewById(R.id.cbGaming);
        rbWindows = findViewById(R.id.rbWindows);
        rbLinux = findViewById(R.id.rbLinux);
        spinnerBrand = findViewById(R.id.spinnerBrand);
        ratingBar = findViewById(R.id.ratingBar);
        switchWarranty = findViewById(R.id.switchWarranty);
        cvWarrantyExpiryDate = findViewById(R.id.chooseWarrantyDate);
        toggleBluetooth = findViewById(R.id.toggleBluetooth);
        btnSave = findViewById(R.id.btnSave);

        cvWarrantyExpiryDate.setVisibility(View.GONE);

        ArrayAdapter<Brand> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Brand.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(adapter);

        isEditMode = getIntent().getBooleanExtra("is_edit", false);

        if (isEditMode) {
            calculatorToEdit = getIntent().getParcelableExtra("calculator_object");
            if (calculatorToEdit != null) {
                populateFields(calculatorToEdit);
                btnSave.setText("Modifica");
            }
        }

        switchWarranty.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                cvWarrantyExpiryDate.setVisibility(View.VISIBLE);
            } else {
                cvWarrantyExpiryDate.setVisibility(View.GONE);
            }
        });

        btnSave.setOnClickListener(v -> saveCalculator());
        applySettings();
    }

    private void applySettings() {
        SharedPreferences prefs = getSharedPreferences("settings_prefs", MODE_PRIVATE);

        int textSize = prefs.getInt("text_size", 16);
        String colorName = prefs.getString("text_color", "Black");

        int color = Color.BLACK;

        switch (colorName) {
            case "Red":
                color = Color.RED;
                break;
            case "Blue":
                color = Color.BLUE;
                break;
            case "Green":
                color = Color.GREEN;
                break;
        }

        etModel.setTextSize(textSize);
        etRam.setTextSize(textSize);

        etModel.setTextColor(color);
        etRam.setTextColor(color);
    }

    private void populateFields(Calculator calculator) {
        etModel.setText(calculator.getModel());
        etRam.setText(String.valueOf(calculator.getRamGb()));
        cbGaming.setChecked(calculator.isGaming());

        if ("Windows".equals(calculator.getOsType())) {
            rbWindows.setChecked(true);
        } else if ("Linux".equals(calculator.getOsType())) {
            rbLinux.setChecked(true);
        }

        if (calculator.getBrand() != null) {
            spinnerBrand.setSelection(calculator.getBrand().ordinal());
        }

        ratingBar.setRating(calculator.getRating());
        switchWarranty.setChecked(calculator.isWarranty());
        toggleBluetooth.setChecked(calculator.isBluetoothEnabled());

        if (calculator.isWarranty()) {
            cvWarrantyExpiryDate.setVisibility(View.VISIBLE);

            if (calculator.getWarrantyExpiryDate() != null) {
                cvWarrantyExpiryDate.setDate(
                        calculator.getWarrantyExpiryDate().getTime(),
                        true,
                        true
                );
            }
        } else {
            cvWarrantyExpiryDate.setVisibility(View.GONE);
        }
    }

    private void saveCalculator() {
        String model = etModel.getText().toString().trim();
        String ramText = etRam.getText().toString().trim();

        if (model.isEmpty()) {
            etModel.setError("Introdu modelul");
            return;
        }

        if (ramText.isEmpty()) {
            etRam.setError("Introdu RAM-ul");
            return;
        }

        int ramGb;
        try {
            ramGb = Integer.parseInt(ramText);
        } catch (NumberFormatException e) {
            etRam.setError("Valoare invalida");
            return;
        }

        boolean gaming = cbGaming.isChecked();
        Brand brand = (Brand) spinnerBrand.getSelectedItem();
        float rating = ratingBar.getRating();
        boolean warranty = switchWarranty.isChecked();
        boolean bluetoothEnabled = toggleBluetooth.isChecked();

        String osType = "Necunoscut";
        if (rbWindows.isChecked()) {
            osType = "Windows";
        } else if (rbLinux.isChecked()) {
            osType = "Linux";
        }

        Date warrantyExpiryDate = null;
        if (warranty) {
            warrantyExpiryDate = new Date(cvWarrantyExpiryDate.getDate());
        }

        Calculator calculator;

        if (isEditMode && calculatorToEdit != null) {
            calculatorToEdit.setModel(model);
            calculatorToEdit.setGaming(gaming);
            calculatorToEdit.setRamGb(ramGb);
            calculatorToEdit.setBrand(brand);
            calculatorToEdit.setRating(rating);
            calculatorToEdit.setWarranty(warranty);
            calculatorToEdit.setWarrantyExpiryDate(warrantyExpiryDate);
            calculatorToEdit.setBluetoothEnabled(bluetoothEnabled);
            calculatorToEdit.setOsType(osType);

            calculator = calculatorToEdit;
        } else {
            calculator = new Calculator(
                    model,
                    gaming,
                    ramGb,
                    brand,
                    rating,
                    warranty,
                    warrantyExpiryDate,
                    bluetoothEnabled,
                    osType
            );
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("calculator_object", calculator);

        setResult(RESULT_OK, resultIntent);

        if (isEditMode) {
            Toast.makeText(this, "Obiect modificat cu succes", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Obiect creat cu succes", Toast.LENGTH_SHORT).show();
        }

        saveCalculatorToFile(calculator);

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        View root = findViewById(android.R.id.content);
        UiUtils.applySettingsToView(this, root);
    }
}