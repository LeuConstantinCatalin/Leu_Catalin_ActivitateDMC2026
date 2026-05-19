package leu.laboratoare.lab8;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etTip, etPutere, etTensiune;
    private EditText etSearchTip;
    private EditText etMinPutere, etMaxPutere;
    private EditText etDeleteValue;
    private EditText etLetter;

    private Button btnInsert, btnGetAll, btnSearchByTip, btnSearchByRange;
    private Button btnDeleteGreater, btnDeleteLess, btnIncrement;

    private ListView listViewMotors;

    private MotorDatabase database;
    private MotorDao motorDao;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> motorStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        database = MotorDatabase.getInstance(this);
        motorDao = database.motorDao();

        motorStrings = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, motorStrings);
        listViewMotors.setAdapter(adapter);

        btnInsert.setOnClickListener(v -> insertMotor());
        btnGetAll.setOnClickListener(v -> showAllMotors());
        btnSearchByTip.setOnClickListener(v -> searchByTip());
        btnSearchByRange.setOnClickListener(v -> searchByRange());
        btnDeleteGreater.setOnClickListener(v -> deleteGreaterThan());
        btnDeleteLess.setOnClickListener(v -> deleteLessThan());
        btnIncrement.setOnClickListener(v -> incrementPutere());

        seedDataIfEmpty();
        showAllMotors();
    }

    private void initViews() {
        etTip = findViewById(R.id.etTip);
        etPutere = findViewById(R.id.etPutere);
        etTensiune = findViewById(R.id.etTensiune);

        etSearchTip = findViewById(R.id.etSearchTip);

        etMinPutere = findViewById(R.id.etMinPutere);
        etMaxPutere = findViewById(R.id.etMaxPutere);

        etDeleteValue = findViewById(R.id.etDeleteValue);
        etLetter = findViewById(R.id.etLetter);

        btnInsert = findViewById(R.id.btnInsert);
        btnGetAll = findViewById(R.id.btnGetAll);
        btnSearchByTip = findViewById(R.id.btnSearchByTip);
        btnSearchByRange = findViewById(R.id.btnSearchByRange);
        btnDeleteGreater = findViewById(R.id.btnDeleteGreater);
        btnDeleteLess = findViewById(R.id.btnDeleteLess);
        btnIncrement = findViewById(R.id.btnIncrement);

        listViewMotors = findViewById(R.id.listViewMotors);
    }

    private void insertMotor() {
        String tip = etTip.getText().toString().trim();
        String putereStr = etPutere.getText().toString().trim();
        String tensiuneStr = etTensiune.getText().toString().trim();

        if (TextUtils.isEmpty(tip) || TextUtils.isEmpty(putereStr) || TextUtils.isEmpty(tensiuneStr)) {
            Toast.makeText(this, "Completeaza toate campurile pentru inserare.", Toast.LENGTH_SHORT).show();
            return;
        }

        int putere = Integer.parseInt(putereStr);
        int tensiune = Integer.parseInt(tensiuneStr);

        Motor motor = new Motor(tip, putere, tensiune);
        motorDao.insertMotor(motor);

        Toast.makeText(this, "Motor inserat.", Toast.LENGTH_SHORT).show();

        etTip.setText("");
        etPutere.setText("");
        etTensiune.setText("");

        showAllMotors();
    }

    private void showAllMotors() {
        List<Motor> motors = motorDao.getAllMotors();
        updateListView(motors);
    }

    private void searchByTip() {
        String tip = etSearchTip.getText().toString().trim();

        if (TextUtils.isEmpty(tip)) {
            Toast.makeText(this, "Introdu tipul cautat.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Motor> motors = motorDao.getMotorsByTip(tip);
        updateListView(motors);
    }

    private void searchByRange() {
        String minStr = etMinPutere.getText().toString().trim();
        String maxStr = etMaxPutere.getText().toString().trim();

        if (TextUtils.isEmpty(minStr) || TextUtils.isEmpty(maxStr)) {
            Toast.makeText(this, "Introdu minimul si maximul.", Toast.LENGTH_SHORT).show();
            return;
        }

        int min = Integer.parseInt(minStr);
        int max = Integer.parseInt(maxStr);

        List<Motor> motors = motorDao.getMotorsByPutereRange(min, max);
        updateListView(motors);
    }

    private void deleteGreaterThan() {
        String valueStr = etDeleteValue.getText().toString().trim();

        if (TextUtils.isEmpty(valueStr)) {
            Toast.makeText(this, "Introdu valoarea pentru stergere.", Toast.LENGTH_SHORT).show();
            return;
        }

        int value = Integer.parseInt(valueStr);
        motorDao.deleteMotorsWithVoltageGreaterThan(value);

        Toast.makeText(this, "S-au sters motoarele cu tensiune > " + value, Toast.LENGTH_SHORT).show();
        showAllMotors();
    }

    private void deleteLessThan() {
        String valueStr = etDeleteValue.getText().toString().trim();

        if (TextUtils.isEmpty(valueStr)) {
            Toast.makeText(this, "Introdu valoarea pentru stergere.", Toast.LENGTH_SHORT).show();
            return;
        }

        int value = Integer.parseInt(valueStr);
        motorDao.deleteMotorsWithVoltageLessThan(value);

        Toast.makeText(this, "S-au sters motoarele cu tensiune < " + value, Toast.LENGTH_SHORT).show();
        showAllMotors();
    }

    private void incrementPutere() {
        String litera = etLetter.getText().toString().trim();

        if (TextUtils.isEmpty(litera)) {
            Toast.makeText(this, "Introdu o litera.", Toast.LENGTH_SHORT).show();
            return;
        }

        motorDao.incrementPutereForTipStartingWith(litera);

        Toast.makeText(this, "Puterea a fost crescuta cu 1.", Toast.LENGTH_SHORT).show();
        showAllMotors();
    }

    private void updateListView(List<Motor> motors) {
        motorStrings.clear();

        if (motors == null || motors.isEmpty()) {
            motorStrings.add("Nu exista inregistrari.");
        } else {
            for (Motor motor : motors) {
                motorStrings.add(motor.toString());
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void seedDataIfEmpty() {
        List<Motor> motors = motorDao.getAllMotors();
        if (motors.isEmpty()) {
            motorDao.insertMotor(new Motor("stepper", 5, 12));
            motorDao.insertMotor(new Motor("servo", 3, 6));
            motorDao.insertMotor(new Motor("dc", 10, 24));
        }
    }
}