package com.example.laborator3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle back = result.getData().getExtras();
                        if (back != null) {
                            String msgBack = back.getString("msgBack", "(no msgBack)");
                            int sum = back.getInt("sum", 0);
                            Toast.makeText(this, msgBack + " | suma=" + sum, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        Button btn = findViewById(R.id.btn_send_intent);
        btn.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity2.this, MainActivity3.class);

            Bundle b = new Bundle();
            b.putString("msg", "Mesaj din MainActivity2");
            b.putInt("a", 100);
            b.putInt("b", 200);

            i.putExtras(b);
            launcher.launch(i);
        });
    }
}