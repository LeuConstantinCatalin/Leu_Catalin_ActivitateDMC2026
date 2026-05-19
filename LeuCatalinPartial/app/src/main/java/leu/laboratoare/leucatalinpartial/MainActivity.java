//bilet 1

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

public class MainActivity extends AppCompatActivity {

    private int REQUEST_CODE_ADD = 2003;
    private Button btnAddHusa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnAddHusa = findViewById(R.id.leu_catalin_btn_adauga_husa);

        btnAddHusa.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddHusaView.class);
            intent.putExtra("is_edit", false);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode != RESULT_OK || data == null) {
                return;
            }

            HusaTelefon husa = data.getParcelableExtra("husa_object");
            if (husa == null) {
                return;
            }
            Toast.makeText(
                    this,
                    "Ai adaugat: " + husa.toString(),
                    Toast.LENGTH_SHORT
            ).show();
        }

}