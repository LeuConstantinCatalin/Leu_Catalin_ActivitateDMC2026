package leu.laboratoare.lab11;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_SLIDERS = 10;
    private static final int MAX_VALUE = 100;

    private EditText etNrParams;
    private LinearLayout slidersContainer;
    private TextView tvSlidersTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etNrParams = findViewById(R.id.etNrParams);
        slidersContainer = findViewById(R.id.slidersContainer);
        tvSlidersTitle = findViewById(R.id.tvSlidersTitle);
        Button btnCreateSliders = findViewById(R.id.btnCreateSliders);
        Button btnSentIntent = findViewById(R.id.btnSentIntent);

        btnCreateSliders.setOnClickListener(v -> generateSliders());
        btnSentIntent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChartActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void generateSliders() {
        String input = etNrParams.getText().toString().trim();
        if (input.isEmpty()) {
            etNrParams.setError("Introdu numarul de valori");
            return;
        }

        int count;
        try {
            count = Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            etNrParams.setError("Introdu un numar valid");
            return;
        }

        if (count <= 0) {
            etNrParams.setError("Numarul trebuie sa fie mai mare ca 0");
            return;
        }

        if (count > MAX_SLIDERS) {
            Toast.makeText(
                    this,
                    "Poti genera cel mult " + MAX_SLIDERS + " slidere.",
                    Toast.LENGTH_SHORT
            ).show();
            count = MAX_SLIDERS;
            etNrParams.setText(String.valueOf(MAX_SLIDERS));
        }

        slidersContainer.removeAllViews();
        tvSlidersTitle.setVisibility(View.VISIBLE);

        for (int i = 1; i <= count; i++) {
            slidersContainer.addView(createSliderRow(i));
        }
    }

    private LinearLayout createSliderRow(int index) {
        LinearLayout row = new LinearLayout(this);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(0, 0, 0, dpToPx(16));

        TextView label = new TextView(this);
        label.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        label.setText("Valoare " + index);
        label.setTextSize(16);

        LinearLayout sliderLine = new LinearLayout(this);
        sliderLine.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        sliderLine.setOrientation(LinearLayout.HORIZONTAL);
        sliderLine.setGravity(Gravity.CENTER_VERTICAL);

        SeekBar seekBar = new SeekBar(this);
        LinearLayout.LayoutParams seekBarParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );
        seekBar.setLayoutParams(seekBarParams);
        seekBar.setMax(MAX_VALUE);
        seekBar.setProgress(50);

        TextView valueView = new TextView(this);
        LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        valueParams.setMarginStart(dpToPx(12));
        valueView.setLayoutParams(valueParams);
        valueView.setMinEms(3);
        valueView.setGravity(Gravity.END);
        valueView.setText(String.valueOf(seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                valueView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sliderLine.addView(seekBar);
        sliderLine.addView(valueView);

        row.addView(label);
        row.addView(sliderLine);
        return row;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
