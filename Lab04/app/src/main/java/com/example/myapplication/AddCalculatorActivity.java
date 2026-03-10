package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AddCalculatorActivity extends AppCompatActivity {

    private EditText etModel;
    private EditText etRam;
    private CheckBox cbGaming;
    private RadioButton rbWindows;
    private RadioButton rbLinux;
    private Spinner spinnerBrand;
    private RatingBar ratingBar;
    private Switch switchWarranty;
    private ToggleButton toggleBluetooth;
    private Button btnSave;

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
        toggleBluetooth = findViewById(R.id.toggleBluetooth);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<Brand> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Brand.values()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBrand.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveCalculator());
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

        Calculator calculator = new Calculator(
                model,
                gaming,
                ramGb,
                brand,
                rating,
                warranty,
                bluetoothEnabled,
                osType
        );



        Bundle bundle = new Bundle();
        bundle.putSerializable("calculator_object", calculator);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("bundle_calculator", bundle);

        setResult(RESULT_OK, resultIntent);
        Toast.makeText(this, "Obiect creat cu succes", Toast.LENGTH_SHORT).show();
        finish();
    }
}