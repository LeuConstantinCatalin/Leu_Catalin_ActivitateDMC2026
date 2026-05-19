package leu.laboratoare.lab11;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Chart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chart);

        Button btnBack = findViewById(R.id.btnBack);
        TextView tvChartTitle = findViewById(R.id.tvChartTitle);
        CanvasChartView chartView = findViewById(R.id.chartView);
        TextView tvReceivedValues = findViewById(R.id.tvReceivedValues);
        int[] values = getIntent().getIntArrayExtra(MainActivity.EXTRA_SLIDER_VALUES);
        int chartType = getIntent().getIntExtra(MainActivity.EXTRA_CHART_TYPE, CanvasChartView.CHART_PIE);

        btnBack.setOnClickListener(v -> finish());
        chartView.setValues(values);
        tvReceivedValues.setText(formatReceivedValues(values));
        updateChart(chartType, tvChartTitle, chartView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String formatReceivedValues(int[] values) {
        if (values == null || values.length == 0) {
            return "Nu au fost primite valori in Bundle.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Numar parametri: ").append(values.length);

        for (int i = 0; i < values.length; i++) {
            builder.append("\nParametru ").append(i + 1).append(": ").append(values[i]);
        }

        return builder.toString();
    }

    private void updateChart(int chartType, TextView tvChartTitle, CanvasChartView chartView) {
        tvChartTitle.setText(getChartTitle(chartType));
        chartView.setChartType(chartType);
    }

    private String getChartTitle(int chartType) {
        if (chartType == CanvasChartView.CHART_COLUMN) {
            return "ColumnChart";
        }

        if (chartType == CanvasChartView.CHART_BAR) {
            return "BarChart";
        }

        return "PieChart";
    }
}
