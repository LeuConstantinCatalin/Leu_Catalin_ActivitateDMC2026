package leu.laboratoare.lab09;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageListActivity extends AppCompatActivity {

    private ListView listViewMotors;
    private ArrayList<MotorImage> motorImages;
    private MotorImageAdapter adapter;

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        listViewMotors = findViewById(R.id.listViewMotors);

        motorImages = new ArrayList<>();
        loadData();

        adapter = new MotorImageAdapter(this, motorImages);
        listViewMotors.setAdapter(adapter);

        loadImagesWithExecutors();

        listViewMotors.setOnItemClickListener((parent, view, position, id) -> {
            MotorImage selected = motorImages.get(position);
            Intent intent = new Intent(ImageListActivity.this, WebViewActivity.class);
            intent.putExtra("webUrl", selected.getWebUrl());
            startActivity(intent);
        });
    }

    private void loadData() {
        motorImages.add(new MotorImage(
                "https://p2.piqsels.com/preview/87/183/667/printer-3d-pressure-3d-printing.jpg",
                "Imprimanta 3D - sistem motorizat de pozitionare",
                "https://en.wikipedia.org/wiki/3D_printing"
        ));

        motorImages.add(new MotorImage(
                "https://p2.piqsels.com/preview/883/414/433/chip-circuit-close-up-electronics.jpg",
                "Circuit electronic pentru controlul motoarelor",
                "https://en.wikipedia.org/wiki/Motor_controller"
        ));

        motorImages.add(new MotorImage(
                "https://p2.piqsels.com/preview/895/965/147/robot-servo-an-ultrasound-sensor-motor.jpg",
                "Servomotor utilizat in robotica",
                "https://en.wikipedia.org/wiki/Servomotor"
        ));

        motorImages.add(new MotorImage(
                "https://p0.piqsels.com/preview/915/954/399/stepper-motor-motor-coil-stepper.jpg",
                "Motor pas cu pas (stepper motor)",
                "https://en.wikipedia.org/wiki/Stepper_motor"
        ));

        motorImages.add(new MotorImage(
                "https://p2.piqsels.com/preview/971/485/213/robot-servo-motor-recycling.jpg",
                "Alt exemplu de servomotor in sistem robotic",
                "https://en.wikipedia.org/wiki/Servo_control"
        ));
    }

    private void loadImagesWithExecutors() {
        for (MotorImage motorImage : motorImages) {
            executorService.execute(() -> {
                Bitmap bitmap = downloadBitmap(motorImage.getImageUrl());
                motorImage.setBitmap(bitmap);

                mainHandler.post(() -> adapter.notifyDataSetChanged());
            });
        }
    }

    private Bitmap downloadBitmap(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}