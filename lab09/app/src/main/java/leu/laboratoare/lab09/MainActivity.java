package leu.laboratoare.lab09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnOpenImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenImages = findViewById(R.id.btnOpenImages);

        btnOpenImages.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
            startActivity(intent);
        });
    }
}