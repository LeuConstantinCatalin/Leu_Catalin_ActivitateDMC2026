package leu.laboratoare.test_senzori;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor rotationSensor;

    private TextView tvValues;
    private JoystickView joystickView;

    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvValues = findViewById(R.id.tvValues);
        joystickView = findViewById(R.id.joystickView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        if (rotationSensor == null) {
            tvValues.setText("Senzorul TYPE_ROTATION_VECTOR nu exista pe dispozitiv.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (rotationSensor != null) {
            sensorManager.registerListener(
                    this,
                    rotationSensor,
                    SensorManager.SENSOR_DELAY_GAME
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientation);

            float yaw = orientation[0];
            float pitch = orientation[1];
            float roll = orientation[2];

            float pitchDeg = (float) Math.toDegrees(pitch);
            float rollDeg = (float) Math.toDegrees(roll);
            float yawDeg = (float) Math.toDegrees(yaw);

            float maxAngle = 45.0f;

            float turretX = rollDeg / maxAngle;
            float turretY = -pitchDeg / maxAngle;

            turretX = Math.max(-1, Math.min(1, turretX));
            turretY = Math.max(-1, Math.min(1, turretY));

            joystickView.updateJoystick(turretX, turretY);

            tvValues.setText(
                    "Control tureta:\n" +
                            "X stanga/dreapta = " + turretX + "\n" +
                            "Y sus/jos = " + turretY + "\n\n" +
                            "Yaw = " + yawDeg + "\n" +
                            "Pitch = " + pitchDeg + "\n" +
                            "Roll = " + rollDeg
            );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}