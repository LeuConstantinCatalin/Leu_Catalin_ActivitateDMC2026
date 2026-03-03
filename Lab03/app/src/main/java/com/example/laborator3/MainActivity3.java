package com.example.laborator3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    private int a = 0;
    private int b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Bundle in = getIntent().getExtras();
        String msg = "(no msg)";
        if (in != null) {
            msg = in.getString("msg", "(no msg)");
            a = in.getInt("a", 0);
            b = in.getInt("b", 0);
        }

        Toast.makeText(this, msg + " | a=" + a + " b=" + b, Toast.LENGTH_LONG).show();

        Button btn = findViewById(R.id.btn_send_result);
        btn.setOnClickListener(v -> {
            Intent back = new Intent();
            Bundle out = new Bundle();
            out.putString("msgBack", "Mesaj din MainActivity3");
            out.putInt("sum", a + b);
            back.putExtras(out);

            setResult(RESULT_OK, back);
            finish();
        });
    }
}