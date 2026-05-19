package leu.laboratoare.leucatalinpartial;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.TextureView;
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

public class AddHusaView extends AppCompatActivity {

    private int REQUEST_CODE_ADD = 2003;
    private Button btnSaveHusa;
    private EditText tvMaterial;
    private EditText tvLungime;
    private EditText tvGreutate;
    private CheckBox cbAreClapa;

    private HusaTelefon husa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_husa_view);

        btnSaveHusa = findViewById(R.id.leu_catalin_btn_salvare);
        tvGreutate = findViewById(R.id.leu_catalin_tv_greutate);
        tvLungime = findViewById(R.id.leu_catalin_tv_lungime);
        cbAreClapa = findViewById(R.id.leu_catalin_cb_are_clapa);
        tvMaterial = findViewById(R.id.leu_catalin_tv_material);

        btnSaveHusa.setOnClickListener(v -> {
            populateFields();
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode != RESULT_OK || data == null) {
//            return;
//        }
//        if (requestCode == REQUEST_CODE_ADD)
//        {
//
//        }
//    }



    private void populateFields()
    {
        husa = new HusaTelefon();
        husa.material = tvMaterial.getText().toString().trim();
        husa.lungime = Float.parseFloat(tvLungime.getText().toString().trim());
        husa.greutate = Float.parseFloat(tvGreutate.getText().toString().trim());
        husa.are_clapa = cbAreClapa.isChecked();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("husa_object", husa);

        setResult(RESULT_OK, resultIntent);

        finish();
    }
}